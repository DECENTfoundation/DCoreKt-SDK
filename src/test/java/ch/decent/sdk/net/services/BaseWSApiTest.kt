package ch.decent.sdk.net.services

import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.client
import ch.decent.sdk.net.TrustAllCerts
import ch.decent.sdk.net.ws.RxWebSocket
import ch.decent.sdk.url
import org.junit.After
import org.junit.Before
import org.slf4j.LoggerFactory

internal abstract class BaseWSApiTest {

  protected val useMockWebServer: Boolean
      get() = true

  protected lateinit var socket: RxWebSocket
  protected var mockWebServer: CustomWebSocketService? = null

  @Before fun init() {
    if (useMockWebServer) {
      mockWebServer = CustomWebSocketService().apply { start() }
    }
    socket = RxWebSocket(
        TrustAllCerts.wrap(client).build(),
        if (useMockWebServer) mockWebServer!!.getUrl() else url,
        logger = LoggerFactory.getLogger("RxWebSocket"),
        gson = DCoreSdk.gsonBuilder.create()
    )
  }

  @After fun finish() {
    mockWebServer?.shutdown()
  }
}