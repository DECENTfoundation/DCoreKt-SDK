package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.net.model.request.*
import io.reactivex.Flowable
import io.reactivex.Single

// todo how to cancel callbacks ?
// cancel.. works only on setSubscribeCallback
class SubscriptionApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Stop receiving any notifications. This unsubscribes from all subscribed objects ([setSubscribeCallback] and [AccountApi.getFullAccounts]).
   */
  fun cancelAllSubscriptions(): Single<Unit> = CancelAllSubscriptions.toRequest()

  /**
   * Receive new block notifications. Cannot be cancelled.
   */
  fun setBlockAppliedCallback(): Flowable<String> = SetBlockAppliedCallback.toRequest()

  /**
   * Receive notifications on content update. Cannot be cancelled.
   *
   * @param uri content URI to monitor
   */
  fun setContentUpdateCallback(uri: String): Flowable<Unit> = SetContentUpdateCallback(uri).toRequest()

  /**
   * Receive notifications on pending transactions. Cannot be cancelled.
   */
  fun setPendingTransactionCallback(): Flowable<Unit> = SetPendingTransactionCallback.toRequest()

  /**
   * Subscribe to callbacks. Can be cancelled. with [cancelAllSubscriptions].
   *
   * @param clearFilter clear current subscriptions created with [AccountApi.getFullAccounts]
   */
  fun setSubscribeCallback(clearFilter: Boolean): Flowable<Unit> = SetSubscribeCallback(clearFilter).toRequest()
}