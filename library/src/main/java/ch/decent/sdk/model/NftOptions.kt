package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName

data class NftOptions(
    @SerializedName("issuer") val issuer: ChainObject,
    @SerializedName("max_supply") @UInt32 val maxSupply: Long,
    @SerializedName("fixed_max_supply") val fixedMaxSupply: Boolean,
    @SerializedName("description") val description: String
)
