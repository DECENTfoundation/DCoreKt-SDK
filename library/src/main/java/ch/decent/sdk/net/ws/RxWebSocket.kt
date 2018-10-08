package ch.decent.sdk.net.ws

import ch.decent.sdk.exception.DCoreException
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.net.model.request.BaseRequest
import ch.decent.sdk.net.model.request.Login
import ch.decent.sdk.net.model.request.RequestApiAccess
import ch.decent.sdk.net.model.request.WithCallback
import ch.decent.sdk.net.model.response.Error
import ch.decent.sdk.net.ws.model.OnMessageText
import ch.decent.sdk.net.ws.model.OnOpen
import ch.decent.sdk.net.ws.model.WebSocketClosedException
import ch.decent.sdk.net.ws.model.WebSocketEvent
import com.google.gson.Gson
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.annotations.SerializedName
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.AsyncProcessor
import io.reactivex.rxkotlin.addTo
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import org.slf4j.Logger
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

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
  }, BackpressureStrategy.ERROR)
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

  private fun BaseRequest<*>.json(callId: Long, apiId: Int) = RequestJson(callId, apiId, method, params).let { gson.toJson(it) }

  private fun BaseRequest<*>.send(ws: WebSocket, callId: Long, apiId: Int) = json(callId, apiId).let { logger?.info(it); ws.send(it) }

  /**
   * check for callback notice or simple result
   */
  private fun parseIdAndObj(message: String) =
      JsonParser().parse(message).asJsonObject.let {
        if (it.has("method") && it.get("method").asString == "notice") {
          it.get("params").asJsonArray.let {
            val id = it[0].asLong
            val result = it[1].asJsonArray[0].asJsonObject
            id to result
          }
        } else {
          it.get("id").asLong to it
        }
      }

  private fun checkForError(callId: Long, resultId: Long, obj: JsonObject) =
      if (resultId == callId && obj.has("error")) throw DCoreException(gson.fromJson(obj["error"], Error::class.java)) else Unit

  private fun checkObjectNotFound(obj: JsonObject, request: BaseRequest<*>) =
      if (obj.get("result")?.isJsonNull == true) throw ObjectNotFoundException(request.description())
      else if (obj.get("result")?.isJsonArray == true && obj.getAsJsonArray("result")?.contains(JsonNull.INSTANCE) == true) throw ObjectNotFoundException(request.description())
      else Unit

  private fun parseResultElement(type: Type, obj: JsonObject) =
      when {
        type is ParameterizedType -> obj.getAsJsonArray("result")
        (type as Class<*>).isPrimitive -> obj.get("result")
        type == String::class.java -> obj.get("result")
        else -> obj.getAsJsonObject("result")
      }

  private fun connect() {
    disposable.addAll(
        events.log("RxWebSocket")
            .doOnComplete {
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

  private fun <T> BaseRequest<T>.make(callId: Long): Single<T> =
      Flowable.merge(listOf(
          events,
//          defer to call again after retry, otherwise would be stuck with old websocket
          Single.defer { webSocket() }.checkApiAccess(this).doOnSuccess { (ws, apiId) -> send(ws, callId, apiId) }.toFlowable()
      ))
          .ofType(OnMessageText::class.java)
          .map { parseIdAndObj(it.text) }
          .doOnNext { (id, obj) -> checkForError(callId, id, obj) }
          .filter { (id, _) -> id == if (this is WithCallback) callbackId else callId }
          .firstOrError()
          .onErrorResumeNext {
            if (it is NoSuchElementException) Single.error(WebSocketClosedException())
            else Single.error(it)
          }
          .doOnSuccess { (_, obj) -> checkObjectNotFound(obj, this) }
          .map { (_, obj) -> if (this is WithCallback) obj else parseResultElement(returnClass, obj) }
          .map { gson.fromJson<T>(it, returnClass) }

  fun <T> request(request: BaseRequest<T>): Single<T> =
      request.make(callId)
//          .retry { times, throwable -> (throwable is WebSocketClosedException) and (times <= 1) }

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