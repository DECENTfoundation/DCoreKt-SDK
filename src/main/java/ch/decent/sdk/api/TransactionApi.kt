package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.BaseOperation
import ch.decent.sdk.model.ProcessedTransaction
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.net.model.request.GetRecentTransactionById
import ch.decent.sdk.net.model.request.GetTransaction
import io.reactivex.Single

class TransactionApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * If the transaction has not expired, this method will return the transaction for the given ID or it will return [ch.decent.sdk.exception.ObjectNotFoundException].
   * Just because it is not known does not mean it wasn't included in the DCore. The ID can be retrieved from [Transaction] or [TransactionConfirmation] objects.
   *
   * @param trxId transaction id
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getRecentTransaction(trxId: String): Single<ProcessedTransaction> = GetRecentTransactionById(trxId).toRequest()

  /**
   * get applied transaction
   *
   * @param blockNum block number
   * @param trxInBlock position of the transaction in block
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getTransaction(blockNum: Long, trxInBlock: Long): Single<ProcessedTransaction> = GetTransaction(blockNum, trxInBlock).toRequest()

  /**
   * get applied transaction
   *
   * @param confirmation confirmation returned from transaction broadcast
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getTransaction(confirmation: TransactionConfirmation): Single<ProcessedTransaction> = getTransaction(confirmation.blockNum, confirmation.trxNum)

  /**
   * create unsigned transaction
   *
   * @param operations operations to include in transaction
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  @JvmOverloads
  fun createTransaction(operations: List<BaseOperation>, expiration: Int = api.transactionExpiration): Single<Transaction> =
      api.core.prepareTransaction(operations, expiration)

  /**
   * create unsigned transaction
   *
   * @param operation operation to include in transaction
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  @JvmOverloads
  fun createTransaction(operation: BaseOperation, expiration: Int = api.transactionExpiration): Single<Transaction> =
      api.core.prepareTransaction(listOf(operation), expiration)

}