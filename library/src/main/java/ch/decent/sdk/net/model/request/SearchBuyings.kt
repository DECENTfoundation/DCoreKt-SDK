package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.NullObjectId
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.model.PurchaseObjectId
import ch.decent.sdk.model.SearchPurchasesOrder
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken

internal class SearchBuyings(
    consumer: AccountObjectId,
    order: SearchPurchasesOrder = SearchPurchasesOrder.PURCHASED_DESC,
    startId: PurchaseObjectId? = null,
    term: String = "",
    limit: Int = REQ_LIMIT_MAX
) : BaseRequest<List<Purchase>>(
    ApiGroup.DATABASE,
    "get_buying_objects_by_consumer",
    TypeToken.getParameterized(List::class.java, Purchase::class.java).type,
    listOf(consumer, order.value, startId ?: NullObjectId, term, limit)
)
