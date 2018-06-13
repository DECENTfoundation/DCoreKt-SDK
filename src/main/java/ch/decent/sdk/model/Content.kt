package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class Content(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("author") val author: String,
    @SerializedName("price") val price: PricePerRegion,
    @SerializedName("synopsis") val synopsis: String,
//    @SerializedName("status") val status: String,
    @SerializedName("URI") val uri: String,
    @SerializedName("_hash") val hash: String,
    @SerializedName("AVG_rating") val rating: Int,
    @SerializedName("size") val size: Long,
    @SerializedName("expiration") val expiration: LocalDateTime,
    @SerializedName("created") val created: LocalDateTime,
    @SerializedName("times_bought") val timesBought: Int
) {

  data class PricePerRegion(
      @SerializedName("map_price") val prices: Map<Int, AssetAmount>
  )

  fun price() = price.prices[regionNone]!!

  companion object {
    const val regionNone = 1
  }
}