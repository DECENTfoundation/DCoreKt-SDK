package ch.decent.sdk.api

import ch.decent.sdk.*
import ch.decent.sdk.net.ws.CustomWebSocketService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.slf4j.LoggerFactory

@RunWith(Parameterized::class)
abstract class BaseApiTest(private val channel: Channel) : TimeOutTest() {

  open val useMock = true

  protected lateinit var mockWebSocket: CustomWebSocketService
  protected lateinit var mockHttp: MockWebServer
  protected lateinit var api: DCoreApi

  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "{0}")
    fun channels() = listOf(
        arrayOf(Channel.RpcService),
        arrayOf(Channel.RxWebSocket)
    )
  }

  @Before fun init() {
    System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE")
    System.setProperty("rx2.buffer-size", "10")

    val logger = LoggerFactory.getLogger(channel.toString())
    mockHttp = MockWebServer().apply { start() }
    mockWebSocket = CustomWebSocketService().apply { start() }
    api = when (channel) {
      Channel.RpcService -> DCoreSdk.createForHttp(client(logger), if (useMock) mockHttp.url("/").toString() else restUrl, logger)
      Channel.RxWebSocket -> DCoreSdk.createForWebSocket(client(logger), if (useMock) mockWebSocket.getUrl() else url, logger)
    }
  }

  @After fun finish() {
    mockWebSocket.shutdown()
    mockHttp.shutdown()
  }

  fun MockWebServer.enqueue(body: String) = this.enqueue(MockResponse().apply { setBody(body) })

  enum class Channel {
    RpcService,
    RxWebSocket
  }
}