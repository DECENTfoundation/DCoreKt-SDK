package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PurchaseApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get list of history purchases`() {
    val test = api.purchaseApi.getAllHistory(Helpers.account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of open purchases`() {
    val test = api.purchaseApi.getAllOpen()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of open purchases for uri`() {
    val test = api.purchaseApi.getAllOpenByUri("ipfs:QmWBoRBYuxzH5a8d3gssRbMS5scs6fqLKgapBfqVNUFUtZ")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of open purchases for account`() {
    val test = api.purchaseApi.getAllOpenByAccount(Helpers.account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get purchase for account and uri`() {
    val test = api.purchaseApi.get(Helpers.account, "ipfs:QmWBoRBYuxzH5a8d3gssRbMS5scs6fqLKgapBfqVNUFUtZ")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should search purchases`() {
    val test = api.purchaseApi.findAll(Helpers.account, "")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should search feedback`() {
    val test = api.purchaseApi.findAllForFeedback("ipfs:QmWBoRBYuxzH5a8d3gssRbMS5scs6fqLKgapBfqVNUFUtZ")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}