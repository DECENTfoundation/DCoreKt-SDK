package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName

data class RegionalPrice(
    @SerializedName("price") val price: AssetAmount,
    @SerializedName("region") @UInt32 val region: Long
)

data class PricePerRegion(
    @SerializedName("map_price") val prices: Map<Long, AssetAmount>
)

enum class Regions(val code: String) {
  NULL("null"),
  NONE(""),
  ALL("default");

  val id = ordinal.toLong()
}
