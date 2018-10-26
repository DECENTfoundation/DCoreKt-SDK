package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.GlobalProperty
import ch.decent.sdk.net.model.ApiGroup

internal object GetGlobalProperties : BaseRequest<GlobalProperty>(
    ApiGroup.DATABASE,
    "get_global_properties",
    GlobalProperty::class.java
)