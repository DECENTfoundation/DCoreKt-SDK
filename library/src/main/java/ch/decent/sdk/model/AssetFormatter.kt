package ch.decent.sdk.model

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat

interface AssetFormatter {

  val id: ChainObject
  val symbol: String
  val precision: Short

  /**
   * default formatter with max number of decimals per precision of asset
   */
  val defaultFormatter: NumberFormat
    get() = DecimalFormat.getInstance().apply { maximumFractionDigits = precision.toInt() }

  /**
   * get asset unit value, eg. 100000000 = 1DCT
   *
   * @param value raw value
   *
   * @return asset unit decimal value according to precision
   */
  fun fromRaw(value: Long): Double = value.toDouble().div(Math.pow(10.0, precision.toDouble()))

  /**
   * get raw value, eg. 1DCT = 100000000
   *
   * @param value asset decimal value
   *
   * @return raw value
   */
  fun toRaw(value: Double): Long = value.times(Math.pow(10.0, precision.toDouble())).toLong()

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
  fun format(value: Double, formatter: NumberFormat.() -> Unit = {}) = defaultFormatter.apply(formatter).format(value) + " $symbol"

  /**
   * format raw value with asset symbol
   *
   * @param value raw value
   * @param formatter formatter to use for numeral value
   *
   * @return asset formatted string
   */
  fun format(value: Long, formatter: NumberFormat) = formatter.format(fromRaw(value)) + " $symbol"

  /**
   * format raw value with asset symbol
   *
   * @param value raw value
   * @param formatter default formatter modifier function
   *
   * @return asset formatted string
   */
  fun format(value: Long, formatter: NumberFormat.() -> Unit = {}) = defaultFormatter.apply(formatter).format(fromRaw(value)) + " $symbol"

  /**
   * parse string unit asset value to AssetAmount
   *
   * @param value asset unit value in string representation
   *
   * @return AssetAmount with raw value and asset id
   */
  fun amount(value: String): AssetAmount = AssetAmount(toRaw(value.toDouble()), id)

  /**
   * parse decimal unit asset value to AssetAmount
   *
   * @param value decimal unit value in string representation
   *
   * @return AssetAmount with raw value and asset id
   */
  fun amount(value: Double): AssetAmount = AssetAmount(toRaw(value), id)
}
