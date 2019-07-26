@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetObjectId
import ch.decent.sdk.model.BalanceChange
import ch.decent.sdk.model.OperationHistory
import ch.decent.sdk.model.OperationHistoryObjectId
import ch.decent.sdk.net.model.request.GetAccountBalanceForTransaction
import ch.decent.sdk.net.model.request.GetAccountHistory
import ch.decent.sdk.net.model.request.GetRelativeAccountHistory
import ch.decent.sdk.net.model.request.SearchAccountBalanceHistory
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import io.reactivex.Single

class HistoryApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Returns balance operation on the account and operation id.
   *
   * @param accountId object id of the account whose history should be queried, 1.2.*
   * @param operationId object id of the history object, 1.7.*
   *
   * @return an balance operation change
   */
  fun getOperation(
      accountId: AccountObjectId,
      operationId: OperationHistoryObjectId
  ): Single<BalanceChange> = GetAccountBalanceForTransaction(accountId, operationId).toRequest()

  /**
   * Get account history of operations.
   *
   * @param accountId object id of the account whose history should be queried, 1.2.*
   * @param limit number of entries, max 100
   * @param startId id of the history object to start from
   * @param stopId id of the history object to stop at
   *
   * @return a list of operations performed by account, ordered from most recent to oldest
   */
  @JvmOverloads
  fun listOperations(
      accountId: AccountObjectId,
      startId: OperationHistoryObjectId? = null,
      stopId: OperationHistoryObjectId? = null,
      limit: Int = REQ_LIMIT_MAX
  ): Single<List<OperationHistory>> =
      GetAccountHistory(accountId, stopId ?: OperationHistoryObjectId(), limit, startId ?: OperationHistoryObjectId()).toRequest()

  /**
   * Get account history of operations.
   *
   * @param accountId object id of the account whose history should be queried, 1.2.*
   * @param start sequence number of the most recent operation to retrieve. 0 is default, which will start querying from the most recent operation
   * @param limit  maximum number of operations to retrieve (must not exceed 100)
   *
   * @return a list of operations performed by account, ordered from most recent to oldest
   */
  @JvmOverloads
  fun listOperationsRelative(
      accountId: AccountObjectId,
      start: Int = 0,
      limit: Int = REQ_LIMIT_MAX
  ): Single<List<OperationHistory>> = GetRelativeAccountHistory(accountId, 0, limit, start).toRequest()

  /**
   * Returns the most recent balance operations on the named account.
   * This returns a list of operation history objects, which describe activity on the account.
   *
   * @param accountId object id of the account whose history should be queried, 1.2.*
   * @param assets list of asset object ids to filter or empty for all assets
   * @param recipientAccount partner account object id to filter transfers to specific account, 1.2.* or null
   * @param fromBlock filtering parameter, starting block number (can be determined from time) or zero when not used
   * @param toBlock filtering parameter, ending block number or zero when not used
   * @param startOffset  starting offset from zero
   * @param limit the number of entries to return (starting from the most recent), max 100
   *
   * @return a list of balance changes
   */
  @JvmOverloads
  fun findAllOperations(
      accountId: AccountObjectId,
      assets: List<AssetObjectId> = emptyList(),
      recipientAccount: AccountObjectId? = null,
      fromBlock: Long = 0,
      toBlock: Long = 0,
      startOffset: Long = 0,
      limit: Int = REQ_LIMIT_MAX
  ): Single<List<BalanceChange>> = SearchAccountBalanceHistory(accountId, assets, recipientAccount, fromBlock, toBlock, startOffset, limit).toRequest()
}
