package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup
import java.math.BigInteger

internal object GetNewAssetPerBlock : BaseRequest<BigInteger>(
    ApiGroup.DATABASE,
    "get_new_asset_per_block",
    BigInteger::class.java
)
