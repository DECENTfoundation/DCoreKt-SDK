package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Nft
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetNfts(
    nftIds: List<ChainObject>
) : BaseRequest<List<Nft>>(
    ApiGroup.DATABASE,
    "get_non_fungible_tokens",
    TypeToken.getParameterized(List::class.java, Nft::class.java).type,
    listOf(nftIds)
) {

  init {
    require(nftIds.all { it.objectType == ObjectType.NFT_OBJECT }) { "not a valid nft object id" }
  }
}
