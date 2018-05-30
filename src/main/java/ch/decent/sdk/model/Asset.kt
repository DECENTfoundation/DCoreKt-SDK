package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.math.BigInteger
import java.text.DecimalFormat

data class Asset(
    @SerializedName("id") val id: ChainObject = ObjectType.ASSET_OBJECT.genericId,
    @SerializedName("symbol") val symbol: String = "UIA",
    @SerializedName("precision") val precision: Int = 0,
    @SerializedName("issuer") val issuer: ChainObject = ChainObject.NONE,
    @SerializedName("description") val description: String = "",
    @SerializedName("options") val options: Options = Options(),
    @SerializedName("dynamic_asset_data_id") val dataId: ChainObject = ChainObject.NONE
) {

  init {
    check(id.objectType == ObjectType.ASSET_OBJECT)
  }

  fun fromBase(value: BigInteger): BigDecimal = value.toBigDecimal().divide(BigDecimal.TEN.pow(precision))
  fun toBase(value: BigDecimal): BigInteger = value.multiply(BigDecimal.TEN.pow(precision)).toBigInteger()

  fun format(value: BigDecimal, maxDecimals: Int = precision) = DecimalFormat.getInstance().apply { maximumFractionDigits = maxDecimals }.format(value) + " $symbol"
  fun format(value: BigInteger, maxDecimals: Int = precision) = DecimalFormat.getInstance().apply { maximumFractionDigits = maxDecimals }.format(fromBase(value)) + " $symbol"

  fun amount(value: String): AssetAmount = AssetAmount(toBase(BigDecimal(value)), id)
  fun amount(value: Double): AssetAmount = AssetAmount(toBase(BigDecimal(value)), id)

  data class Options(
      @SerializedName("max_supply") val maxSupply: Long = 0,
      @SerializedName("core_exchange_rate") val exchangeRate: ExchangeRate = ExchangeRate(),
      @SerializedName("is_exchangeable") val exchangeable: Boolean = false,
      @SerializedName("extensions") val extensions: List<Any> = emptyList()
  )

  data class ExchangeRate(
      @SerializedName("base") val base: AssetAmount = AssetAmount(1),
      @SerializedName("quote") val quote: AssetAmount = AssetAmount(1)
  )
}
