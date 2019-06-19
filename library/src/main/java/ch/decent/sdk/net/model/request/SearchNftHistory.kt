package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.TransactionDetail
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class SearchNftHistory(
    nftDataId: ChainObject
    ) : BaseRequest<List<TransactionDetail>>(
    ApiGroup.DATABASE,
    "search_non_fungible_token_history",
    TypeToken.getParameterized(List::class.java, TransactionDetail::class.java).type,
    listOf(nftDataId)
) {

  init {
    require(nftDataId.objectType == ObjectType.NFT_DATA_OBJECT ) { "not a valid nft data object id" }
  }
}
