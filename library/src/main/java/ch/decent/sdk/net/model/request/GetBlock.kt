package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.SignedBlock
import ch.decent.sdk.net.model.ApiGroup

internal class GetBlock(blockNum: Long) : BaseRequest<SignedBlock>(
    ApiGroup.DATABASE,
    "get_block",
    SignedBlock::class.java,
    listOf(blockNum)
)
