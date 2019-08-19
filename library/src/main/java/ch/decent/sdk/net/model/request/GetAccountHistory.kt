package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.OperationHistory
import ch.decent.sdk.model.OperationHistoryObjectId
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken
import kotlin.math.max

internal class GetAccountHistory(
    accountId: AccountObjectId,
    stopId: OperationHistoryObjectId = OperationHistoryObjectId(),
    limit: Int = REQ_LIMIT_MAX,
    startId: OperationHistoryObjectId = OperationHistoryObjectId()
) : BaseRequest<List<OperationHistory>>(
    ApiGroup.HISTORY,
    "get_account_history",
    TypeToken.getParameterized(List::class.java, OperationHistory::class.java).type,
    listOf(accountId, stopId, max(0, limit), startId)
)
