package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.DCoreClient
import ch.decent.sdk.Helpers
import ch.decent.sdk.TimeOutTest
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized
import org.slf4j.LoggerFactory
import org.threeten.bp.Duration

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
      Channel.RpcService -> DCoreClient.createForHttp(Helpers.client(logger), Helpers.httpUrl, logger)
      Channel.RxWebSocket -> DCoreClient.createForWebSocket(Helpers.client(logger), Helpers.wsUrl, logger)
    }
  }

  @After fun finish() {
  }

  enum class Channel {
    RpcService,
    RxWebSocket
  }
}

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
abstract class BaseOperationsTest {

  lateinit var api: DCoreApi

  @Before fun init() {
    val logger = LoggerFactory.getLogger("RxWebSocket")
    api = DCoreClient.createForWebSocket(Helpers.client(logger), Helpers.wsUrl, logger)
    api.transactionExpiration = Duration.ofSeconds(5)
  }

  @After fun finish() {
  }

}
