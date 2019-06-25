package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup

internal class SetSubscribeCallback(clearFilter: Boolean) : BaseRequest<Unit>(
    ApiGroup.DATABASE,
    "set_subscribe_callback",
    Unit::class.java,
    listOf(clearFilter)
), WithCallback
