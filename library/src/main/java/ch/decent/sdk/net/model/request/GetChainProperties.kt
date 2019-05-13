package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainProperties
import ch.decent.sdk.net.model.ApiGroup

internal object GetChainProperties : BaseRequest<ChainProperties>(
    ApiGroup.DATABASE,
    "get_chain_properties",
    ChainProperties::class.java
)
