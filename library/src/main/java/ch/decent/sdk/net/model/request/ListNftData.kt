package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.NftData
import ch.decent.sdk.model.NftObjectId
import ch.decent.sdk.model.RawNft
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class ListNftData(
    nftId: NftObjectId
) : BaseRequest<List<NftData<RawNft>>>(
    ApiGroup.DATABASE,
    "list_non_fungible_token_data",
    TypeToken.getParameterized(List::class.java, TypeToken.getParameterized(NftData::class.java, RawNft::class.java).type).type,
    listOf(nftId)
)
