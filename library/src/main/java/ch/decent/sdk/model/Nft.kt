package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName

data class Nft(
    @SerializedName("id") val id: NftObjectId,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("options") val options: NftOptions,
    @SerializedName("definitions") val definitions: List<NftDataType>,
    @SerializedName("fixed_max_supply") val fixedMaxSupply: Boolean,
    @SerializedName("transferable") val transferable: Boolean,
    @SerializedName("current_supply") @UInt32 val currentSupply: Long
)
