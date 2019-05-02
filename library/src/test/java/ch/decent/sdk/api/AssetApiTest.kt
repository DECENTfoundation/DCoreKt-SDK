package ch.decent.sdk.api

import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.toChainObject
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class AssetApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get asset for id`() {
    val test = api.assetApi.get("1.3.0".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get assets for ids`() {
    val test = api.assetApi.getAll(listOf("1.3.0".toChainObject(), "1.3.1".toChainObject()))
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

  @Test fun `should get asset data for id`() {
    val test = api.assetApi.getAssetData("2.3.0".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get assets data for id`() {
    val test = api.assetApi.getAssetsData(listOf("2.3.0".toChainObject(), "2.3.35".toChainObject()))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should list assets for lower bound symbol`() {
    val test = api.assetApi.listAllRelative("A")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get asset for symbol`() {
    val test = api.assetApi.getByName("DCT")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get assets for symbols`() {
    val test = api.assetApi.getAllByName(listOf("DCT", "USD"))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get price in DCT`() {
    val test = api.assetApi.convertToDct(AssetAmount(1000, "1.3.35"))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

}