package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Subscription
import ch.decent.sdk.model.SubscriptionObjectId
import ch.decent.sdk.net.model.ApiGroup

internal class GetSubscription(
    subscriptionId: SubscriptionObjectId
) : BaseRequest<Subscription>(
    ApiGroup.DATABASE,
    "get_subscription",
    Subscription::class.java,
    listOf(subscriptionId)
)
