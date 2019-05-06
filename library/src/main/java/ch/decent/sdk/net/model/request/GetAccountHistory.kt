package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.OperationHistory
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken
import kotlin.math.max
import kotlin.math.min

internal class GetAccountHistory(
    accountId: ChainObject,
    stopId: ChainObject = ObjectType.OPERATION_HISTORY_OBJECT.genericId,
    limit: Int = REQ_LIMIT_MAX,
    startId: ChainObject = ObjectType.OPERATION_HISTORY_OBJECT.genericId
) : BaseRequest<List<OperationHistory>>(
    ApiGroup.HISTORY,
    "get_account_history",
    TypeToken.getParameterized(List::class.java, OperationHistory::class.java).type,
    listOf(accountId.objectId, stopId.objectId, max(0, limit), startId.objectId)
) {

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(startId.objectType == ObjectType.OPERATION_HISTORY_OBJECT) { "not a valid history object id" }
    require(stopId.objectType == ObjectType.OPERATION_HISTORY_OBJECT) { "not a valid history object id" }
  }
}
