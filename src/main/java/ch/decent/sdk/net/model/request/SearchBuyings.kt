package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.model.SearchPurchasesOrder
import com.google.gson.reflect.TypeToken
import kotlin.math.max
import kotlin.math.min

internal class SearchBuyings(
    consumer: ChainObject,
    order: SearchPurchasesOrder = SearchPurchasesOrder.PURCHASED_DESC,
    startId: ChainObject = ObjectType.NULL_OBJECT.genericId,
    term: String = "",
    limit: Int = 100
): BaseRequest<List<Purchase>>(
    ApiGroup.DATABASE,
    "get_buying_objects_by_consumer",
    TypeToken.getParameterized(List::class.java, Purchase::class.java).type,
    listOf(consumer.objectId, order.value, startId.objectId, term, max(0, min(100, limit)))
) {

  init {
    require(consumer.objectType == ObjectType.ACCOUNT_OBJECT)
    require(startId == ObjectType.NULL_OBJECT.genericId || startId.objectType == ObjectType.BUYING_OBJECT)
  }
}