package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Subscription
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class ListSubscriptionsByConsumer(
    accountId: ChainObject,
    count: Int
) : BaseRequest<List<Subscription>>(
    ApiGroup.DATABASE,
    "list_subscriptions_by_consumer",
    TypeToken.getParameterized(List::class.java, Subscription::class.java).type,
    listOf(accountId, count)
)