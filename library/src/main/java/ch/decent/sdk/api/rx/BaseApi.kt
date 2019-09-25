package ch.decent.sdk.api.rx

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.operation.BaseOperation
import ch.decent.sdk.net.model.request.BaseRequest
import ch.decent.sdk.net.model.request.WithCallback
import io.reactivex.Flowable
import io.reactivex.Single

abstract class BaseApi internal constructor(protected val api: DCoreApi) {
  internal fun <T> BaseRequest<T>.toRequest(): Single<T> = api.core.makeRequest(this)
  internal fun <R, T> T.callbacks(): Flowable<R> where T : BaseRequest<R>, T : WithCallback = api.core.makeRequestStream(this)

  internal fun <T : BaseOperation> Single<T>.broadcast(credentials: Credentials): Single<TransactionConfirmation> =
      flatMap { it.broadcast(credentials) }

  internal fun <T : BaseOperation> T.broadcast(credentials: Credentials): Single<TransactionConfirmation> =
      api.broadcastApi.broadcastWithCallback(credentials.keyPair, this)
}
