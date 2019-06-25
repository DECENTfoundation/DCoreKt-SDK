package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class KeyPart(
    @SerializedName("C1") val keyC1: PubKey,
    @SerializedName("D1") val keyD1: PubKey
)
