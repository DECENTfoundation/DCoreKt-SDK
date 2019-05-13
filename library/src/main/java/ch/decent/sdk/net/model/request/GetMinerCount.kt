package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup

internal object GetMinerCount: BaseRequest<Long>(
    ApiGroup.DATABASE,
    "get_miner_count",
    Long::class.java
)
