package ch.decent.sdk.model

import org.junit.Assert
import org.junit.Test
import java.math.BigInteger
import java.math.RoundingMode

class AssetTest {

  private val otherId = "1.3.4".toChainObject()

  private fun testAsset(precision: Int, base: AssetAmount, quote: AssetAmount) =
    Asset(
        id = otherId,
        precision = precision,
        options = Asset.Options(
            exchangeRate = Asset.ExchangeRate(
                base = base,
                quote = quote
            ),
            exchangeable = true
        )
    )

  private fun testConversion(
      amountToConvert: BigInteger,
      precision: Int,
      base: AssetAmount,
      quote: AssetAmount,
      expectedAmount: BigInteger,
      roundingMode: RoundingMode? = null
  ) {
    val testAsset = testAsset(precision, base, quote)
    val assetAmountToConvert = AssetAmount(amountToConvert)
    val convertedAmount =
        roundingMode?.let { testAsset.convert(assetAmountToConvert, it) } ?: testAsset.convert(assetAmountToConvert)
    Assert.assertEquals(expectedAmount, convertedAmount.amount)
  }

  @Test
  fun `should successfully convert`() {
    testConversion(
        amountToConvert = 4.toBigInteger(),
        precision = 4,
        base = AssetAmount(2.toBigInteger()),
        quote = AssetAmount(1.toBigInteger(), otherId),
        expectedAmount = 2.toBigInteger()
    )
  }

  @Test
  fun `should successfully convert when base is in other asset`() {
    testConversion(
        amountToConvert = 4.toBigInteger(),
        precision = 4,
        base = AssetAmount(2.toBigInteger(), otherId),
        quote = AssetAmount(1.toBigInteger()),
        expectedAmount = 8.toBigInteger()
    )
  }

  @Test
  fun `should successfully convert when precision of one token is higher using default rounding`() {
    testConversion(
        amountToConvert = 1.toBigInteger(),
        precision = 1,
        base = AssetAmount(10.toBigInteger()),
        quote = AssetAmount(1.toBigInteger(), otherId),
        expectedAmount = 1.toBigInteger()
    )
  }

  @Test
  fun `should successfully convert when precision of one token is higher using rounding down`() {
    testConversion(
        amountToConvert = 1.toBigInteger(),
        precision = 1,
        base = AssetAmount(10.toBigInteger()),
        quote = AssetAmount(1.toBigInteger(), otherId),
        expectedAmount = 0.toBigInteger(),
        roundingMode = RoundingMode.DOWN
    )
  }

  @Test
  fun `should successfully convert when precision of one token is higher using rounding up`() {
    testConversion(
        amountToConvert = 1.toBigInteger(),
        precision = 1,
        base = AssetAmount(10.toBigInteger()),
        quote = AssetAmount(1.toBigInteger(), otherId),
        expectedAmount = 1.toBigInteger(),
        roundingMode = RoundingMode.UP
    )
  }

  @Test(expected = IllegalArgumentException::class)
  fun `checks for supported asset conversion`() {
    val testAsset = Asset(
        id = "1.3.2".toChainObject(),
        options = Asset.Options(
            exchangeable = true
        )
    )

    testAsset.convert(AssetAmount(1.toBigInteger(), "1.3.1".toChainObject()))
  }
}