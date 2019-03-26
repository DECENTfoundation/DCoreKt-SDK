package ch.decent.sdk.model

import ch.decent.sdk.DCoreConstants
import org.junit.Assert
import org.junit.Test
import java.math.BigInteger
import java.math.RoundingMode

class AssetTest {

  private val otherCoin = "1.3.1".toChainObject()

  private fun testAsset(assetId: ChainObject, base: AssetAmount, quote: AssetAmount) =
      Asset(
          id = assetId,
          options = Asset.Options(
              exchangeRate = Asset.ExchangeRate(
                  base = base,
                  quote = quote
              ),
              exchangeable = true
          )
      )

  private fun testConversionToDct(
      assetId: ChainObject,
      amountToConvert: BigInteger,
      base: AssetAmount,
      quote: AssetAmount,
      expectedAmount: BigInteger,
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
      amountToConvert: BigInteger,
      base: AssetAmount,
      quote: AssetAmount,
      expectedAmount: BigInteger,
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
        amountToConvert = 5.toBigInteger(),
        base = AssetAmount(10.toBigInteger()),
        quote = AssetAmount(3.toBigInteger(), otherCoin),
        expectedAmount = 17.toBigInteger(),
        roundingMode = RoundingMode.UP
    )
  }

  @Test
  fun `should successfully convert to DCT with rounding UP and switched base and quote`() {
    testConversionToDct(
        assetId = otherCoin,
        amountToConvert = 5.toBigInteger(),
        base = AssetAmount(3.toBigInteger(), otherCoin),
        quote = AssetAmount(10.toBigInteger()),
        expectedAmount = 17.toBigInteger(),
        roundingMode = RoundingMode.UP
    )
  }

  @Test
  fun `should successfully convert from DCT with rounding UP`() {
    testConversionFromDct(
        assetId = otherCoin,
        amountToConvert = 5.toBigInteger(),
        base = AssetAmount(10.toBigInteger()),
        quote = AssetAmount(3.toBigInteger(), otherCoin),
        expectedAmount = 2.toBigInteger(),
        roundingMode = RoundingMode.UP
    )
  }

  @Test
  fun `should successfully convert from DCT with rounding UP and switched base and quote`() {
    testConversionFromDct(
        assetId = otherCoin,
        amountToConvert = 5.toBigInteger(),
        base = AssetAmount(3.toBigInteger(), otherCoin),
        quote = AssetAmount(10.toBigInteger()),
        expectedAmount = 2.toBigInteger(),
        roundingMode = RoundingMode.UP
    )
  }

  @Test
  fun `should successfully convert to DCT with rounding DOWN`() {
    testConversionToDct(
        assetId = otherCoin,
        amountToConvert = 5.toBigInteger(),
        base = AssetAmount(10.toBigInteger()),
        quote = AssetAmount(3.toBigInteger(), otherCoin),
        expectedAmount = 16.toBigInteger(),
        roundingMode = RoundingMode.DOWN
    )
  }

  @Test
  fun `should successfully convert to DCT with rounding DOWN and switched base and quote`() {
    testConversionToDct(
        assetId = otherCoin,
        amountToConvert = 5.toBigInteger(),
        base = AssetAmount(3.toBigInteger(), otherCoin),
        quote = AssetAmount(10.toBigInteger()),
        expectedAmount = 16.toBigInteger(),
        roundingMode = RoundingMode.DOWN
    )
  }

  @Test
  fun `should successfully convert from DCT with rounding DOWN`() {
    testConversionFromDct(
        assetId = otherCoin,
        amountToConvert = 5.toBigInteger(),
        base = AssetAmount(10.toBigInteger()),
        quote = AssetAmount(3.toBigInteger(), otherCoin),
        expectedAmount = 1.toBigInteger(),
        roundingMode = RoundingMode.DOWN
    )
  }

  @Test
  fun `should successfully convert from DCT with rounding DOWN and switched base and quote`() {
    testConversionFromDct(
        assetId = otherCoin,
        amountToConvert = 5.toBigInteger(),
        base = AssetAmount(3.toBigInteger(), otherCoin),
        quote = AssetAmount(10.toBigInteger()),
        expectedAmount = 1.toBigInteger(),
        roundingMode = RoundingMode.DOWN
    )
  }

  @Test(expected = IllegalArgumentException::class)
  fun `should throw IllegalArgumentException for quote amount not greater than zero`() {
    testAsset(otherCoin, AssetAmount(3.toBigInteger(), otherCoin), AssetAmount((-1).toBigInteger())).convertFromDCT(1.toBigInteger(), RoundingMode.DOWN)
  }

  @Test(expected = IllegalArgumentException::class)
  fun `should throw IllegalArgumentException for base amount not greater than zero`() {
    testAsset(otherCoin, AssetAmount((-1).toBigInteger(), otherCoin), AssetAmount(3.toBigInteger())).convertFromDCT(1.toBigInteger(), RoundingMode.DOWN)
  }
}