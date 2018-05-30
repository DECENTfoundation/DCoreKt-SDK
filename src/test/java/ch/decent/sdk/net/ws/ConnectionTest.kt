package ch.decent.sdk.net.ws

import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.client
import ch.decent.sdk.net.TrustAllCerts
import ch.decent.sdk.net.model.request.Login
import ch.decent.sdk.net.ws.model.OnClosing
import ch.decent.sdk.net.ws.model.WebSocketClosedException
import ch.decent.sdk.net.ws.model.WebSocketEvent
import ch.decent.sdk.url
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import okhttp3.WebSocket
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
    val observer = TestObserver<WebSocket>()
    val subscriber = TestSubscriber<WebSocketEvent>()

    socket.webSocket().subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertValueCount(1)

    socket.events.subscribe(subscriber)
    socket.disconnect()

    subscriber.awaitTerminalEvent()
    subscriber.assertComplete()
        .assertValueAt(0, OnClosing)

    socket.webSocket().subscribe(observer)
    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertValueCount(1)
  }

  @Test fun `should connect, disconnect, fail request and reconnect with success`() {
    val observer = TestObserver<WebSocket>()
    var login = TestObserver<Boolean>()

    socket.webSocket().subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertValueCount(1)

    socket.disconnect()
    socket.request(Login()).subscribe(login)

    login.awaitTerminalEvent()
    login.assertError(WebSocketClosedException::class.java)

    login = TestObserver()
    socket.request(Login()).subscribe(login)
    login.awaitTerminalEvent()
    login.assertComplete()
        .assertNoErrors()
        .assertValue(true)
  }
}