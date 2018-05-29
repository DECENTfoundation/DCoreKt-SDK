package ch.decent.sdk.net.ws

import ch.decent.sdk.net.ws.model.*
import io.reactivex.FlowableEmitter
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

internal class WebSocketEmitter(
    private val emitter: FlowableEmitter<WebSocketEvent>
): WebSocketListener() {
  override fun onOpen(webSocket: WebSocket, response: Response) {
    emitter.onNext(OnOpen(webSocket))
  }

  override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
    emitter.onError(t)
  }

  override fun onClosing(webSocket: WebSocket, code: Int, reason: String?) {
    webSocket.close(code, reason)
    emitter.onNext(OnClosing)
  }

  override fun onMessage(webSocket: WebSocket, text: String) {
    emitter.onNext(OnMessageText(text))
  }

  override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
    emitter.onNext(OnMessageBytes(bytes))
  }

  override fun onClosed(webSocket: WebSocket, code: Int, reason: String?) {
    emitter.onComplete()
  }
}