package ch.decent.sdk.model

import org.amshove.kluent.`should be`
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
      roundingMode: RoundingMode = RoundingMode.UP
  ) {
    val testAsset = testAsset(precision, base, quote)
    val assetAmountToConvert = AssetAmount(amountToConvert)
    val convertedAmount = testAsset.convert(assetAmountToConvert, roundingMode)
    expectedAmount `should be` convertedAmount.amount
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
}