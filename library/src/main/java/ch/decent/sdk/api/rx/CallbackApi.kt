@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api.rx

import ch.decent.sdk.net.model.request.SetBlockAppliedCallback
import ch.decent.sdk.net.model.request.SetContentUpdateCallback
import ch.decent.sdk.net.model.request.SetPendingTransactionCallback
import io.reactivex.Flowable

// todo how to cancel callbacks ?
// cancel.. works only on setSubscribeCallback
class CallbackApi internal constructor(api: DCoreApi) : BaseApi(api) {

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
}
