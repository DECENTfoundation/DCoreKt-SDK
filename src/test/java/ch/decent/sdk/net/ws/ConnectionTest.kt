package ch.decent.sdk.net.ws

import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.client
import ch.decent.sdk.net.TrustAllCerts
import ch.decent.sdk.net.model.request.Login
import ch.decent.sdk.net.ws.model.WebSocketClosedException
import ch.decent.sdk.url
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory

class ConnectionTest {

  private lateinit var socket: RxWebSocket

  @Before fun init() {
    socket = RxWebSocket(
        TrustAllCerts.wrap(client).build(),
        url,
        logger = LoggerFactory.getLogger("RxWebSocket"),
        gson = DCoreSdk.gsonBuilder.create()
    )
  }

  @Test fun `should connect, disconnect and connect`() {
    var websocket = socket.webSocket().test()

    websocket.awaitTerminalEvent()
    websocket.assertComplete()
        .assertValueCount(1)

    val events = socket.events.test()
    socket.disconnect()

    events.awaitTerminalEvent()
    events.assertComplete()

    websocket = socket.webSocket().test()

    websocket.awaitTerminalEvent()
    websocket.assertComplete()
        .assertValueCount(1)
  }

  @Test fun `should connect, disconnect, fail request and reconnect with success`() {
    val websocket = socket.webSocket().test()

    websocket.awaitTerminalEvent()
    websocket.assertComplete()
        .assertValueCount(1)

    socket.disconnect()
    var login = socket.request(Login()).test()

    login.awaitTerminalEvent()
    login.assertError(WebSocketClosedException::class.java)

    login = socket.request(Login()).test()

    login.awaitTerminalEvent()
    login.assertComplete()
        .assertNoErrors()
        .assertValue(true)
  }
}