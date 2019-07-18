package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import kotlin.reflect.KClass

data class NftData<T : NftModel>(
    @SerializedName("id") val id: NftDataObjectId,
    @SerializedName("nft_id") val nftId: NftObjectId,
    @SerializedName("owner") val owner: AccountObjectId,
    @SerializedName("data") val data: T?
) {

  constructor(other: NftData<RawNft>, clazz: KClass<T>) : this(other.id, other.nftId, other.owner, other.data?.make(clazz))
}
