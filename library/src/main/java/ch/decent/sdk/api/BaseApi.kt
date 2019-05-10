package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.net.model.request.BaseRequest
import ch.decent.sdk.net.model.request.WithCallback
import io.reactivex.Flowable
import io.reactivex.Single

abstract class BaseApi internal constructor(protected val api: DCoreApi) {
  internal fun <T> BaseRequest<T>.toRequest(): Single<T> = api.core.makeRequest(this)
  internal fun <R, T> T.callbacks(): Flowable<R> where T : BaseRequest<R>, T : WithCallback = api.core.makeRequestStream(this)
}
