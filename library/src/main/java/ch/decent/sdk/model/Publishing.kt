package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class Publishing(
    @SerializedName("is_publishing_manager") val isPublishingManager: Boolean,
    @SerializedName("publishing_rights_received") val publishRightsReceived: List<Any>,
    @SerializedName("publishing_rights_forwarded") val publishRightsForwarded: List<Any>
)
