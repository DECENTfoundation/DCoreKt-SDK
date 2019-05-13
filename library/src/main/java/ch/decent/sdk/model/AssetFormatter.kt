package ch.decent.sdk.model

import java.math.BigDecimal
import java.math.BigInteger
import java.text.DecimalFormat
import java.text.NumberFormat

interface AssetFormatter {

  val id: ChainObject
  val symbol: String
  val precision: Int

  /**
   * default formatter with max number of decimals per precision of asset
   */
  val defaultFormatter: NumberFormat
    get() = DecimalFormat.getInstance().apply { maximumFractionDigits = precision }

  /**
   * get asset unit value, eg. 100000000 = 1DCT
   *
   * @param value raw value
   *
   * @return asset unit decimal value according to precision
   */
  fun fromRaw(value: BigInteger): BigDecimal = value.toBigDecimal().divide(BigDecimal.TEN.pow(precision))

  /**
   * get raw value, eg. 1DCT = 100000000
   *
   * @param value asset decimal value
   *
   * @return raw value
   */
  fun toRaw(value: BigDecimal): BigInteger = value.multiply(BigDecimal.TEN.pow(precision)).toBigInteger()

  /**
   * format asset unit value with asset symbol
   *
   * @param value asset unit value
   * @param formatter formatter to use for numeral value
   *
   * @return asset formatted string
   */
  fun format(value: BigDecimal, formatter: NumberFormat) = formatter.format(value) + " $symbol"

  /**
   * format asset unit value with asset symbol
   *
   * @param value asset unit value
   * @param formatter default formatter modifier function
   *
   * @return asset formatted string
   */
  fun format(value: BigDecimal, formatter: NumberFormat.() -> Unit = {}) = defaultFormatter.apply(formatter).format(value) + " $symbol"

  /**
   * format raw value with asset symbol
   *
   * @param value raw value
   * @param formatter formatter to use for numeral value
   *
   * @return asset formatted string
   */
  fun format(value: BigInteger, formatter: NumberFormat) = formatter.format(fromRaw(value)) + " $symbol"

  /**
   * format raw value with asset symbol
   *
   * @param value raw value
   * @param formatter default formatter modifier function
   *
   * @return asset formatted string
   */
  fun format(value: BigInteger, formatter: NumberFormat.() -> Unit = {}) = defaultFormatter.apply(formatter).format(fromRaw(value)) + " $symbol"

  /**
   * parse string unit asset value to AssetAmount
   *
   * @param value asset unit value in string representation
   *
   * @return AssetAmount with raw value and asset id
   */
  fun amount(value: String): AssetAmount = AssetAmount(toRaw(BigDecimal(value)), id)

  /**
   * parse decimal unit asset value to AssetAmount
   *
   * @param value decimal unit value in string representation
   *
   * @return AssetAmount with raw value and asset id
   */
  fun amount(value: BigDecimal): AssetAmount = AssetAmount(toRaw(value), id)
}
