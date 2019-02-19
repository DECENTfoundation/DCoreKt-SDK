package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.net.model.request.*
import io.reactivex.Flowable
import io.reactivex.Single

// todo how to cancel callbacks ?
// cancel.. works only on setSubscribeCallback
class CallbackApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Stop receiving any notifications. This unsubscribes from all subscribed objects ([setSubscribeCallback] and [AccountApi.getFullAccounts]).
   */
  fun cancelAll(): Single<Unit> = CancelAllSubscriptions.toRequest()

  /**
   * Receive new block notifications. Cannot be cancelled.
   */
  fun onBlockApplied(): Flowable<String> = SetBlockAppliedCallback.toRequest()

  /**
   * Receive notifications on content update. Cannot be cancelled.
   *
   * @param uri content URI to monitor
   */
  fun onContentUpdate(uri: String): Flowable<Unit> = SetContentUpdateCallback(uri).toRequest()

  /**
   * Receive notifications on pending transactions. Cannot be cancelled.
   */
  fun onPendingTransaction(): Flowable<Unit> = SetPendingTransactionCallback.toRequest()

  /**
   * Subscribe to callbacks. Can be cancelled. with [cancelAllSubscriptions].
   *
   * @param clearFilter clear current subscriptions created with [AccountApi.getFullAccounts]
   */
  fun onGlobal(clearFilter: Boolean): Flowable<Unit> = SetSubscribeCallback(clearFilter).toRequest()
}