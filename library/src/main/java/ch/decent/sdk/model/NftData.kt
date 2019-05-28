package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class NftData<T : NftModel>(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("nft_id") val nftId: ChainObject,
    @SerializedName("owner") val owner: ChainObject,
    @SerializedName("data") val data: T?
)
