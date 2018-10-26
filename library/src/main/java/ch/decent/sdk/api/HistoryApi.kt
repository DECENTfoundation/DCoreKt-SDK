package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.model.BalanceChange
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.OperationHistory
import ch.decent.sdk.net.model.request.GetAccountBalanceForTransaction
import ch.decent.sdk.net.model.request.GetAccountHistory
import ch.decent.sdk.net.model.request.GetRelativeAccountHistory
import ch.decent.sdk.net.model.request.SearchAccountBalanceHistory
import io.reactivex.Single

class HistoryApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * get account history
   *
   * @param accountId object id of the account, 1.2.*
   * @param limit number of entries, max 100
   * @param startId id of the history object to start from, use [ObjectType.OPERATION_HISTORY_OBJECT.genericId] to ignore
   * @param stopId id of the history object to stop at, use [ObjectType.OPERATION_HISTORY_OBJECT.genericId] to ignore
   *
   * @return list of history operations
   */
  @JvmOverloads
  fun getAccountHistory(
      accountId: ChainObject,
      startId: ChainObject = ObjectType.OPERATION_HISTORY_OBJECT.genericId,
      stopId: ChainObject = ObjectType.OPERATION_HISTORY_OBJECT.genericId,
      limit: Int = 100
  ): Single<List<OperationHistory>> = GetAccountHistory(accountId, stopId, limit, startId).toRequest()

  fun getAccountHistoryRelative(
      accountId: ChainObject,
      start: Int = 0,
      stop: Int = 0,
      limit: Int = 100
  ): Single<List<OperationHistory>> = GetRelativeAccountHistory(accountId, stop, limit, start).toRequest()

  fun searchAccountBalanceHistory(
      accountId: ChainObject,
      assets: List<ChainObject> = emptyList(),
      recipientAccount: ChainObject? = null,
      fromBlock: Long = 0,
      toBlock: Long = 0,
      startOffset: Long = 0,
      limit: Int = 100
  ): Single<List<BalanceChange>> = SearchAccountBalanceHistory(accountId, assets, recipientAccount, fromBlock, toBlock, startOffset, limit).toRequest()

  fun getAccountBalanceForTransaction(
      accountId: ChainObject,
      operationId: ChainObject
  ): Single<BalanceChange> = GetAccountBalanceForTransaction(accountId, operationId).toRequest()
}