package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup

internal class SetContentUpdateCallback(uri: String) : BaseRequest<Unit>(
    ApiGroup.DATABASE,
    "set_content_update_callback",
    Unit::class.java,
    listOf(uri)
), WithCallback
