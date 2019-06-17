package ch.decent.sdk.net.ws

import ch.decent.sdk.exception.DCoreException
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.net.model.request.BaseRequest
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
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.AsyncProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.addTo
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import org.slf4j.Logger
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicLong

internal sealed class MessageEvent
internal data class Message(val id: Long, val obj: JsonObject) : MessageEvent()
internal data class WsError(val t: Throwable) : MessageEvent()

internal class RxWebSocket(
    private val client: OkHttpClient,
    private val url: String,
    private val gson: Gson,
    private val logger: Logger? = null,
    private val request: Request = Request.Builder().url(url).build()
) {

  private val disposable = CompositeDisposable()
  private val messages = PublishProcessor.create<MessageEvent>()
  internal val events = Flowable.create<WebSocketEvent>({ emitter ->
    val webSocket = client.newWebSocket(request, WebSocketEmitter(emitter))
    emitter.setCancellable { webSocket.close(CLOSE, null) }
  }, BackpressureStrategy.BUFFER)
      .publish()

  private var webSocketAsync: AsyncProcessor<WebSocket>? = null
  private val backingId = AtomicLong(0)
  val callId: Long
    get() = backingId.getAndIncrement()

  internal var timeout = TimeUnit.MINUTES.toSeconds(1)

  val connected: Boolean
    get() = disposable.size() != 0


  private fun connect() {
    disposable.addAll(
        events.infoLog("RxWebSocket", logger)
            .doOnTerminate { clearConnection() }
            .doOnComplete { messages.onNext(WsError(WebSocketClosedException())) }
            .doOnError { messages.onNext(WsError(it)) }
            .onErrorResumeNext(Flowable.empty())
            .ofType(OnMessageText::class.java)
            .map { parseIdAndElement(it.text) }
            .doOnNext { messages.onNext(it) }
            .subscribe(),
        events.ofType(OnOpen::class.java).firstOrError()
            .doOnSuccess { webSocketAsync!!.onNext(it.webSocket); webSocketAsync!!.onComplete() }
            .ignoreElement().onErrorComplete()
            .subscribe()
    )
    events.connect { it.addTo(disposable) }
  }

  private fun clearConnection() {
    logger?.info("clearing connection state")
    webSocketAsync = null
    disposable.clear()
    backingId.set(0)
  }

  internal fun webSocket(): Single<WebSocket> {
    if (webSocketAsync == null) {
      webSocketAsync = AsyncProcessor.create()
      connect()
    }
    return webSocketAsync!!.singleOrError()
  }

  private fun <T> BaseRequest<T>.makeStream(callId: Long, callback: Long? = null): Flowable<T> =
      Flowable.merge(listOf(
          Single.defer { webSocket() }.doOnSuccess { ws -> send(ws, callId, callback, gson, logger) }.ignoreElement().toFlowable(),
          messages
      ))
          .flatMap { if (it is WsError) Flowable.error(it.t) else Flowable.just(it) }
          .ofType(Message::class.java)
          .doOnNext { (id, obj) -> checkForError(callId, id, obj, gson) }
          .filter { (id, _) -> id == callback ?: callId }
          .doOnNext { (_, obj) -> checkObjectNotFound(obj, this) }
          .map { (_, obj) -> parseResultElement(returnClass, obj) }
          .map { gson.fromJson<T>(it, returnClass) }

  private fun <T> BaseRequest<T>.make(callId: Long, callback: Long? = null): Single<T> =
      makeStream(callId, callback)
          .firstOrError()
          .timeout(timeout, TimeUnit.SECONDS)
          .doOnError { if (it is TimeoutException) clearConnection() }

  internal fun <T, R> requestStream(request: T): Flowable<R> where T : BaseRequest<R>, T : WithCallback = request.makeStream(callId, callId)

  internal fun <T> request(request: BaseRequest<T>): Single<T> = request.make(callId, if (request is WithCallback) callId else null)

  fun disconnect() {
    disposable.add(
        webSocket().subscribe { ws -> ws.close(CLOSE, "disconnect") }
    )
  }

  companion object {
    private const val CLOSE = 1000

    private fun <T> Flowable<T>.infoLog(tag: String, logger: Logger?): Flowable<T> = if (logger == null) this else
      this.compose {
        it.doOnSubscribe { logger.info("$tag #onSubscribe on ${Thread.currentThread()}") }
            .doOnNext { value -> logger.info("$tag #onNext:$value on ${Thread.currentThread()}") }
            .doOnComplete { logger.info("$tag #onComplete on ${Thread.currentThread()}") }
            .doOnError { error -> logger.info("$tag #onError:$error on ${Thread.currentThread()}") }
            .doOnCancel { logger.info("$tag #onCancel on ${Thread.currentThread()}") }
            .doOnRequest { value -> logger.info("$tag #onRequest:$value on ${Thread.currentThread()}") }
      }

    private fun BaseRequest<*>.send(ws: WebSocket, callId: Long, callback: Long?, gson: Gson, logger: Logger?) =
        json(gson, callId, callback).let { logger?.info(it); ws.send(it) }

    /**
     * check for callback notice or simple result
     */
    private fun parseIdAndElement(message: String): Message =
        JsonParser().parse(message).asJsonObject.let {
          if (it.has("method") && it.get("method").asString == "notice") {
            it.get("params").asJsonArray.let {
              val id = it[0].asLong
              val result = it[1].asJsonArray[0]
              Message(id, JsonObject().apply { add("result", result) })
            }
          } else {
            Message(it.get("id").asLong, it)
          }
        }

    private fun checkForError(callId: Long, resultId: Long, obj: JsonObject, gson: Gson) =
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
  }
}
