package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken
import kotlin.math.max
import kotlin.math.min

internal class SearchContent(
    term: String,
    order: SearchContentOrder = SearchContentOrder.CREATED_DESC,
    user: String,
    regionCode: String,
    type: String,
    startId: ChainObject = ObjectType.NULL_OBJECT.genericId,
    limit: Int = 100
): BaseRequest<List<Content>>(
    ApiGroup.DATABASE,
    "search_content",
    TypeToken.getParameterized(List::class.java, Purchase::class.java).type,
    listOf(term, order.value, user, regionCode, type, startId, limit)
)