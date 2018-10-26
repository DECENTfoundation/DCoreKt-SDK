package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.net.model.request.*
import io.reactivex.Flowable
import io.reactivex.Single

// todo how to cancel callbacks ?
// cancel.. works only on setSubscribeCallback
class SubscriptionApi internal constructor(api: DCoreApi) : BaseApi(api) {

  fun cancelAllSubscriptions(): Single<Unit> = CancelAllSubscriptions.toRequest()

  fun setBlockAppliedCallback(): Flowable<String> = SetBlockAppliedCallback.toRequest()

  fun setContentUpdateCallback(uri: String): Flowable<Unit> = SetContentUpdateCallback(uri).toRequest()

  fun setPendingTransactionCallback(): Flowable<Unit> = SetPendingTransactionCallback.toRequest()

  fun setSubscribeCallback(clearFilter: Boolean): Flowable<Unit> = SetSubscribeCallback(clearFilter).toRequest()
}