package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.DynamicGlobalProps
import ch.decent.sdk.net.model.ApiGroup

internal object GetDynamicGlobalProps : BaseRequest<DynamicGlobalProps>(
    ApiGroup.DATABASE,
    "get_dynamic_global_properties",
    DynamicGlobalProps::class.java
)
