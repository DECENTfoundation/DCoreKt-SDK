package ch.decent.sdk.api

import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.net.model.request.PriceToDct
import ch.decent.sdk.testCheck
import org.junit.Test

class AssetApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get asset for id`() {
    api.assetApi.get("1.3.0".toChainObject()).testCheck()
  }

  @Test fun `should get assets for ids`() {
    api.assetApi.getAll(listOf("1.3.0".toChainObject(), "1.3.1".toChainObject())).testCheck()
  }

  @Test fun `should get real supply`() {
    api.assetApi.getRealSupply().testCheck()
  }

  @Test fun `should get asset data for id`() {
    api.assetApi.getAssetData("2.3.0".toChainObject()).testCheck()
  }

  @Test fun `should get assets data for id`() {
    api.assetApi.getAssetsData(listOf("2.3.0".toChainObject(), "2.3.35".toChainObject())).testCheck()
  }

  @Test fun `should list assets for lower bound symbol`() {
    api.assetApi.listAllRelative("A").testCheck()
  }

  @Test fun `should get asset for symbol`() {
    api.assetApi.getByName("SDK.1557392016T").testCheck()
  }

  @Test fun `should get assets for symbols`() {
    api.assetApi.getAllByName(listOf("DCT", "USD")).testCheck()
  }

  @Test fun `should list all assets`() {
    api.assetApi.listAll(true).testCheck()
  }

  @Test fun `should convert asset to DCT`() {
    api.core.makeRequest(PriceToDct(AssetAmount(3, "1.3.33".toChainObject()))).testCheck {
      assertComplete()
      assertNoErrors()
      assertValue { it.amount == 3000000L }
    }

    api.assetApi.convertToDCT("1.3.33".toChainObject(), 3).testCheck {
      assertComplete()
      assertNoErrors()
      assertValue { it.amount == 3000000L }
    }
  }

  @Test fun `should convert asset from DCT`() {
    api.assetApi.convertFromDCT("1.3.33".toChainObject(), 3000000).testCheck {
      assertComplete()
      assertNoErrors()
      assertValue { it.amount == 3L }
    }
  }

}
