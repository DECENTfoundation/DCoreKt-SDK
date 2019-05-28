package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.NftData
import ch.decent.sdk.model.NftModel
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetNftsBalances<T : NftModel>(
    accountId: ChainObject,
    nftIds: List<ChainObject>
) : BaseRequest<List<NftData<T>>>(
    ApiGroup.DATABASE,
    "get_non_fungible_token_balances",
    TypeToken.getParameterized(List::class.java, NftData::class.java).type,
    listOf(accountId, nftIds)
) {

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(nftIds.all { it.objectType == ObjectType.NFT_OBJECT }) { "not a valid nft object id" }
  }
}
