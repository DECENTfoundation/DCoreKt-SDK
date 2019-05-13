package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup

internal object GetChainId : BaseRequest<String>(
    ApiGroup.DATABASE,
    "get_chain_id",
    String::class.java
)
