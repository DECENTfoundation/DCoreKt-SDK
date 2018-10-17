package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken
import kotlin.math.max
import kotlin.math.min

internal class SearchAccountBalanceHistory(
    accountId: ChainObject,
    assets: List<ChainObject> = emptyList(),
    recipientAccount: ChainObject? = null,
    fromBlock: Long = 0,
    toBlock: Long = 0,
    startOffset: Long = 0,
    limit: Int = 100
) : BaseRequest<List<BalanceChange>>(
    ApiGroup.HISTORY,
    "search_account_balance_history",
    TypeToken.getParameterized(List::class.java, BalanceChange::class.java).type,
    listOf(accountId, assets, recipientAccount, fromBlock, toBlock, startOffset, limit)
)