@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetObjectId
import ch.decent.sdk.model.OperationHistoryObjectId
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import kotlin.Int
import kotlin.Long
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.emptyList

class HistoryApi internal constructor(
  private val api: ch.decent.sdk.api.HistoryApi
) {
  fun getOperation(accountId: AccountObjectId, operationId: OperationHistoryObjectId) =
      api.getOperation(accountId, operationId).blockingGet()
  fun listOperations(
    accountId: AccountObjectId,
    startId: OperationHistoryObjectId? = null,
    stopId: OperationHistoryObjectId? = null,
    limit: Int = REQ_LIMIT_MAX
  ) = api.listOperations(accountId, startId, stopId, limit).blockingGet()
  fun listOperationsRelative(
    accountId: AccountObjectId,
    start: Int = 0,
    limit: Int = REQ_LIMIT_MAX
  ) = api.listOperationsRelative(accountId, start, limit).blockingGet()
  fun findAllOperations(
    accountId: AccountObjectId,
    assets: List<AssetObjectId> = emptyList(),
    recipientAccount: AccountObjectId? = null,
    fromBlock: Long = 0,
    toBlock: Long = 0,
    startOffset: Long = 0,
    limit: Int = REQ_LIMIT_MAX
  ) = api.findAllOperations(accountId, assets, recipientAccount, fromBlock, toBlock, startOffset,
      limit).blockingGet()}
