package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup
import java.math.BigInteger

internal class GetAssetPerBlock(
    blockNum: Long
) : BaseRequest<BigInteger>(
    ApiGroup.DATABASE,
    "get_asset_per_block_by_block_num",
    BigInteger::class.java,
    listOf(blockNum)
)