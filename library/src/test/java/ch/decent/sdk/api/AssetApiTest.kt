package ch.decent.sdk.api

import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.OperationType
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.print
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import java.math.BigInteger

class AssetApiTest(channel: Channel) : BaseApiTest(channel) {
  override val useMock: Boolean = false

  @Test fun `should get assets for id`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"get_assets",[["1.3.0"]]],"id":1}""", """{"id":1,"result":[{"id":"1.3.0","symbol":"DCT","precision":8,"issuer":"1.2.1","description":"","options":{"max_supply":"7319777577456900","core_exchange_rate":{"base":{"amount":1,"asset_id":"1.3.0"},"quote":{"amount":1,"asset_id":"1.3.0"}},"is_exchangeable":true,"extensions":[]},"dynamic_asset_data_id":"2.3.0"}]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    mockHttp.enqueue("""{"id":0,"result":[{"id":"1.3.0","symbol":"DCT","precision":8,"issuer":"1.2.1","description":"","options":{"max_supply":"7319777577456900","core_exchange_rate":{"base":{"amount":1,"asset_id":"1.3.0"},"quote":{"amount":1,"asset_id":"1.3.0"}},"is_exchangeable":true,"extensions":[]},"dynamic_asset_data_id":"2.3.0"}]}""")

    val test = api.assetApi.getAsset("1.3.0".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get asset for symbol`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"lookup_asset_symbols",[["DCT"]]],"id":1}""", """{"id":1,"result":[{"id":"1.3.0","symbol":"DCT","precision":8,"issuer":"1.2.1","description":"","options":{"max_supply":"7319777577456900","core_exchange_rate":{"base":{"amount":1,"asset_id":"1.3.0"},"quote":{"amount":1,"asset_id":"1.3.0"}},"is_exchangeable":true,"extensions":[]},"dynamic_asset_data_id":"2.3.0"}]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    mockHttp.enqueue("""{"id":0,"result":[{"id":"1.3.0","symbol":"DCT","precision":8,"issuer":"1.2.1","description":"","options":{"max_supply":"7319777577456900","core_exchange_rate":{"base":{"amount":1,"asset_id":"1.3.0"},"quote":{"amount":1,"asset_id":"1.3.0"}},"is_exchangeable":true,"extensions":[]},"dynamic_asset_data_id":"2.3.0"}]}""")

    val test = api.assetApi.lookupAsset("DCT")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get fee for transfer OP`() {
    val test = api.assetApi.getFee(OperationType.TRANSFER2_OPERATION)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should list assets for lower bound symbol`() {
    val test = api.assetApi.listAssets("ALX")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get price in DCT`() {
    val test = api.assetApi.priceToDct(AssetAmount(1000, "1.3.54"))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get real supply`() {
    val test = api.assetApi.getRealSupply()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

}