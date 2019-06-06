package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetObjectId
import ch.decent.sdk.model.BalanceChange
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken

internal class SearchAccountBalanceHistory(
    accountId: AccountObjectId,
    assets: List<AssetObjectId> = emptyList(),
    recipientAccount: AccountObjectId? = null,
    fromBlock: Long = 0,
    toBlock: Long = 0,
    startOffset: Long = 0,
    limit: Int = REQ_LIMIT_MAX
) : BaseRequest<List<BalanceChange>>(
    ApiGroup.HISTORY,
    "search_account_balance_history",
    TypeToken.getParameterized(List::class.java, BalanceChange::class.java).type,
    listOf(accountId, assets, recipientAccount, fromBlock, toBlock, startOffset, limit)
)
