package ch.decent.sdk.api

import ch.decent.sdk.*
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.toChainObject
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class MessagingApiTest(channel: Channel) : BaseApiTest(channel) {
  override val useMock: Boolean = false

  @Test fun `should get messages account and decrypt for receiver`() {
    val credentials = Credentials(account2, private2)
/*
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_assets",[["1.3.0"]]],"id":1}""",
            """{"id":1,"result":[{"id":"1.3.0","symbol":"DCT","precision":8,"issuer":"1.2.1","description":"","options":{"max_supply":"7319777577456900","core_exchange_rate":{"base":{"amount":1,"asset_id":"1.3.0"},"quote":{"amount":1,"asset_id":"1.3.0"}},"is_exchangeable":true,"extensions":[]},"dynamic_asset_data_id":"2.3.0"}]}"""
        )

    mockHttp.enqueue(
        """{"id":0,"result":[{"id":"1.3.0","symbol":"DCT","precision":8,"issuer":"1.2.1","description":"","options":{"max_supply":"7319777577456900","core_exchange_rate":{"base":{"amount":1,"asset_id":"1.3.0"},"quote":{"amount":1,"asset_id":"1.3.0"}},"is_exchangeable":true,"extensions":[]},"dynamic_asset_data_id":"2.3.0"}]}"""
    )
*/

    val test = api.messagingApi.getAll(receiver = account2)
        .map { it.map { it.decrypt(credentials) } }
        .log("***")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get messages for account and decrypt for sender`() {
    val credentials = Credentials(account, private)
/*
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_assets",[["1.3.0"]]],"id":1}""",
            """{"id":1,"result":[{"id":"1.3.0","symbol":"DCT","precision":8,"issuer":"1.2.1","description":"","options":{"max_supply":"7319777577456900","core_exchange_rate":{"base":{"amount":1,"asset_id":"1.3.0"},"quote":{"amount":1,"asset_id":"1.3.0"}},"is_exchangeable":true,"extensions":[]},"dynamic_asset_data_id":"2.3.0"}]}"""
        )

    mockHttp.enqueue(
        """{"id":0,"result":[{"id":"1.3.0","symbol":"DCT","precision":8,"issuer":"1.2.1","description":"","options":{"max_supply":"7319777577456900","core_exchange_rate":{"base":{"amount":1,"asset_id":"1.3.0"},"quote":{"amount":1,"asset_id":"1.3.0"}},"is_exchangeable":true,"extensions":[]},"dynamic_asset_data_id":"2.3.0"}]}"""
    )
*/

    val test = api.messagingApi.getAll(account)
        .map { it.map { it.decrypt(credentials) } }
        .log("***")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}