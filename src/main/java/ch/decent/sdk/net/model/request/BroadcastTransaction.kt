package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Transaction
import ch.decent.sdk.net.model.ApiGroup

internal class BroadcastTransaction(
    transaction: Transaction
): BaseRequest<Unit>(
    ApiGroup.BROADCAST,
    "broadcast_transaction",
    Unit::class.java,
    listOf(transaction)
) {
  init {
    require(transaction.signatures?.isNotEmpty() == true) {"transaction not signed, forgot to call .withSignature(key) ?"}
  }
}