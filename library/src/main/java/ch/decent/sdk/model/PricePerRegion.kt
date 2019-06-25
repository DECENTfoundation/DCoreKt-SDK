package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName

data class RegionalPrice(
    @SerializedName("price") val price: AssetAmount,
    @SerializedName("region") @UInt32 val region: Long = Regions.ALL.id
)

data class PricePerRegion(
    @SerializedName("map_price") val prices: Map<Long, AssetAmount>
) {
  val regionalPrice
    get() = prices.map { (region, amount) -> RegionalPrice(amount, region) }
}

enum class Regions(val code: String) {
  NULL("null"),
  NONE(""),
  ALL("default");

  val id = ordinal.toLong()
}
