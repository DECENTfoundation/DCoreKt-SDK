package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.BlockHeader
import ch.decent.sdk.net.model.ApiGroup

internal class GetBlockHeader(blockNum: Long) : BaseRequest<BlockHeader>(
    ApiGroup.DATABASE,
    "get_block_header",
    BlockHeader::class.java,
    listOf(blockNum)
)
