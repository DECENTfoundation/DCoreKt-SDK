package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.NullObjectId
import ch.decent.sdk.model.SearchAccountHistoryOrder
import ch.decent.sdk.model.TransactionDetail
import ch.decent.sdk.model.TransactionDetailObjectId
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken

@Deprecated(message = "use SearchAccountBalanceHistory")
internal class SearchAccountHistory(
    accountId: AccountObjectId,
    order: SearchAccountHistoryOrder = SearchAccountHistoryOrder.TIME_DESC,
    startId: TransactionDetailObjectId? = null,
    limit: Int = REQ_LIMIT_MAX
) : BaseRequest<List<TransactionDetail>>(
    ApiGroup.DATABASE,
    "search_account_history",
    TypeToken.getParameterized(List::class.java, TransactionDetail::class.java).type,
    listOf(accountId, order.value, startId ?: NullObjectId, limit)
)
