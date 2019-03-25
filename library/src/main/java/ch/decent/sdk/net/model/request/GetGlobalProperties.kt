package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.GlobalProperties
import ch.decent.sdk.net.model.ApiGroup

internal object GetGlobalProperties : BaseRequest<GlobalProperties>(
    ApiGroup.DATABASE,
    "get_global_properties",
    GlobalProperties::class.java
)