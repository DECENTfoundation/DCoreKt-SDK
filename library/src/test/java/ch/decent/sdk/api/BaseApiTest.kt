package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.Helpers
import ch.decent.sdk.TimeOutTest
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
    api = when (channel) {
      Channel.RpcService -> DCoreSdk.createForHttp(Helpers.client(logger), Helpers.restUrl, logger)
      Channel.RxWebSocket -> DCoreSdk.createForWebSocket(Helpers.client(logger), Helpers.wsUrl, logger)
    }
  }

  @After fun finish() {
  }

  enum class Channel {
    RpcService,
    RxWebSocket
  }
}