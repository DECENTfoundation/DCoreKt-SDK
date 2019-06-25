package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.net.model.ApiGroup

internal class BroadcastTransactionSynchronous(
    transaction: Transaction
) : BaseRequest<TransactionConfirmation>(
    ApiGroup.BROADCAST,
    "broadcast_transaction_synchronous",
    TransactionConfirmation::class.java,
    listOf(transaction)
) {
  init {
    require(transaction.signatures?.isNotEmpty() == true) { "transaction not signed, forgot to call .withSignature(key) ?" }
  }
}
