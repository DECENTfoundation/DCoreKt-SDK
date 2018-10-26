package ch.decent.sdk.api

import ch.decent.sdk.account
import ch.decent.sdk.accountName
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PurchaseApiTest(channel: Channel) : BaseApiTest(channel) {
  override val useMock: Boolean = false

  @Test fun `should get list of open purchases`() {
    val test = api.purchaseApi.getOpenPurchases()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of open purchases for account`() {
    val test = api.purchaseApi.getOpenPurchases(account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of open purchases for uri`() {
    val test = api.purchaseApi.getOpenPurchases("http://some.uri")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of history purchases`() {
    val test = api.purchaseApi.getHistoryPurchases(account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get purchase for account and uri`() {
    val test = api.purchaseApi.getPurchase(account, "http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=bbc8a9c3-1bcd-48a6-820d-e5a60c29cf56")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
  @Test fun `should search purchases`() {
    val test = api.purchaseApi.searchPurchases(account, "new")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should search feedback`() {
    val test = api.purchaseApi.searchFeedback("")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of subscription for consumer`() {
    val test = api.purchaseApi.listSubscriptionsByConsumer(account, 10)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}