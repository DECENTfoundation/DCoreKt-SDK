package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken

internal class ListPublishingManagers(
    lowerBound: String,
    limit: Int = REQ_LIMIT_MAX
) : BaseRequest<List<ChainObject>>(
    ApiGroup.DATABASE,
    "list_publishing_managers",
    TypeToken.getParameterized(List::class.java, ChainObject::class.java).type,
    listOf(lowerBound, limit)
)
