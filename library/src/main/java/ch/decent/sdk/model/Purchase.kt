package ch.decent.sdk.model

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import java.math.BigInteger

data class Purchase(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("consumer") val author: String,
    @SerializedName("URI") val uri: String,
    @SerializedName("synopsis") val synopsisJson: String,
    @SerializedName("price") val price: AssetAmount,
    @SerializedName("paid_price_before_exchange") val priceBefore: AssetAmount,
    @SerializedName("paid_price_after_exchange") val priceAfter: AssetAmount,
    @SerializedName("seeders_answered") val seedersAnswered: List<ChainObject>,
    @SerializedName("size") val size: Int,
    @SerializedName("rating") val rating: BigInteger,
    @SerializedName("comment") val comment: String,
    @SerializedName("expiration_time") val expiration: LocalDateTime,
    @SerializedName("pubKey") val pubElGamalKey: PubKey?,
    @SerializedName("key_particles") val keyParticles: List<KeyParts>,
    @SerializedName("expired") val expired: Boolean,
    @SerializedName("delivered") val delivered: Boolean,
    @SerializedName("expiration_or_delivery_time") val deliveryExpiration: LocalDateTime,
    @SerializedName("rated_or_commented") val ratedOrCommented: Boolean,
    @SerializedName("created") val created: LocalDateTime,
    @SerializedName("region_code_from") val regionFrom: Int
) {

  fun synopsis(): Synopsis =
      GsonBuilder()
          .registerTypeAdapter(ChainObject::class.java, ChainObjectAdapter)
          .create()
          .fromJson(synopsisJson, Synopsis::class.java)

}