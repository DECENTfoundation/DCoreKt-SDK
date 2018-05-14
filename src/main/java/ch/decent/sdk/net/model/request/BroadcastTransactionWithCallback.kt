package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.net.model.ApiGroup

internal class BroadcastTransactionWithCallback(
    transaction: Transaction,
    override val callbackId: Long
): BaseRequest<TransactionConfirmation>(
    ApiGroup.BROADCAST,
    "broadcast_transaction_with_callback",
    TransactionConfirmation::class.java,
    listOf(callbackId, transaction)
), WithCallback