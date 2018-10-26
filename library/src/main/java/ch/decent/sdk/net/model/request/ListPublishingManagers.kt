package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class ListPublishingManagers(
    lowerBound: String,
    limit: Int = 100
) : BaseRequest<List<ChainObject>>(
    ApiGroup.DATABASE,
    "list_publishing_managers",
    TypeToken.getParameterized(List::class.java, ChainObject::class.java).type,
    listOf(lowerBound, limit)
)