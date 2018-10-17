package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.OperationHistory
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken
import kotlin.math.max
import kotlin.math.min

internal class GetRelativeAccountHistory(
    accountId: ChainObject,
    stop: Int = 0,
    limit: Int = 100,
    start: Int = 0
) : BaseRequest<List<OperationHistory>>(
    ApiGroup.HISTORY,
    "get_relative_account_history",
    TypeToken.getParameterized(List::class.java, OperationHistory::class.java).type,
    listOf(accountId.objectId, stop, max(0, min(100, limit)), start)
) {

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
  }
}