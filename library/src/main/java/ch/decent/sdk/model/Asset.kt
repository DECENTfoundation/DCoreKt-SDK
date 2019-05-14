package ch.decent.sdk.model

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.types.Int64
import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.model.types.UInt8
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import java.math.BigDecimal
import java.math.RoundingMode

data class Asset(
    @SerializedName("id") override val id: ChainObject,
    @SerializedName("symbol") override val symbol: String,
    @SerializedName("precision") @UInt8 override val precision: Short,
    @SerializedName("issuer") val issuer: ChainObject,
    @SerializedName("description") val description: String,
    @SerializedName("options") val options: AssetOptions,
    @SerializedName("dynamic_asset_data_id") val dataId: ChainObject,
    @SerializedName("monitored_asset_opts") val monitoredAssetOpts: MonitoredAssetOpts? = null
) : AssetFormatter {

  companion object {
    private val regex = Regex("(?=.{3,16}$)^[A-Z][A-Z0-9]+(\\.[A-Z0-9]*)?[A-Z]$")
    @JvmStatic fun isValidSymbol(symbol: String) = regex.matches(symbol)
  }

  init {
    check(id.objectType == ObjectType.ASSET_OBJECT)
  }

  /**
   * Converts DCT [amount] according conversion rate.
   * Throws an [IllegalArgumentException] if the quote or base [amount] is not greater then zero.
   */
  fun convertFromDCT(amount: Long, roundingMode: RoundingMode) = convert(amount, id, roundingMode)

  /**
   * Converts asset [amount] to DCT according conversion rate.
   * Throws an [IllegalArgumentException] if the quote or base [amount] is not greater then zero.
   */
  fun convertToDCT(amount: Long, roundingMode: RoundingMode) = convert(amount, DCoreConstants.DCT_ASSET_ID, roundingMode)

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
  private fun convert(amount: Long, toAssetId: ChainObject, roundingMode: RoundingMode): AssetAmount {
    val quoteAmount: BigDecimal = options.exchangeRate.quote.amount.toBigDecimal()
    val baseAmount: BigDecimal = options.exchangeRate.base.amount.toBigDecimal()
    require(quoteAmount > BigDecimal.ZERO) { "Quote amount ($quoteAmount) must be greater then zero" }
    require(baseAmount > BigDecimal.ZERO) { "Base amount ($baseAmount) must be greater then zero" }
    val convertedAmount = when (toAssetId) {
      options.exchangeRate.quote.assetId -> (quoteAmount * amount.toBigDecimal()).divide(baseAmount, roundingMode)
      options.exchangeRate.base.assetId -> (baseAmount * amount.toBigDecimal()).divide(quoteAmount, roundingMode)
      else -> throw IllegalArgumentException("cannot convert $id with $symbol:$toAssetId")
    }
    return AssetAmount(convertedAmount.toLong(), toAssetId)
  }

  data class MonitoredAssetOpts(
      @SerializedName("feeds") val feeds: List<Any>,
      @SerializedName("current_feed") val currentFeed: CurrentFeed,
      @SerializedName("current_feed_publication_time") val currentFeedPublicationTime: LocalDateTime,
      @SerializedName("feed_lifetime_sec") @UInt32 val feedLifetimeSec: Long,
      @SerializedName("minimum_feeds") @UInt8 val minimumFeeds: Short
  )

  data class CurrentFeed(
      @SerializedName("core_exchange_rate") val coreExchangeRate: ExchangeRate
  )

  data class AssetOptions(
      @SerializedName("max_supply") @Int64 val maxSupply: Long = DCoreConstants.MAX_SHARE_SUPPLY,
      @SerializedName("core_exchange_rate") val exchangeRate: ExchangeRate = ExchangeRate(AssetAmount(1), AssetAmount(1)),
      @SerializedName("is_exchangeable") val exchangeable: Boolean = true,
      @SerializedName("extensions") val extensions: List<Any> = emptyList()
  )

  data class ExchangeRate(
      @SerializedName("base") val base: AssetAmount,
      @SerializedName("quote") val quote: AssetAmount
  )
}
