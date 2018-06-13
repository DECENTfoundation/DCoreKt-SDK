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