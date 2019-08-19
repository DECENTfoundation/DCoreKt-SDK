package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class ChainProperties(
    @SerializedName("id") val id: ChainPropertyObjectId,
    @SerializedName("chain_id") val chainId: String,
    @SerializedName("immutable_parameters") val parameters: ChainParameters
)
