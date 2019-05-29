package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class Subscription(
    @SerializedName("id") val id: SubscriptionObjectId,
    @SerializedName("from") val from: AccountObjectId,
    @SerializedName("to") val to: AccountObjectId,
    @SerializedName("expiration") val expiration: LocalDateTime,
    @SerializedName("automatic_renewal") val renewal: Boolean
)
