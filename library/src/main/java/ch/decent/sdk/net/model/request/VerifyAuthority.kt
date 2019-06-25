package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Transaction
import ch.decent.sdk.net.model.ApiGroup

internal class VerifyAuthority(
    transaction: Transaction
) : BaseRequest<Boolean>(
    ApiGroup.DATABASE,
    "verify_authority",
    Boolean::class.java,
    listOf(transaction)
)
