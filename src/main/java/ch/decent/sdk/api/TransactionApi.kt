package ch.decent.sdk.api

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.BaseOperation
import ch.decent.sdk.model.ProcessedTransaction
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.TransactionConfirmation
import io.reactivex.Single

interface TransactionApi {

  /**
   * If the transaction has not expired, this method will return the transaction for the given ID or it will return [ch.decent.sdk.exception.ObjectNotFoundException].
   * Just because it is not known does not mean it wasn't included in the DCore. The ID can be retrieved from [Transaction] or [TransactionConfirmation] objects.
   *
   * @param trxId transaction id
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getRecentTransaction(trxId: String): Single<ProcessedTransaction>

  /**
   * get applied transaction
   *
   * @param blockNum block number
   * @param trxInBlock position of the transaction in block
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getTransaction(blockNum: Long, trxInBlock: Long): Single<ProcessedTransaction>

  /**
   * get applied transaction
   *
   * @param confirmation confirmation returned from transaction broadcast
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getTransaction(confirmation: TransactionConfirmation): Single<ProcessedTransaction> = getTransaction(confirmation.blockNum, confirmation.trxNum)

  fun createTransaction(operations: List<BaseOperation>, expiration: Int = DCoreConstants.DEFAULT_EXPIRATION): Single<Transaction>

}