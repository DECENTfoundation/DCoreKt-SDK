package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ProcessedTransaction
import ch.decent.sdk.net.model.ApiGroup

internal class GetTransaction(
    blockNum: Long,
    trxInBlock: Long
) : BaseRequest<ProcessedTransaction>(
    ApiGroup.DATABASE,
    "get_transaction",
    ProcessedTransaction::class.java,
    listOf(blockNum, trxInBlock)
)