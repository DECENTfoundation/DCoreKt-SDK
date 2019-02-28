package ch.decent.sdk.model

import ch.decent.sdk.DCoreConstants.DCT_ASSET_ID
import ch.decent.sdk.utils.toBigInteger
import com.google.gson.annotations.SerializedName
import java.math.RoundingMode

data class Asset(
    @SerializedName("id") override val id: ChainObject = ObjectType.ASSET_OBJECT.genericId,
    @SerializedName("symbol") override val symbol: String = "UIA",
    @SerializedName("precision") override val precision: Int = 0,
    @SerializedName("issuer") val issuer: ChainObject = ObjectType.NULL_OBJECT.genericId,
    @SerializedName("description") val description: String = "",
    @SerializedName("options") val options: Options = Options(),
    @SerializedName("dynamic_asset_data_id") val dataId: ChainObject = ObjectType.NULL_OBJECT.genericId
): AssetFormatter {

  init {
    check(id.objectType == ObjectType.ASSET_OBJECT)
  }

  /**
   * Convert asset amount to/from DCT. Conversions between arbitrary assets are not supported
   *
   * @param assetAmount asset amount to convert. Conversion will be made to assetId of this assetAmount
   * @param roundingMode rounding mode to use when resolving remainder of conversion
   *
   * @return converted asset amount in requested asset id
   */
  fun convert(assetAmount: AssetAmount, roundingMode: RoundingMode): AssetAmount {
    if (options.exchangeRate.base.assetId == assetAmount.assetId) {
      val amount = options.exchangeRate.quote.amount.toBigDecimal().divide(options.exchangeRate.base.amount.toBigDecimal()) * assetAmount.amount.toBigDecimal()
      return AssetAmount(amount.toBigInteger(roundingMode), id)
    }
    if (options.exchangeRate.quote.assetId == assetAmount.assetId) {
      val amount = options.exchangeRate.base.amount.toBigDecimal().divide(options.exchangeRate.quote.amount.toBigDecimal()) * assetAmount.amount.toBigDecimal()
      return AssetAmount(amount.toBigInteger(roundingMode), id)
    }
    throw IllegalArgumentException("cannot convert ${assetAmount.assetId} with $symbol:$id")
  }

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
