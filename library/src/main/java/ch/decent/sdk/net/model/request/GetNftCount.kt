package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup
import java.math.BigInteger

//@UInt64
internal object GetNftCount : BaseRequest<BigInteger>(
    ApiGroup.DATABASE,
    "get_non_fungible_token_count",
    BigInteger::class.java
)
