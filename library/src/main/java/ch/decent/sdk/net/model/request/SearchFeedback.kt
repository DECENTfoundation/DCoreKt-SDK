package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.model.SearchPurchasesOrder
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken
import kotlin.math.max
import kotlin.math.min

internal class SearchFeedback(
    user: String?,
    uri: String,
    startId: ChainObject = ObjectType.NULL_OBJECT.genericId,
    count: Int
): BaseRequest<List<Purchase>>(
    ApiGroup.DATABASE,
    "search_feedback",
    TypeToken.getParameterized(List::class.java, Purchase::class.java).type,
    listOf(user, uri, startId, count)
)