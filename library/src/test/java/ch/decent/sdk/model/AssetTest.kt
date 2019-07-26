package ch.decent.sdk.model

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.DCoreClient
import org.junit.Assert
import org.junit.Test
import java.math.RoundingMode
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class AssetTest {

  private val otherCoin = "1.3.1".toChainObject()

  private fun testAsset(assetId: ChainObject, base: AssetAmount, quote: AssetAmount) =
      Asset(
          assetId,
          "TEST",
          0,
          ObjectType.ACCOUNT_OBJECT.genericId,
          "",
          AssetOptions(
              exchangeRate = ExchangeRate(
                  base = base,
                  quote = quote
              ),
              exchangeable = true
          ),
          ObjectType.ASSET_DYNAMIC_DATA.genericId
      )

  private fun testConversionToDct(
      assetId: ChainObject,
      amountToConvert: Long,
      base: AssetAmount,
      quote: AssetAmount,
      expectedAmount: Long,
      roundingMode: RoundingMode
  ) {
    val testAsset = testAsset(assetId, base, quote)
    with(testAsset.convertToDCT(amountToConvert, roundingMode)) {
      Assert.assertEquals(expectedAmount, this.amount)
      Assert.assertEquals(DCoreConstants.DCT_ASSET_ID, this.assetId)
    }
  }

  private fun testConversionFromDct(
      assetId: ChainObject,
      amountToConvert: Long,
      base: AssetAmount,
      quote: AssetAmount,
      expectedAmount: Long,
      roundingMode: RoundingMode
  ) {
    val testAsset = testAsset(assetId, base, quote)
    with(testAsset.convertFromDCT(amountToConvert, roundingMode)) {
      Assert.assertEquals(expectedAmount, this.amount)
      Assert.assertEquals(assetId, this.assetId)
    }
  }


  @Test
  fun `should successfully convert to DCT with rounding UP`() {
    testConversionToDct(
        assetId = otherCoin,
        amountToConvert = 5,
        base = AssetAmount(10),
        quote = AssetAmount(3, otherCoin),
        expectedAmount = 17,
        roundingMode = RoundingMode.UP
    )
  }

  @Test
  fun `should successfully convert to DCT with rounding UP and switched base and quote`() {
    testConversionToDct(
        assetId = otherCoin,
        amountToConvert = 5,
        base = AssetAmount(3, otherCoin),
        quote = AssetAmount(10),
        expectedAmount = 17,
        roundingMode = RoundingMode.UP
    )
  }

  @Test
  fun `should successfully convert from DCT with rounding UP`() {
    testConversionFromDct(
        assetId = otherCoin,
        amountToConvert = 5,
        base = AssetAmount(10),
        quote = AssetAmount(3, otherCoin),
        expectedAmount = 2,
        roundingMode = RoundingMode.UP
    )
  }

  @Test
  fun `should successfully convert from DCT with rounding UP and switched base and quote`() {
    testConversionFromDct(
        assetId = otherCoin,
        amountToConvert = 5,
        base = AssetAmount(3, otherCoin),
        quote = AssetAmount(10),
        expectedAmount = 2,
        roundingMode = RoundingMode.UP
    )
  }

  @Test
  fun `should successfully convert to DCT with rounding DOWN`() {
    testConversionToDct(
        assetId = otherCoin,
        amountToConvert = 5,
        base = AssetAmount(10),
        quote = AssetAmount(3, otherCoin),
        expectedAmount = 16,
        roundingMode = RoundingMode.DOWN
    )
  }

  @Test
  fun `should successfully convert to DCT with rounding DOWN and switched base and quote`() {
    testConversionToDct(
        assetId = otherCoin,
        amountToConvert = 5,
        base = AssetAmount(3, otherCoin),
        quote = AssetAmount(10),
        expectedAmount = 16,
        roundingMode = RoundingMode.DOWN
    )
  }

  @Test
  fun `should successfully convert from DCT with rounding DOWN`() {
    testConversionFromDct(
        assetId = otherCoin,
        amountToConvert = 5,
        base = AssetAmount(10),
        quote = AssetAmount(3, otherCoin),
        expectedAmount = 1,
        roundingMode = RoundingMode.DOWN
    )
  }

  @Test
  fun `should successfully convert from DCT with rounding DOWN and switched base and quote`() {
    testConversionFromDct(
        assetId = otherCoin,
        amountToConvert = 5,
        base = AssetAmount(3, otherCoin),
        quote = AssetAmount(10),
        expectedAmount = 1,
        roundingMode = RoundingMode.DOWN
    )
  }

  @Test
  fun `json mapping for monitored assets options`() {
    val gson = DCoreClient.gsonBuilder.create()
    val source = """{"id":"1.3.1","symbol":"USD","precision":4,"issuer":"1.2.20","description":"US dollar","monitored_asset_opts":{"feeds":[["1.2.1948",["2019-01-26T14:08:25",{"core_exchange_rate":{"base":{"amount":539041491,"asset_id":"1.3.1"},"quote":{"amount":"100000000000000","asset_id":"1.3.0"}}}]],["1.2.2044",["2017-09-28T12:17:55",{"core_exchange_rate":{"base":{"amount":628945,"asset_id":"1.3.1"},"quote":{"amount":"10000000000","asset_id":"1.3.0"}}}]],["1.2.2064",["2017-09-11T13:00:05",{"core_exchange_rate":{"base":{"amount":69014,"asset_id":"1.3.1"},"quote":{"amount":100000,"asset_id":"1.3.0"}}}]],["1.2.3149",["2018-05-17T11:12:20",{"core_exchange_rate":{"base":{"amount":805024,"asset_id":"1.3.1"},"quote":{"amount":"10000000000","asset_id":"1.3.0"}}}]],["1.2.3466",["2017-08-29T23:00:10",{"core_exchange_rate":{"base":{"amount":122419,"asset_id":"1.3.1"},"quote":{"amount":1000000000,"asset_id":"1.3.0"}}}]],["1.2.4435",["2017-11-03T06:00:10",{"core_exchange_rate":{"base":{"amount":416409,"asset_id":"1.3.1"},"quote":{"amount":"10000000000","asset_id":"1.3.0"}}}]]],"current_feed":{"core_exchange_rate":{"base":{"amount":0,"asset_id":"1.3.0"},"quote":{"amount":0,"asset_id":"1.3.0"}}},"current_feed_publication_time":"2019-04-09T14:08:25","feed_lifetime_sec":86400,"minimum_feeds":1},"options":{"max_supply":0,"core_exchange_rate":{"base":{"amount":0,"asset_id":"1.3.0"},"quote":{"amount":0,"asset_id":"1.3.0"}},"is_exchangeable":true,"extensions":[]},"dynamic_asset_data_id":"2.3.1"}"""
    assertNotNull(gson.fromJson<Asset>(source, Asset::class.java).monitoredAssetOpts)
    val sourceWithoutMonitoredOptions = """{"id":"1.3.0","symbol":"DCT","precision":8,"issuer":"1.2.1","description":"","options":{"max_supply":"7319777577456900","core_exchange_rate":{"base":{"amount":1,"asset_id":"1.3.0"},"quote":{"amount":1,"asset_id":"1.3.0"}},"is_exchangeable":true,"extensions":[]},"dynamic_asset_data_id":"2.3.0"}"""
    assertNull(gson.fromJson<Asset>(sourceWithoutMonitoredOptions, Asset::class.java).monitoredAssetOpts)
  }

  @Test(expected = IllegalArgumentException::class)
  fun `should throw IllegalArgumentException for quote amount not greater than zero`() {
    testAsset(otherCoin, AssetAmount(3, otherCoin), AssetAmount((-1))).convertFromDCT(1, RoundingMode.DOWN)
  }

  @Test(expected = IllegalArgumentException::class)
  fun `should throw IllegalArgumentException for base amount not greater than zero`() {
    testAsset(otherCoin, AssetAmount((-1), otherCoin), AssetAmount(3)).convertFromDCT(1, RoundingMode.DOWN)
  }
}
