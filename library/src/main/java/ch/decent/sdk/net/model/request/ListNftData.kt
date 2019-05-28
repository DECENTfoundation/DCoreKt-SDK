package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.NftData
import ch.decent.sdk.model.NftModel
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class ListNftData<T : NftModel>(
    nftId: ChainObject
) : BaseRequest<List<NftData<T>>>(
    ApiGroup.DATABASE,
    "list_non_fungible_token_data",
    TypeToken.getParameterized(List::class.java, NftData::class.java).type,
    listOf(nftId)
) {

  init {
    require(nftId.objectType == ObjectType.NFT_OBJECT) { "not a valid nft object id" }
  }
}
