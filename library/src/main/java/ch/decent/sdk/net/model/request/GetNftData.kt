package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.NftData
import ch.decent.sdk.model.NftDataObjectId
import ch.decent.sdk.model.RawNft
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetNftData(
    nftDataIds: List<NftDataObjectId>
) : BaseRequest<List<NftData<RawNft>>>(
    ApiGroup.DATABASE,
    "get_non_fungible_token_data",
    TypeToken.getParameterized(List::class.java, TypeToken.getParameterized(NftData::class.java, RawNft::class.java).type).type,
    listOf(nftDataIds)
)
