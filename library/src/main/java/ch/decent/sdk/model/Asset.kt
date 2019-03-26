package ch.decent.sdk.model

import ch.decent.sdk.DCoreConstants
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

data class Asset(
    @SerializedName("id") override val id: ChainObject = ObjectType.ASSET_OBJECT.genericId,
    @SerializedName("symbol") override val symbol: String = "UIA",
    @SerializedName("precision") override val precision: Int = 0,
    @SerializedName("issuer") val issuer: ChainObject = ObjectType.NULL_OBJECT.genericId,
    @SerializedName("description") val description: String = "",
    @SerializedName("options") val options: Options = Options(),
    @SerializedName("dynamic_asset_data_id") val dataId: ChainObject = ObjectType.NULL_OBJECT.genericId
) : AssetFormatter {

  init {
    check(id.objectType == ObjectType.ASSET_OBJECT)
  }

  /**
   * Converts DCT [amount] according conversion rate.
   * Throws an [IllegalArgumentException] if the quote or base [amount] is not greater then zero.
   */
  fun convertFromDCT(amount: BigInteger, roundingMode: RoundingMode) = convert(amount, id, roundingMode)

  /**
   * Converts asset [amount] to DCT according conversion rate.
   * Throws an [IllegalArgumentException] if the quote or base [amount] is not greater then zero.
   */
  fun convertToDCT(amount: BigInteger, roundingMode: RoundingMode) = convert(amount, DCoreConstants.DCT_ASSET_ID, roundingMode)

  /**
   * Method convert [amount] to [toAssetId] asset and returns [AssetAmount] for converted amount.
   * Throws an [IllegalArgumentException] if the quote or base [amount] is not greater then zero.
   *
   * @param amount amount to convert
   * @param toAssetId asset id
   * @param roundingMode rounding mode for converted amount
   *
   * @return [AssetAmount] for converted amount
   */
  private fun convert(amount: BigInteger, toAssetId: ChainObject, roundingMode: RoundingMode): AssetAmount {
    val quoteAmount: BigDecimal = options.exchangeRate.quote.amount.toBigDecimal()
    val baseAmount: BigDecimal = options.exchangeRate.base.amount.toBigDecimal()
    require(quoteAmount > BigDecimal.ZERO) { "Quote amount ($quoteAmount) must be greater then zero" }
    require(baseAmount > BigDecimal.ZERO) { "Base amount ($baseAmount) must be greater then zero" }
    val convertedAmount = when (toAssetId) {
      options.exchangeRate.quote.assetId -> (quoteAmount * amount.toBigDecimal()).divide(baseAmount, roundingMode)
      options.exchangeRate.base.assetId -> (baseAmount * amount.toBigDecimal()).divide(quoteAmount, roundingMode)
      else -> throw IllegalArgumentException("cannot convert ${id} with $symbol:$toAssetId")
    }
    return AssetAmount(convertedAmount.toBigInteger(), toAssetId)
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
