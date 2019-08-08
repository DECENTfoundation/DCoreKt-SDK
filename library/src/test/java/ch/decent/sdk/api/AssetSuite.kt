package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.crypto.ecKey
import ch.decent.sdk.exception.DCoreException
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.ExchangeRateValues
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.operation.AssetClaimFeesOperation
import ch.decent.sdk.model.operation.AssetFundPoolsOperation
import ch.decent.sdk.model.toObjectId
import ch.decent.sdk.net.model.request.PriceToDct
import ch.decent.sdk.testCheck
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import org.junit.FixMethodOrder
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Suite

@Suite.SuiteClasses(AssetOperationsTest::class, AssetApiTest::class)
@RunWith(Suite::class)
class AssetSuite

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AssetOperationsTest : BaseOperationsTest() {

  @Test fun `asset-1 should create main SDK asset`() {
    api.assetApi.create(
        Helpers.credentials,
        Helpers.createAsset,
        12,
        "test asset"
    ).testCheck()
  }

  // account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; }
  // therefore Missing Active Authority 1.2.0
  @Ignore
  @Test fun `asset- should create a monitored asset`() {
    api.assetApi.createMonitoredAsset(
        Helpers.credentials,
        "MSDK",
        4,
        "hello api monitored"
    ).testCheck()
  }

  @Test fun `asset-2 should update an asset`() {
    api.assetApi.update(
        Helpers.credentials,
        Helpers.createAsset,
        ExchangeRateValues(1, 50),
        "some nested asset",
        true,
        System.currentTimeMillis() / 1000
    ).testCheck()
  }

  @Test fun `asset-3 should update advanced asset`() {
    api.assetApi.updateAdvanced(
        Helpers.credentials,
        Helpers.createAsset,
        6,
        true
    ).testCheck()
  }

  @Test fun `asset-4 should issue an asset`() {
    api.assetApi.issue(
        Helpers.credentials,
        Helpers.createAsset,
        6000000
    ).testCheck()
  }

  @Test fun `asset-5 should fund an asset pool`() {
    api.assetApi.fund(
        Helpers.credentials,
        Helpers.createAsset,
        0,
        100000 // 0.01 dct fee
    ).testCheck()
  }

  @Test fun `asset-5 should fund an asset pool from non-issuer account`() {
    val op = AssetFundPoolsOperation(Helpers.account2, AssetAmount(0, Helpers.createAssetId), AssetAmount(1))
    api.broadcastApi.broadcastWithCallback(Helpers.private2.ecKey(), op)
        .testCheck()
  }

  @Test fun `asset-6 should make a transfer with fee`() {
    api.accountApi.transfer(
        Helpers.credentials,
        Helpers.accountName2,
        AssetAmount(1),
        fee = Fee(Helpers.createAssetId)
    ).testCheck {
      assertComplete()
      assertNoErrors()
      assertValue { it.transaction.operations.single().fee.assetId == Helpers.createAssetId }
    }
  }

  @Test fun `asset-7 should claim an asset pool`() {
    api.assetApi.claim(
        Helpers.credentials,
        Helpers.createAsset,
        5000000,
        0
    ).testCheck()
  }

  @Test fun `asset-7 should claim an asset pool from non-issuer account is not allowed`() {
    val op = AssetClaimFeesOperation(Helpers.account2, AssetAmount(0, Helpers.createAssetId), AssetAmount(1))
    api.broadcastApi.broadcastWithCallback(Helpers.private2.ecKey(), op)
        .testCheck {
          assertError(DCoreException::class.java)
        }
  }

  @Test fun `asset-8 should reserve an asset`() {
    api.assetApi.reserve(
        Helpers.credentials,
        Helpers.createAsset,
        5000000
    ).testCheck()
  }

}

class AssetApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get asset for id`() {
    api.assetApi.get("1.3.0".toObjectId()).testCheck()
  }

  @Test fun `should get assets for ids`() {
    api.assetApi.getAll(listOf("1.3.0".toObjectId(), "1.3.1".toObjectId())).testCheck()
  }

  @Test fun `should get real supply`() {
    api.assetApi.getRealSupply().testCheck()
  }

  @Test fun `should get asset data for id`() {
    api.assetApi.getAssetData("2.3.0".toObjectId()).testCheck()
  }

  @Test fun `should get assets data for id`() {
    api.assetApi.getAssetsData(listOf("2.3.0".toObjectId(), "2.3.1".toObjectId())).testCheck()
  }

  @Test fun `should list assets for lower bound symbol`() {
    api.assetApi.listAllRelative("A").testCheck()
  }

  @Test fun `should get asset for symbol`() {
    api.assetApi.getByName("USD").testCheck()
  }

  @Test fun `should get assets for symbols`() {
    api.assetApi.getAllByName(listOf("DCT", "USD")).testCheck()
  }

  @Test fun `should list all assets`() {
    api.assetApi.listAll(true).testCheck()
  }

  @Test fun `should convert asset to DCT`() {
    Single.zip(
        api.core.makeRequest(PriceToDct(AssetAmount(1000, Helpers.createAssetId))),
        api.assetApi.convertToDCT(Helpers.createAssetId, 1000),
        BiFunction { t1: AssetAmount, t2: AssetAmount -> t1.amount to t2.amount }
    ).testCheck { assertValue { it.first == it.second && it.first == 20L } }
  }

  @Test fun `should convert asset from DCT`() {
    api.assetApi.convertFromDCT(Helpers.createAssetId, 20).testCheck {
      assertComplete()
      assertNoErrors()
      assertValue { it.amount == 1000L }
    }
  }

}
