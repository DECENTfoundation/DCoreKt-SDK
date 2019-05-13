package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.model.SearchPurchasesOrder
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken
import kotlin.math.max
import kotlin.math.min

internal class SearchBuyings(
    consumer: ChainObject,
    order: SearchPurchasesOrder = SearchPurchasesOrder.PURCHASED_DESC,
    startId: ChainObject = ObjectType.NULL_OBJECT.genericId,
    term: String = "",
    limit: Int = REQ_LIMIT_MAX
): BaseRequest<List<Purchase>>(
    ApiGroup.DATABASE,
    "get_buying_objects_by_consumer",
    TypeToken.getParameterized(List::class.java, Purchase::class.java).type,
    listOf(consumer.objectId, order.value, startId.objectId, term, limit)
) {

  init {
    require(consumer.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(startId == ObjectType.NULL_OBJECT.genericId || startId.objectType == ObjectType.PURCHASE_OBJECT) { "not a valid null or purchase object id" }
  }
}
