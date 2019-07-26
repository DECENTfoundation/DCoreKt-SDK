@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.operation.BaseOperation
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import org.threeten.bp.Duration

class BroadcastApi internal constructor(
  private val api: ch.decent.sdk.api.BroadcastApi
) {
  fun broadcast(transaction: Transaction) = api.broadcast(transaction).blockingGet()
  fun broadcast(
    privateKey: ECKeyPair,
    operations: List<BaseOperation>,
    expiration: Duration = api.transactionExpiration
  ) = api.broadcast(privateKey, operations, expiration).blockingGet()
  fun broadcast(
    privateKey: ECKeyPair,
    operation: BaseOperation,
    expiration: Duration = api.transactionExpiration
  ) = api.broadcast(privateKey, operation, expiration).blockingGet()
  fun broadcast(
    privateKey: String,
    operations: List<BaseOperation>,
    expiration: Duration = api.transactionExpiration
  ) = api.broadcast(privateKey, operations, expiration).blockingGet()
  fun broadcast(
    privateKey: String,
    operation: BaseOperation,
    expiration: Duration = api.transactionExpiration
  ) = api.broadcast(privateKey, operation, expiration).blockingGet()
  fun broadcastWithCallback(transaction: Transaction) =
      api.broadcastWithCallback(transaction).blockingGet()
  fun broadcastWithCallback(
    privateKey: ECKeyPair,
    operations: List<BaseOperation>,
    expiration: Duration = api.transactionExpiration
  ) = api.broadcastWithCallback(privateKey, operations, expiration).blockingGet()
  fun broadcastWithCallback(
    privateKey: ECKeyPair,
    operation: BaseOperation,
    expiration: Duration = api.transactionExpiration
  ) = api.broadcastWithCallback(privateKey, operation, expiration).blockingGet()
  fun broadcastWithCallback(
    privateKey: String,
    operations: List<BaseOperation>,
    expiration: Duration = api.transactionExpiration
  ) = api.broadcastWithCallback(privateKey, operations, expiration).blockingGet()
  fun broadcastWithCallback(
    privateKey: String,
    operation: BaseOperation,
    expiration: Duration = api.transactionExpiration
  ) = api.broadcastWithCallback(privateKey, operation, expiration).blockingGet()
  fun broadcastSynchronous(transaction: Transaction) =
      api.broadcastSynchronous(transaction).blockingGet()}
