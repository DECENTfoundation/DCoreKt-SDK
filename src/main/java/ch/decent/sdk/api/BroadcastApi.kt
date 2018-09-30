package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.BaseOperation
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.net.model.request.BroadcastTransaction
import io.reactivex.Single

class BroadcastApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * broadcast operations to DCore
   * @param privateKey private key
   * @param operations operations to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  @JvmOverloads
  fun broadcast(privateKey: ECKeyPair, operations: List<BaseOperation>, expiration: Int = api.transactionExpiration): Single<Unit> =
      api.core.broadcast(privateKey, operations, expiration)

  /**
   * broadcast operation to DCore
   * @param privateKey private key
   * @param operation operation to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  @JvmOverloads
  fun broadcast(privateKey: ECKeyPair, operation: BaseOperation, expiration: Int = api.transactionExpiration): Single<Unit> =
      broadcast(privateKey, listOf(operation), expiration)

  /**
   * broadcast operations to DCore
   * @param privateKey private key in base58 format
   * @param operations operations to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  @JvmOverloads
  fun broadcast(privateKey: String, operations: List<BaseOperation>, expiration: Int = api.transactionExpiration): Single<Unit> =
      broadcast(ECKeyPair.fromBase58(privateKey), operations, expiration)

  /**
   * broadcast operation to DCore
   * @param privateKey private key in base58 format
   * @param operation operation to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  @JvmOverloads
  fun broadcast(privateKey: String, operation: BaseOperation, expiration: Int = api.transactionExpiration): Single<Unit> =
      broadcast(privateKey, listOf(operation), expiration)

  /**
   * broadcast transaction to DCore
   * @param transaction transaction to broadcast
   */
  fun broadcast(transaction: Transaction): Single<Unit> = BroadcastTransaction(transaction).toRequest()

  /**
   * broadcast operations to DCore with callback when applied
   * @param privateKey private key
   * @param operations operations to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun broadcastWithCallback(privateKey: ECKeyPair, operations: List<BaseOperation>, expiration: Int = api.transactionExpiration): Single<TransactionConfirmation> =
      api.core.broadcastWithCallback(privateKey, operations, expiration)

  /**
   * broadcast operation to DCore with callback when applied
   * @param privateKey private key
   * @param operation operation to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun broadcastWithCallback(privateKey: ECKeyPair, operation: BaseOperation, expiration: Int = api.transactionExpiration): Single<TransactionConfirmation> =
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
  fun broadcastWithCallback(privateKey: String, operations: List<BaseOperation>, expiration: Int = api.transactionExpiration): Single<TransactionConfirmation> =
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
  fun broadcastWithCallback(privateKey: String, operation: BaseOperation, expiration: Int = api.transactionExpiration): Single<TransactionConfirmation> =
      broadcastWithCallback(ECKeyPair.fromBase58(privateKey), listOf(operation), expiration)

  /**
   * broadcast transaction to DCore with callback
   * @param transaction transaction to broadcast
   *
   * @return a transaction confirmation
   */
  fun broadcastWithCallback(transaction: Transaction): Single<TransactionConfirmation> = api.core.broadcastWithCallback(transaction)

}