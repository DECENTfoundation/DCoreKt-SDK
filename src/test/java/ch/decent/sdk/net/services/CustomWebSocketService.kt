package ch.decent.sdk.net.services

import com.google.gson.Gson
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.ByteString
import org.slf4j.LoggerFactory

class CustomWebSocketService {

  private var mockWebServer = MockWebServer()
  private val responses = HashMap<String, String>()
  private val logger = LoggerFactory.getLogger("CustomWebSocketService")

  init {
    enqueueResponse()
  }

  fun start() {
    mockWebServer.start()
  }

  fun shutdown() {
    mockWebServer.shutdown()
  }

  fun getUrl() = mockWebServer.url("/").toString()

  fun enqueue(request: String, response: String): CustomWebSocketService {
    responses[request] = response
    return this
  }

  fun enqueue(request: Any, response: String): CustomWebSocketService {
    val key = Gson().toJson(request)
    responses[key] = response
    return this
  }

  private fun enqueueResponse() {
    val response = MockResponse().withWebSocketUpgrade(object : WebSocketListener() {
      override fun onOpen(webSocket: WebSocket?, response: Response?) {
        logger.debug("open")
      }

      override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
        logger.debug("fail")
      }

      override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
        logger.debug("closing")
      }

      override fun onMessage(webSocket: WebSocket?, text: String?) {
        logger.debug("message")
        responses[text!!]?.let {
          webSocket?.send(it)
          responses.remove(text)
        }
        if (responses.size == 0) webSocket?.close(1000, "closing...")
      }

      override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
        logger.debug("message")
      }

      override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
        logger.debug("closed")
      }
    })
    mockWebServer.enqueue(response)
  }
}