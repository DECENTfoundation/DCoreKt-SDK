package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.SearchAccountHistoryOrder
import ch.decent.sdk.model.TransactionDetail
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken
import kotlin.math.max
import kotlin.math.min

internal class SearchAccountHistory(
    accountId: ChainObject,
    order: SearchAccountHistoryOrder = SearchAccountHistoryOrder.TIME_DESC,
    startId: ChainObject = ObjectType.NULL_OBJECT.genericId,
    limit: Int = 100
) : BaseRequest<List<TransactionDetail>>(
    ApiGroup.DATABASE,
    "search_account_history",
    TypeToken.getParameterized(List::class.java, TransactionDetail::class.java).type,
    listOf(accountId.objectId, order.value, startId.objectId, max(0, min(100, limit)))
) {

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(startId == ObjectType.NULL_OBJECT.genericId || startId.objectType == ObjectType.TRANSACTION_DETAIL_OBJECT) { "not a valid null or transaction detail object id" }
  }
}