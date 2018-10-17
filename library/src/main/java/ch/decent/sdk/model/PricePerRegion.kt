package ch.decent.sdk.model

import ch.decent.sdk.net.serialization.ByteSerializable
import ch.decent.sdk.net.serialization.bytes
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName

data class RegionalPrice(
    @SerializedName("price") val prices: AssetAmount,
    @SerializedName("region") val region: Int
) : ByteSerializable {
  override val bytes: ByteArray
    get() = Bytes.concat(
        region.bytes(),
        prices.bytes
    )
}

data class PricePerRegion(
    @SerializedName("map_price") val prices: Map<Int, AssetAmount>
)

enum class Regions(val code: String) {
  NULL("null"),
  NONE(""),
  ALL("default");

  val id = ordinal
}