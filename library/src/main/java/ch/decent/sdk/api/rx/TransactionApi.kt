@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api.rx

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.ProcessedTransaction
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.operation.BaseOperation
import ch.decent.sdk.net.model.request.GetProposedTransactions
import ch.decent.sdk.net.model.request.GetRecentTransactionById
import ch.decent.sdk.net.model.request.GetTransaction
import ch.decent.sdk.net.model.request.GetTransactionById
import ch.decent.sdk.net.model.request.GetTransactionHex
import io.reactivex.Single
import org.threeten.bp.Duration

class TransactionApi internal constructor(api: DCoreApi) : BaseApi(api) {

  internal val transactionExpiration: Duration
    get() = api.transactionExpiration

  /**
   * create unsigned transaction
   *
   * @param operations operations to include in transaction
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  @JvmOverloads
  fun createTransaction(operations: List<BaseOperation>, expiration: Duration = api.transactionExpiration): Single<Transaction> =
      api.core.prepareTransaction(operations, expiration)

  /**
   * create unsigned transaction
   *
   * @param operation operation to include in transaction
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  @JvmOverloads
  fun createTransaction(operation: BaseOperation, expiration: Duration = api.transactionExpiration): Single<Transaction> =
      api.core.prepareTransaction(listOf(operation), expiration)

  /**
   * Get the set of proposed transactions relevant to the specified account id.
   *
   * @param accountId account object id, 1.2.*
   *
   * @return a set of proposed transactions
   */
  // todo model
  fun getAllProposed(accountId: AccountObjectId): Single<List<Any>> = GetProposedTransactions(accountId).toRequest()

  /**
   * If the transaction has not expired, this method will return the transaction for the given ID or it will return [ch.decent.sdk.exception.ObjectNotFoundException].
   * Just because it is not known does not mean it wasn't included in the DCore. The ID can be retrieved from [Transaction] or [TransactionConfirmation] objects.
   *
   * @param trxId transaction id
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getRecent(trxId: String): Single<ProcessedTransaction> = GetRecentTransactionById(trxId).toRequest()

  /**
   * This method will return the transaction for the given ID or it will return [ch.decent.sdk.exception.ObjectNotFoundException].
   * The ID can be retrieved from [Transaction] or [TransactionConfirmation] objects.
   *
   * Note: By default these objects are not tracked, the transaction_history_plugin must be loaded for these objects to be maintained.
   * Just because it is not known does not mean it wasn't included in the DCore.
   *
   * @param trxId transaction id
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun get(trxId: String): Single<ProcessedTransaction> = GetTransactionById(trxId).toRequest()

  /**
   * get applied transaction
   *
   * @param blockNum block number
   * @param trxInBlock position of the transaction in block
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun get(blockNum: Long, trxInBlock: Long): Single<ProcessedTransaction> = GetTransaction(blockNum, trxInBlock).toRequest()

  /**
   * get applied transaction
   *
   * @param confirmation confirmation returned from transaction broadcast
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun get(confirmation: TransactionConfirmation): Single<ProcessedTransaction> = get(confirmation.blockNum, confirmation.trxNum)

  /**
   * Get a hexdump of the serialized binary form of a transaction.
   *
   * @param transaction a signed transaction
   *
   * @return hexadecimal string
   */
  fun getHexDump(transaction: Transaction): Single<String> = GetTransactionHex(transaction).toRequest()

}
