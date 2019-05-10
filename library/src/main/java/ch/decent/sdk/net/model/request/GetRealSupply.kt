package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.RealSupply
import ch.decent.sdk.net.model.ApiGroup

internal object GetRealSupply : BaseRequest<RealSupply>(
    ApiGroup.DATABASE,
    "get_real_supply",
    RealSupply::class.java
)
