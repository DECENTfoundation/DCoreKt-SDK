package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName

data class CustodyData(
    @SerializedName("n") @UInt32 val n: Long,
    @SerializedName("u_seed") val seed: String, // Fixed size 16 bytes (32 chars string)
    @SerializedName("pubKey") val pubKey: String // Fixed size 33 bytes (66 chars string)
)
