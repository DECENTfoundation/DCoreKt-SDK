package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup
import java.math.BigInteger

//@UInt64, capped by max instance id so Long is safe
internal object GetNftCount : BaseRequest<Long>(
    ApiGroup.DATABASE,
    "get_non_fungible_token_count",
    Long::class.java
)
