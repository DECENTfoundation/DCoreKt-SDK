package ch.decent.sdk.net.ws

import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.Globals
import ch.decent.sdk.TimeOutTest
import ch.decent.sdk.client
import ch.decent.sdk.net.model.request.GetChainId
import ch.decent.sdk.net.model.request.Login
import ch.decent.sdk.net.ws.model.WebSocketClosedException
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory

class ConnectionTest : TimeOutTest() {

  private lateinit var socket: RxWebSocket
  private lateinit var mockWebServer: CustomWebSocketService

  @Before fun init() {
    mockWebServer = CustomWebSocketService().apply { start() }
    socket = RxWebSocket(
        client,
        mockWebServer.getUrl(),
//        url,
        logger = LoggerFactory.getLogger("RxWebSocket"),
        gson = DCoreSdk.gsonBuilder.create()
    )
  }

  @Test fun `should connect, disconnect and connect`() {
    mockWebServer
        .enqueue("keep alive", "")

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
    mockWebServer
        .enqueue("keep alive", "")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    val websocket = socket.webSocket().test()

    websocket.awaitTerminalEvent()
    websocket.assertComplete()
        .assertValueCount(1)

    val fail = socket.request(GetChainId).test()
    socket.disconnect()

    fail.awaitTerminalEvent()
    fail.assertError(WebSocketClosedException::class.java)

    val login = socket.request(Login).test()

    login.awaitTerminalEvent()
    login.assertComplete()
        .assertNoErrors()
        .assertValue(true)
  }

  @Test fun `should connect, disconnect, fail request and retry with success`() {
    mockWebServer
        .enqueue("keep alive", "")
        .enqueue("""{"method":"call","params":[2,"get_chain_id",[]],"id":0}""", """{"id":0,"result":"17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc"}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":3}""", """{"id":3,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":4}""", """{"id":4,"result":true}""")

    val websocket = socket.webSocket().test()

    websocket.awaitTerminalEvent()
    websocket.assertComplete()
        .assertValueCount(1)

    val test = socket.request(GetChainId).retry(1).test()
    socket.disconnect()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue(Globals.DCT_CHAIN_ID)
  }
}