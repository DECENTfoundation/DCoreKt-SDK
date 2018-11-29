package ch.decent.sdk.net.ws

import ch.decent.sdk.exception.DCoreException
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.net.model.request.BaseRequest
import ch.decent.sdk.net.model.request.Login
import ch.decent.sdk.net.model.request.RequestApiAccess
import ch.decent.sdk.net.model.request.WithCallback
import ch.decent.sdk.net.model.response.Error
import ch.decent.sdk.net.ws.model.*
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.AsyncProcessor
import io.reactivex.rxkotlin.addTo
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import org.slf4j.Logger
import org.threeten.bp.LocalDateTime
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.xml.stream.events.EndElement

internal class RxWebSocket(
    private val client: OkHttpClient,
    private val url: String,
    private val gson: Gson,
    private val logger: Logger? = null,
    private val request: Request = Request.Builder().url(url).build()
) {

  private val disposable = CompositeDisposable()
  private val apiId = mutableMapOf<ApiGroup, Int>()
  internal val events = Flowable.create<WebSocketEvent>({ emitter ->
    val webSocket = client.newWebSocket(request, WebSocketEmitter(emitter))
    emitter.setCancellable { webSocket.close(1000, null) }
  }, BackpressureStrategy.BUFFER)
      .publish()

  private var webSocketAsync: AsyncProcessor<WebSocket>? = null
  internal var callId: Long = 0
    get() = field++

  val connected: Boolean
    get() = disposable.size() != 0

  private fun <T> Flowable<T>.log(tag: String): Flowable<T> = if (logger == null) this else
    this.compose {
      it.doOnSubscribe { logger.info("$tag #onSubscribe on ${Thread.currentThread()}") }
          .doOnNext { value -> logger.info("$tag #onNext:$value on ${Thread.currentThread()}") }
          .doOnComplete { logger.info("$tag #onComplete on ${Thread.currentThread()}") }
          .doOnError { error -> logger.info("$tag #onError:$error on ${Thread.currentThread()}") }
          .doOnCancel { logger.info("$tag #onCancel on ${Thread.currentThread()}") }
          .doOnRequest { value -> logger.info("$tag #onRequest:$value on ${Thread.currentThread()}") }
    }

  private fun BaseRequest<*>.json(callId: Long, callback: Long?, apiId: Int) =
      (if (callback != null) listOf(callback) + params else params).let {
        RequestJson(callId, apiId, method, it).let { gson.toJson(it) }
      }

  private fun BaseRequest<*>.send(ws: WebSocket, callId: Long, callback: Long?, apiId: Int) =
      json(callId, callback, apiId).let { logger?.info(it); ws.send(it) }

  /**
   * check for callback notice or simple result
   */
  private fun parseIdAndElement(message: String): Pair<Long, JsonObject> =
      JsonParser().parse(message).asJsonObject.let {
        if (it.has("method") && it.get("method").asString == "notice") {
          it.get("params").asJsonArray.let {
            val id = it[0].asLong
            val result = it[1].asJsonArray[0]
            id to JsonObject().apply { add("result", result) }
          }
        } else {
          it.get("id").asLong to it
        }
      }

  private fun checkForError(callId: Long, resultId: Long, obj: JsonObject) =
      if (resultId == callId && obj.has("error")) throw DCoreException(gson.fromJson(obj["error"], Error::class.java)) else Unit

  private fun checkObjectNotFound(obj: JsonObject, request: BaseRequest<*>) =
      if (obj["result"].isJsonNull && request.returnClass != Unit::class.java) throw ObjectNotFoundException(request.description())
      else if (obj["result"].isJsonArray && obj.getAsJsonArray("result").contains(JsonNull.INSTANCE)) throw ObjectNotFoundException(request.description())
      else Unit

  private fun parseResultElement(type: Type, obj: JsonObject) =
      when {
        type is ParameterizedType -> obj.getAsJsonArray("result")
        obj.get("result").isJsonPrimitive -> obj.get("result")
        type == Unit::class.java -> JsonObject()
        else -> obj.getAsJsonObject("result")
      }

  private fun connect() {
    disposable.addAll(
        events.log("RxWebSocket")
            .doOnTerminate {
              webSocketAsync = null
              disposable.clear()
            }.subscribe(),
        events.ofType(OnOpen::class.java).firstOrError()
            .doOnSuccess { webSocketAsync!!.onNext(it.webSocket); webSocketAsync!!.onComplete() }
            .subscribe()
    )
    events.connect { it.addTo(disposable) }
  }

  internal fun webSocket(): Single<WebSocket> {
    if (webSocketAsync == null) {
      webSocketAsync = AsyncProcessor.create()
      connect()
    }
    return webSocketAsync!!.singleOrError()
  }

  private fun <T : WebSocket> Single<T>.checkApiAccess(request: BaseRequest<*>): Single<Pair<T, Int>> =
      this.flatMap { ws ->
        when {
          request === Login -> Single.just(ws to 1)
          request.apiGroup !in apiId && request.apiGroup == ApiGroup.LOGIN -> Login.make(callId).doOnSuccess { apiId[ApiGroup.LOGIN] = 1 }.map { ws to 1 }
          request.apiGroup !in apiId -> RequestApiAccess(request.apiGroup).make(callId).doOnSuccess { apiId[request.apiGroup] = it }.map { ws to it }
          else -> Single.just(ws to apiId[request.apiGroup]!!)
        }
      }

  private fun <T> BaseRequest<T>.makeStream(callId: Long, callback: Long? = null): Flowable<T> =
      Flowable.merge(listOf(
          events,
//          defer to call again after retry, otherwise would be stuck with old websocket
          Single.defer { webSocket() }.checkApiAccess(this).doOnSuccess { (ws, apiId) -> send(ws, callId, callback, apiId) }.toFlowable()
      ))
          .ofType(OnMessageText::class.java)
          .map { parseIdAndElement(it.text) }
          .doOnNext { (id, obj) -> checkForError(callId, id, obj) }
          .filter { (id, _) -> id == callback ?: callId }
          .doOnNext { (_, obj) -> checkObjectNotFound(obj, this) }
          .map { (_, obj) -> parseResultElement(returnClass, obj) }
          .map { gson.fromJson<T>(it, returnClass) }

  private fun <T> BaseRequest<T>.make(callId: Long): Single<T> =
      makeStream(callId)
          .firstOrError()
          .onErrorResumeNext {
            if (it is NoSuchElementException) Single.error(WebSocketClosedException())
            else Single.error(it)
          }


  internal fun <T, R> requestStream(request: T): Flowable<R> where T : BaseRequest<R>, T : WithCallback = request.makeStream(callId, callId)

  internal fun <T> request(request: BaseRequest<T>): Single<T> = request.make(callId)

  fun disconnect() {
    disposable.add(
        webSocket().subscribe { ws -> ws.close(1000, "disconnect") }
    )
  }

  private class RequestJson(
      @SerializedName("id") val callId: Long,
      apiId: Int,
      apiMethod: String,
      params: List<*>
  ) {
    @SerializedName("method") val method: String = "call"
    @SerializedName("params") val params: List<*> = listOf(apiId, apiMethod, params)

  }
}