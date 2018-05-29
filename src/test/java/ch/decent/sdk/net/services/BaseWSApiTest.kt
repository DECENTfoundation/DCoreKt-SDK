package ch.decent.sdk.net.services

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.client
import ch.decent.sdk.net.TrustAllCerts
import ch.decent.sdk.net.ws.RxWebSocket
import org.junit.After
import org.junit.Before
import org.slf4j.LoggerFactory

open class BaseWSApiTest {

  protected lateinit var socket: RxWebSocket
  protected lateinit var mockWebServer: CustomWebSocketService

  @Before fun init() {
    mockWebServer = CustomWebSocketService()
    mockWebServer.start()
    socket = RxWebSocket(
        TrustAllCerts.wrap(client).build(),
        mockWebServer.getUrl(),
        //url,
        logger = LoggerFactory.getLogger("RxWebSocket"),
        gson = DCoreApi.gsonBuilder.create()
    )
  }

  @After fun finish() {
    mockWebServer.shutdown()
  }

}