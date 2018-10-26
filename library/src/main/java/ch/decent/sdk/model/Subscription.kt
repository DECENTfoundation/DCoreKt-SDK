package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class Subscription(
    @SerializedName("from") val from: ChainObject,
    @SerializedName("to") val to: ChainObject,
    @SerializedName("expiration") val expiration: LocalDateTime,
    @SerializedName("automatic_renewal") val renewal: Boolean
)