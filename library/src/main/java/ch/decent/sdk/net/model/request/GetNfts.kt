package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Nft
import ch.decent.sdk.model.NftObjectId
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetNfts(
    nftIds: List<NftObjectId>
) : BaseRequest<List<Nft>>(
    ApiGroup.DATABASE,
    "get_non_fungible_tokens",
    TypeToken.getParameterized(List::class.java, Nft::class.java).type,
    listOf(nftIds)
)
