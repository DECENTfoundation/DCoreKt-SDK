package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.NftData
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.RawNft
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetNftData(
    nftDataIds: List<ChainObject>
) : BaseRequest<List<NftData<RawNft>>>(
    ApiGroup.DATABASE,
    "get_non_fungible_token_data",
    TypeToken.getParameterized(List::class.java, TypeToken.getParameterized(NftData::class.java, RawNft::class.java).type).type,
    listOf(nftDataIds)
) {

  init {
    require(nftDataIds.all { it.objectType == ObjectType.NFT_DATA_OBJECT }) { "not a valid nft data object id" }
  }
}
