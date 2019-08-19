package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.model.types.UInt64
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import java.math.BigInteger

data class Purchase(
    @SerializedName("id") val id: PurchaseObjectId,
    @SerializedName("consumer") val author: String,
    @SerializedName("URI") val uri: String,
    @SerializedName("synopsis") val synopsisJson: String,
    @SerializedName("price") val price: AssetAmount,
    @SerializedName("paid_price_before_exchange") val priceBefore: AssetAmount,
    @SerializedName("paid_price_after_exchange") val priceAfter: AssetAmount,
    @SerializedName("seeders_answered") val seedersAnswered: List<AccountObjectId>,
    @SerializedName("size") @UInt64 val size: BigInteger,
    @SerializedName("rating") @UInt64 val rating: BigInteger,
    @SerializedName("comment") val comment: String,
    @SerializedName("expiration_time") val expiration: LocalDateTime,
    @SerializedName("pubKey") val pubElGamalKey: PubKey?,
    @SerializedName("key_particles") val keyParticles: List<KeyPart>,
    @SerializedName("expired") val expired: Boolean,
    @SerializedName("delivered") val delivered: Boolean,
    @SerializedName("expiration_or_delivery_time") val deliveryExpiration: LocalDateTime,
    @SerializedName("rated_or_commented") val ratedOrCommented: Boolean,
    @SerializedName("created") val created: LocalDateTime,
    @SerializedName("region_code_from") @UInt32 val regionFrom: Long
) {

  fun synopsis(): Synopsis = Gson().fromJson(synopsisJson, Synopsis::class.java)

}
