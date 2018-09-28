package ch.decent.sdk.api

import ch.decent.sdk.model.toChainObject
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class AssetApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get assets for id`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"get_assets",[["1.3.0"]]],"id":1}""", """{"id":1,"result":[{"id":"1.3.0","symbol":"DCT","precision":8,"issuer":"1.2.1","description":"","options":{"max_supply":"7319777577456900","core_exchange_rate":{"base":{"amount":1,"asset_id":"1.3.0"},"quote":{"amount":1,"asset_id":"1.3.0"}},"is_exchangeable":true,"extensions":[]},"dynamic_asset_data_id":"2.3.0"}]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    mockHttp.enqueue("""{"id":0,"result":[{"id":"1.3.0","symbol":"DCT","precision":8,"issuer":"1.2.1","description":"","options":{"max_supply":"7319777577456900","core_exchange_rate":{"base":{"amount":1,"asset_id":"1.3.0"},"quote":{"amount":1,"asset_id":"1.3.0"}},"is_exchangeable":true,"extensions":[]},"dynamic_asset_data_id":"2.3.0"}]}""")

    val test = api.assetApi.getAssets((listOf("1.3.0".toChainObject())))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

}