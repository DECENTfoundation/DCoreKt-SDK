package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Transaction
import ch.decent.sdk.net.model.ApiGroup

internal class GetTransactionHex(
    transaction: Transaction
) : BaseRequest<String>(
    ApiGroup.DATABASE,
    "get_transaction_hex",
    String::class.java,
    listOf(transaction)
)
