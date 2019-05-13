package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup

internal object SetBlockAppliedCallback : BaseRequest<String>(
    ApiGroup.DATABASE,
    "set_block_applied_callback",
    String::class.java
), WithCallback
