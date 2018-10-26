package ch.decent.sdk.api

import ch.decent.sdk.*
import ch.decent.sdk.net.ws.CustomWebSocketService
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException

// todo, unable to cancel callback
class SubscriptionsTest {

  private lateinit var mockWebSocket: CustomWebSocketService
  private lateinit var api: DCoreApi

  @Before fun init() {
    val logger = LoggerFactory.getLogger("RxWebSocket")
    mockWebSocket = CustomWebSocketService().apply { start() }
//    api = DCoreSdk.createForWebSocket(client(logger), mockWebSocket.getUrl(), logger)
    api = DCoreSdk.createForWebSocket(client(logger), url, logger)
  }

  @After fun finish() {
    mockWebSocket.shutdown()
  }

  // todo
  @Ignore
  @Test fun `should fail for HTTP`() {
    api = DCoreSdk.createForHttp(client(), restUrl)

    val test = api.subscriptionApi.setBlockAppliedCallback()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(IllegalArgumentException::class.java)
  }


  @Test fun `should cancel all subscriptions`() {
    val test = api.subscriptionApi.cancelAllSubscriptions()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should set block applied callback`() {
    val test = api.subscriptionApi.setBlockAppliedCallback()
        .take(3)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Ignore
  @Test fun `should set content update callback`() {
    val test = api.subscriptionApi.setContentUpdateCallback("http://some.uri")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Ignore
  @Test fun `should set pending transaction callback`() {
    val test = api.subscriptionApi.setPendingTransactionCallback()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should set subscribe callback`() {
    val test = api.subscriptionApi.setSubscribeCallback(false)
        .take(3)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}