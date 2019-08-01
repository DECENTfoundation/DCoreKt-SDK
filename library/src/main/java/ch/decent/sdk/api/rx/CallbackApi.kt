@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api.rx

import ch.decent.sdk.net.model.request.CancelAllSubscriptions
import ch.decent.sdk.net.model.request.SetBlockAppliedCallback
import ch.decent.sdk.net.model.request.SetContentUpdateCallback
import ch.decent.sdk.net.model.request.SetPendingTransactionCallback
import ch.decent.sdk.net.model.request.SetSubscribeCallback
import io.reactivex.Flowable
import io.reactivex.Single

// todo how to cancel callbacks ?
// cancel.. works only on setSubscribeCallback
class CallbackApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Stop receiving any notifications. This unsubscribes from all subscribed objects ([onGlobal] and [AccountApi.getFullAccounts]).
   */
  fun cancelAll(): Single<Unit> = CancelAllSubscriptions.toRequest()

  /**
   * Receive new block notifications. Cannot be cancelled.
   */
  fun onBlockApplied(): Flowable<String> = SetBlockAppliedCallback.callbacks()

  /**
   * Receive notifications on content update. Cannot be cancelled.
   *
   * @param uri content URI to monitor
   */
  fun onContentUpdate(uri: String): Flowable<Unit> = SetContentUpdateCallback(uri).callbacks()

  /**
   * Receive notifications on pending transactions. Cannot be cancelled.
   */
  fun onPendingTransaction(): Flowable<Unit> = SetPendingTransactionCallback.callbacks()

  /**
   * Subscribe to callbacks. Can be cancelled. with [cancelAll].
   *
   * @param clearFilter clear current subscriptions created with [AccountApi.getFullAccounts]
   */
  fun onGlobal(clearFilter: Boolean): Flowable<Unit> = SetSubscribeCallback(clearFilter).callbacks()
}
