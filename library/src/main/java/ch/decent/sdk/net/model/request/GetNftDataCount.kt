package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup

//@UInt64, capped by max instance id so Long is safe
internal object GetNftDataCount : BaseRequest<Long>(
    ApiGroup.DATABASE,
    "get_non_fungible_token_data_count",
    Long::class.java
)
