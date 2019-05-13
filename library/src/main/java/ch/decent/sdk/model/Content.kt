package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.model.types.UInt64
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import java.math.BigInteger

data class Content(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("author") val author: String,
    @SerializedName("price") val price: PricePerRegion,
    @SerializedName("synopsis") val synopsis: String,
    @SerializedName("status") val status: String,
    @SerializedName("URI") val uri: String,
    @SerializedName("_hash") val hash: String,
    @SerializedName("AVG_rating") @UInt32 val rating: Long,
    @SerializedName("size") @UInt64 val size: BigInteger,
    @SerializedName("expiration") val expiration: LocalDateTime,
    @SerializedName("created") val created: LocalDateTime,
    @SerializedName("times_bought") @UInt32 val timesBought: Long
) {

  // todo?
  fun price() = price.prices[Regions.NONE.id]!!
}
