@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.operation.BaseOperation
import ch.decent.sdk.net.model.request.BroadcastTransaction
import ch.decent.sdk.net.model.request.BroadcastTransactionSynchronous
import ch.decent.sdk.net.model.request.BroadcastTransactionWithCallback
import io.reactivex.Single
import org.threeten.bp.Duration

class BroadcastApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * broadcast transaction to DCore
   * @param transaction transaction to broadcast
   */
  fun broadcast(transaction: Transaction): Single<Unit> = BroadcastTransaction(transaction).toRequest()

  /**
   * broadcast operations to DCore
   * @param privateKey private key
   * @param operations operations to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  @JvmOverloads
  fun broadcast(privateKey: ECKeyPair, operations: List<BaseOperation>, expiration: Duration = api.transactionExpiration): Single<Unit> =
      api.transactionApi.createTransaction(operations, expiration)
          .map { it.withSignature(privateKey) }
          .flatMap { broadcast(it) }

  /**
   * broadcast operation to DCore
   * @param privateKey private key
   * @param operation operation to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  @JvmOverloads
  fun broadcast(privateKey: ECKeyPair, operation: BaseOperation, expiration: Duration = api.transactionExpiration): Single<Unit> =
      broadcast(privateKey, listOf(operation), expiration)

  /**
   * broadcast operations to DCore
   * @param privateKey private key in base58 format
   * @param operations operations to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  @JvmOverloads
  fun broadcast(privateKey: String, operations: List<BaseOperation>, expiration: Duration = api.transactionExpiration): Single<Unit> =
      broadcast(ECKeyPair.fromBase58(privateKey), operations, expiration)

  /**
   * broadcast operation to DCore
   * @param privateKey private key in base58 format
   * @param operation operation to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  @JvmOverloads
  fun broadcast(privateKey: String, operation: BaseOperation, expiration: Duration = api.transactionExpiration): Single<Unit> =
      broadcast(privateKey, listOf(operation), expiration)

  /**
   * broadcast transaction to DCore with callback
   * @param transaction transaction to broadcast
   *
   * @return a transaction confirmation
   */
  fun broadcastWithCallback(transaction: Transaction): Single<TransactionConfirmation> =
      BroadcastTransactionWithCallback(transaction).toRequest()

  /**
   * broadcast operations to DCore with callback when applied
   * @param privateKey private key
   * @param operations operations to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun broadcastWithCallback(privateKey: ECKeyPair, operations: List<BaseOperation>, expiration: Duration = api.transactionExpiration): Single<TransactionConfirmation> =
      api.transactionApi.createTransaction(operations, expiration)
          .map { it.withSignature(privateKey) }
          .flatMap { broadcastWithCallback(it) }


  /**
   * broadcast operation to DCore with callback when applied
   * @param privateKey private key
   * @param operation operation to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun broadcastWithCallback(privateKey: ECKeyPair, operation: BaseOperation, expiration: Duration = api.transactionExpiration): Single<TransactionConfirmation> =
      broadcastWithCallback(privateKey, listOf(operation), expiration)

  /**
   * broadcast operations to DCore with callback when applied
   * @param privateKey private key in base58 format
   * @param operations operations to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun broadcastWithCallback(privateKey: String, operations: List<BaseOperation>, expiration: Duration = api.transactionExpiration): Single<TransactionConfirmation> =
      broadcastWithCallback(ECKeyPair.fromBase58(privateKey), operations, expiration)

  /**
   * broadcast operation to DCore with callback when applied
   * @param privateKey private key in base58 format
   * @param operation operation to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun broadcastWithCallback(privateKey: String, operation: BaseOperation, expiration: Duration = api.transactionExpiration): Single<TransactionConfirmation> =
      broadcastWithCallback(ECKeyPair.fromBase58(privateKey), listOf(operation), expiration)

  // todo
  fun broadcastSynchronous(transaction: Transaction): Single<TransactionConfirmation> = BroadcastTransactionSynchronous(transaction).toRequest()

}
