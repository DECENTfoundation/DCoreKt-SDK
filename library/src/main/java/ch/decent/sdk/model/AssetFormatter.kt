package ch.decent.sdk.model

import java.math.BigDecimal
import java.math.BigInteger
import java.text.DecimalFormat

interface AssetFormatter {

  val id: ChainObject
  val symbol: String
  val precision: Int

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
   * @param maxDecimals number of decimals, by default precision of asset
   *
   * @return asset formatted string
   */
  fun format(value: BigDecimal, maxDecimals: Int = precision) = DecimalFormat.getInstance().apply { maximumFractionDigits = maxDecimals }.format(value) + " $symbol"

  /**
   * format raw value with asset symbol
   *
   * @param value raw value
   * @param maxDecimals number of decimals, by default precision of asset
   */
  fun format(value: BigInteger, maxDecimals: Int = precision) = DecimalFormat.getInstance().apply { maximumFractionDigits = maxDecimals }.format(fromRaw(value)) + " $symbol"

  fun amount(value: String): AssetAmount = AssetAmount(toRaw(BigDecimal(value)), id)
  fun amount(value: Double): AssetAmount = AssetAmount(toRaw(BigDecimal(value)), id)
  fun amount(value: BigDecimal): AssetAmount = AssetAmount(toRaw(value), id)
}
