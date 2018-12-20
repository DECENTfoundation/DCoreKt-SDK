package ch.decent.sdk.api

import ch.decent.sdk.account
import ch.decent.sdk.model.toChainObject
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class BalanceApiTest(channel: Channel) : BaseApiTest(channel) {
//  override val useMock: Boolean = false

  @Test fun `should get balance for account`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_account_balances",["1.2.34",[]]],"id":1}""",
            """{"id":1,"result":[{"amount":50500000,"asset_id":"1.3.0"}]}"""
        )

    mockHttp.enqueue(
        """{"id":0,"result":[{"amount":50500000,"asset_id":"1.3.0"}]}"""
    )

    val test = api.balanceApi.getBalance(account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get zero balance for UIA`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_account_balances",["1.2.34",["1.3.56576"]]],"id":1}""",
            """{"id":1,"result":[{"amount":0,"asset_id":"1.3.56576"}]}"""
        )

    mockHttp.enqueue(
        """{"id":0,"result":[{"amount":50500000,"asset_id":"1.3.0"}]}"""
    )

    val test = api.balanceApi.getBalance(account, "1.3.56576".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get vesting balances`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_vesting_balances",["1.2.34"]],"id":1}""",
            """{"id":1,"result":[]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[]}"""
    )

    val test = api.balanceApi.getVestingBalances(account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

}