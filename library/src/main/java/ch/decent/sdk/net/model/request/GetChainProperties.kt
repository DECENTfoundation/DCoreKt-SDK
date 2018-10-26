package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainProperty
import ch.decent.sdk.net.model.ApiGroup

internal object GetChainProperties: BaseRequest<ChainProperty>(
    ApiGroup.DATABASE,
    "get_chain_properties",
    ChainProperty::class.java
)