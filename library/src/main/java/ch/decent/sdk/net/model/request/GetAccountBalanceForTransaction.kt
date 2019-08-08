package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.BalanceChange
import ch.decent.sdk.model.OperationHistoryObjectId
import ch.decent.sdk.net.model.ApiGroup

internal class GetAccountBalanceForTransaction(
    accountId: AccountObjectId,
    operationId: OperationHistoryObjectId
) : BaseRequest<BalanceChange>(
    ApiGroup.HISTORY,
    "get_account_balance_for_transaction",
    BalanceChange::class.java,
    listOf(accountId, operationId)
)
