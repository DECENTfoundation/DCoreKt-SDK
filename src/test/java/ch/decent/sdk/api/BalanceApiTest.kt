package ch.decent.sdk.api

import ch.decent.sdk.account
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class BalanceApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get balance for account`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"get_account_balances",["1.2.34",[]]],"id":1}""", """{"id":1,"result":[{"amount":50500000,"asset_id":"1.3.0"}]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    mockHttp.enqueue("""{"id":0,"result":[{"amount":50500000,"asset_id":"1.3.0"}]}""")

    val test = api.balance.getBalance(account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

}