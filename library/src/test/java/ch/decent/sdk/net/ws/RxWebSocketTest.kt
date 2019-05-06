package ch.decent.sdk.net.ws

import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.Helpers
import ch.decent.sdk.TimeOutTest
import ch.decent.sdk.net.model.request.GetChainId
import ch.decent.sdk.net.model.request.Login
import ch.decent.sdk.net.ws.model.WebSocketClosedException
import ch.decent.sdk.print
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.amshove.kluent.`should be equal to`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory

class RxWebSocketTest : TimeOutTest() {

  private lateinit var socket: RxWebSocket

  @Before fun init() {
    val logger = LoggerFactory.getLogger("RxWebSocket")
    socket = RxWebSocket(
        Helpers.client(logger),
        Helpers.wsUrl,
        logger = logger,
        gson = DCoreSdk.gsonBuilder.create()
    )
  }

  @After fun close() {
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
    val fail = socket.request(GetChainId).test()

    fail.awaitTerminalEvent()
    fail.assertError(WebSocketClosedException::class.java)

    val login = socket.request(Login).test()

    login.awaitTerminalEvent()
    login.assertComplete()
        .assertNoErrors()
        .assertValue(true)
  }

  @Test fun `should connect, disconnect, fail request and retry with success`() {
    val websocket = socket.webSocket().test()

    websocket.awaitTerminalEvent()
    websocket.assertComplete()
        .assertValueCount(1)

    socket.disconnect()
    val test = socket.request(GetChainId).retry(1).test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get unique call id`() {
    val len = 9999
    val test = Single.merge((1..len).map { Single.just(it).subscribeOn(Schedulers.newThread()).map { socket.callId } })
        .toList()
        .map { it.toSet().size }
        .test()

    test.awaitTerminalEvent()
    test.values().single() `should be equal to` len
  }
}