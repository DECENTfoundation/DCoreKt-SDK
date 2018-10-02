package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.OperationHistory
import ch.decent.sdk.net.model.request.GetAccountHistory
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

}