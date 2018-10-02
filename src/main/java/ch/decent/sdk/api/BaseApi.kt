package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.net.model.request.BaseRequest
import io.reactivex.Single

abstract class BaseApi internal constructor(protected val api: DCoreApi) {
  internal fun <T> BaseRequest<T>.toRequest(): Single<T> = api.core.makeRequest(this)
}