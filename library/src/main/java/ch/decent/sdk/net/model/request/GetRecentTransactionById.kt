package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ProcessedTransaction
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.TRX_ID_SIZE
import ch.decent.sdk.utils.unhex

internal class GetRecentTransactionById(
    id: String
) : BaseRequest<ProcessedTransaction>(
    ApiGroup.DATABASE,
    "get_recent_transaction_by_id",
    ProcessedTransaction::class.java,
    listOf(id)
) {

  init {
    require(id.unhex().size == TRX_ID_SIZE) { "invalid transaction id" }
  }
}