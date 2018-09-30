package ch.decent.sdk.api

import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.BaseOperation
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.TransactionConfirmation
import io.reactivex.Single

interface BroadcastApi {

  /**
   * broadcast operations to DCore
   * @param privateKey private key
   * @param operations operations to be submitted to DCore
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  fun broadcast(privateKey: ECKeyPair, operations: List<BaseOperation>, expiration: Int = DCoreSdk.defaultExpiration): Single<Unit>

  /**
   * broadcast operations to DCore
   * @param privateKey private key in base58 format
   * @param operations operations to be submitted to DCore
   */
  fun broadcast(privateKey: String, operations: List<BaseOperation>, expiration: Int = DCoreSdk.defaultExpiration): Single<Unit> =
      broadcast(ECKeyPair.fromBase58(privateKey), operations, expiration)

  /**
   * broadcast transaction to DCore
   * @param transaction transaction to broadcast
   */
  fun broadcast(transaction: Transaction): Single<Unit>

  /**
   * broadcast operations to DCore with callback when applied
   * @param privateKey private key
   * @param operations operations to be submitted to DCore
   * @return a transaction confirmation
   */
  fun broadcastWithCallback(privateKey: ECKeyPair, operations: List<BaseOperation>, expiration: Int = DCoreSdk.defaultExpiration): Single<TransactionConfirmation>

  /**
   * broadcast operations to DCore with callback when applied
   * @param privateKey private key in base58 format
   * @param operations operations to be submitted to DCore
   * @return a transaction confirmation
   */
  fun broadcastWithCallback(privateKey: String, operations: List<BaseOperation>, expiration: Int = DCoreSdk.defaultExpiration): Single<TransactionConfirmation> =
      broadcastWithCallback(ECKeyPair.fromBase58(privateKey), operations, expiration)

  /**
   * broadcast transaction to DCore with callback
   * @param transaction transaction to broadcast
   */
  fun broadcastWithCallback(transaction: Transaction): Single<TransactionConfirmation>


}