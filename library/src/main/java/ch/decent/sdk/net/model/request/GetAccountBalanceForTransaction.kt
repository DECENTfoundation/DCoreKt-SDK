package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.BalanceChange
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup

internal class GetAccountBalanceForTransaction(
    accountId: ChainObject,
    operationId: ChainObject
) : BaseRequest<BalanceChange>(
    ApiGroup.HISTORY,
    "get_account_balance_for_transaction",
    BalanceChange::class.java,
    listOf(accountId, operationId)
) {

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(operationId.objectType == ObjectType.OPERATION_HISTORY_OBJECT) { "not a valid history object id" }
  }
}
