package ch.decent.sdk.api

import ch.decent.sdk.DCoreClient
import ch.decent.sdk.Helpers
import ch.decent.sdk.api.rx.DCoreApi
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.slf4j.LoggerFactory

@Ignore
class CallbackApiTest {

  private lateinit var api: DCoreApi

  @Before fun init() {
    val logger = LoggerFactory.getLogger("RxWebSocket")
    api = DCoreClient.createForWebSocket(Helpers.client(logger), Helpers.wsUrl, logger)
  }

  @After fun finish() {
  }

  @Test fun `should fail for HTTP`() {
    api = DCoreClient.createForHttp(Helpers.client(), Helpers.httpUrl)

    val test = api.callbackApi.onBlockApplied()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(IllegalArgumentException::class.java)
  }

  @Test fun `should set block applied callback`() {
    val test = api.callbackApi.onBlockApplied()
        .take(1)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should set content update callback`() {
    val test = api.callbackApi.onContentUpdate("http://some.uri")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should set pending transaction callback`() {
    val test = api.callbackApi.onPendingTransaction()
        .take(1)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}
