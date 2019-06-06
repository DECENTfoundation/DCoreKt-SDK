package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.OperationHistory
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken
import kotlin.math.max

internal class GetRelativeAccountHistory(
    accountId: AccountObjectId,
    stop: Int = 0,
    limit: Int = REQ_LIMIT_MAX,
    start: Int = 0
) : BaseRequest<List<OperationHistory>>(
    ApiGroup.HISTORY,
    "get_relative_account_history",
    TypeToken.getParameterized(List::class.java, OperationHistory::class.java).type,
    listOf(accountId, stop, max(0, limit), start)
)
