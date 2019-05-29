package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.model.toObjectId
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class BalanceApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get balance for account id`() {
    val test = api.balanceApi.get(Helpers.account, "1.3.35".toObjectId())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get balances for account id`() {
    val test = api.balanceApi.getAll(Helpers.account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get balance for account name`() {
    val test = api.balanceApi.get(Helpers.accountName, "1.3.35".toObjectId())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get balances for account name`() {
    val test = api.balanceApi.getAll(Helpers.accountName)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get balance for account id with asset`() {
    val test = api.balanceApi.getWithAsset(Helpers.account, "DCT")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get balances for account id with asset`() {
    val test = api.balanceApi.getAllWithAsset(Helpers.account, listOf("DCT", "SDK"))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get balance for account name with asset`() {
    val test = api.balanceApi.getWithAsset(Helpers.accountName, "DCT")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get balances for account name with asset`() {
    val test = api.balanceApi.getAllWithAsset(Helpers.accountName, listOf("DCT", "SDK"))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get vesting balances`() {
    val test = api.balanceApi.getAllVesting(Helpers.account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

}
