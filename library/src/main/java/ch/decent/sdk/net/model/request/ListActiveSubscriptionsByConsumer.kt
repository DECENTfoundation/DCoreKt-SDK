package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Subscription
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class ListActiveSubscriptionsByConsumer(
    accountId: AccountObjectId,
    count: Int
) : BaseRequest<List<Subscription>>(
    ApiGroup.DATABASE,
    "list_active_subscriptions_by_consumer",
    TypeToken.getParameterized(List::class.java, Subscription::class.java).type,
    listOf(accountId, count)
)
