package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.AssetObjectId
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetAccountBalances(
    accountId: AccountObjectId,
    assets: List<AssetObjectId> = emptyList()
) : BaseRequest<List<AssetAmount>>(
    ApiGroup.DATABASE,
    "get_account_balances",
    TypeToken.getParameterized(List::class.java, AssetAmount::class.java).type,
    listOf(accountId, assets)
)
