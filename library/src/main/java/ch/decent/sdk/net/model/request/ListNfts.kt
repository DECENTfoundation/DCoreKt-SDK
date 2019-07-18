package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Nft
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken

internal class ListNfts(
    lowerBound: String,
    limit: Int = REQ_LIMIT_MAX
) : BaseRequest<List<Nft>>(
    ApiGroup.DATABASE,
    "list_non_fungible_tokens",
    TypeToken.getParameterized(List::class.java, Nft::class.java).type,
    listOf(lowerBound, limit)
)
