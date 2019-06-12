package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.GenericNft
import ch.decent.sdk.model.NftData
import ch.decent.sdk.model.NftModel
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetNftData(
    nftDataIds: List<ChainObject>
) : BaseRequest<List<NftData<GenericNft>>>(
    ApiGroup.DATABASE,
    "get_non_fungible_token_data",
    TypeToken.getParameterized(List::class.java, NftData::class.java).type,
    listOf(nftDataIds)
) {

  init {
    require(nftDataIds.all { it.objectType == ObjectType.NFT_DATA_OBJECT }) { "not a valid nft data object id" }
  }
}
