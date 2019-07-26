@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.operation.BaseOperation
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import org.threeten.bp.Duration

class TransactionApi internal constructor(
  private val api: ch.decent.sdk.api.TransactionApi
) {
  fun createTransaction(operations: List<BaseOperation>, expiration: Duration =
      api.transactionExpiration) = api.createTransaction(operations, expiration).blockingGet()
  fun createTransaction(operation: BaseOperation, expiration: Duration = api.transactionExpiration)
      = api.createTransaction(operation, expiration).blockingGet()
  fun getAllProposed(accountId: AccountObjectId) = api.getAllProposed(accountId).blockingGet()
  fun getRecent(trxId: String) = api.getRecent(trxId).blockingGet()
  fun get(trxId: String) = api.get(trxId).blockingGet()
  fun get(blockNum: Long, trxInBlock: Long) = api.get(blockNum, trxInBlock).blockingGet()
  fun get(confirmation: TransactionConfirmation) = api.get(confirmation).blockingGet()
  fun getHexDump(transaction: Transaction) = api.getHexDump(transaction).blockingGet()}
