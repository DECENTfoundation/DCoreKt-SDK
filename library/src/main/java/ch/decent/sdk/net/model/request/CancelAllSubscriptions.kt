package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup

internal object CancelAllSubscriptions: BaseRequest<Unit>(
    ApiGroup.DATABASE,
    "cancel_all_subscriptions",
    Unit::class.java
)
