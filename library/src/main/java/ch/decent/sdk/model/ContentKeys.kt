package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class ContentKeys(
    @SerializedName("key") val key: String,
    @SerializedName("parts") val keyParts: List<KeyParts>
)