package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.PublisherObjectId
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken

internal class ListPublishingManagers(
    lowerBound: String,
    limit: Int = REQ_LIMIT_MAX
) : BaseRequest<List<AccountObjectId>>(
    ApiGroup.DATABASE,
    "list_publishing_managers",
    TypeToken.getParameterized(List::class.java, AccountObjectId::class.java).type,
    listOf(lowerBound, limit)
)
