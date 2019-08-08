package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.NftData
import ch.decent.sdk.model.NftObjectId
import ch.decent.sdk.model.RawNft
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetNftsBalances(
    accountId: AccountObjectId,
    nftIds: List<NftObjectId>
) : BaseRequest<List<NftData<RawNft>>>(
    ApiGroup.DATABASE,
    "get_non_fungible_token_balances",
    TypeToken.getParameterized(List::class.java, TypeToken.getParameterized(NftData::class.java, RawNft::class.java).type).type,
    listOf(accountId, nftIds)
)
