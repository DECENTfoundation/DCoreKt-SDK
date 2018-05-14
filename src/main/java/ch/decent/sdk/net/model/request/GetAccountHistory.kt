package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.OperationHistory
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken
import kotlin.math.max
import kotlin.math.min

internal class GetAccountHistory(
    accountId: ChainObject,
    stopId: ChainObject = ChainObject.parse("1.7.0"),
    limit: Int = 100,
    startId: ChainObject = ChainObject.parse("1.7.0")
) : BaseRequest<List<OperationHistory>>(
    ApiGroup.HISTORY,
    "get_account_history",
    TypeToken.getParameterized(List::class.java, OperationHistory::class.java).type,
    listOf(accountId.objectId, stopId.objectId, max(0, min(100, limit)), startId.objectId)
) {

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT)
    require(startId == ChainObject.NONE || startId.objectType == ObjectType.OPERATION_HISTORY_OBJECT)
  }
}