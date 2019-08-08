package ch.decent.sdk.api

import io.reactivex.observers.DisposableSingleObserver

abstract class Callback<T> : DisposableSingleObserver<T>(), Cancelable {
  override fun onError(e: Throwable) {}

  override fun cancel() = dispose()
}

interface Cancelable {
  fun cancel()
}
