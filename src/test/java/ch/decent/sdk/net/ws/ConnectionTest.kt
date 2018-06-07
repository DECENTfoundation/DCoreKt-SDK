package ch.decent.sdk.net.ws

import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.TimeOutTest
import ch.decent.sdk.client
import ch.decent.sdk.net.TrustAllCerts
import ch.decent.sdk.net.model.request.GetChainId
import ch.decent.sdk.net.model.request.GetObjects
import ch.decent.sdk.net.model.request.Login
import ch.decent.sdk.net.ws.model.WebSocketClosedException
import ch.decent.sdk.url
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
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
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":0}""", """{"id":0,"result":true}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":1}""", """{"id":1,"result":true}""")
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
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":0}""", """{"id":0,"result":true}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    val websocket = socket.webSocket().test()

    websocket.awaitTerminalEvent()
    websocket.assertComplete()
        .assertValueCount(1)

    val fail = socket.request(GetChainId).test()
    socket.disconnect()

    fail.awaitTerminalEvent()
    fail.assertError(WebSocketClosedException::class.java)

    val login = socket.request(Login()).test()

    login.awaitTerminalEvent()
    login.assertComplete()
        .assertNoErrors()
        .assertValue(true)
  }
}