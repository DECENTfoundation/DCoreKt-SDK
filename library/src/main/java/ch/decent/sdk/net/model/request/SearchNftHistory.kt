package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.NftDataObjectId
import ch.decent.sdk.model.TransactionDetail
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class SearchNftHistory(
    nftDataId: NftDataObjectId
) : BaseRequest<List<TransactionDetail>>(
    ApiGroup.DATABASE,
    "search_non_fungible_token_history",
    TypeToken.getParameterized(List::class.java, TransactionDetail::class.java).type,
    listOf(nftDataId)
)
