package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.RawNft
import ch.decent.sdk.model.NftData
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetNftsBalances(
    accountId: ChainObject,
    nftIds: List<ChainObject>
) : BaseRequest<List<NftData<RawNft>>>(
    ApiGroup.DATABASE,
    "get_non_fungible_token_balances",
    TypeToken.getParameterized(List::class.java, TypeToken.getParameterized(NftData::class.java, RawNft::class.java).type).type,
    listOf(accountId, nftIds)
) {

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(nftIds.all { it.objectType == ObjectType.NFT_OBJECT }) { "not a valid nft object id" }
  }
}
