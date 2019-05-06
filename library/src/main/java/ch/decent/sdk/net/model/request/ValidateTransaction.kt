package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ProcessedTransaction
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.net.model.ApiGroup

internal class ValidateTransaction(
    transaction: Transaction
) : BaseRequest<ProcessedTransaction>(
    ApiGroup.DATABASE,
    "validate_transaction",
    ProcessedTransaction::class.java,
    listOf(transaction)
)
