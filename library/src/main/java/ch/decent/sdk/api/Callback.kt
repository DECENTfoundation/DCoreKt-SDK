package ch.decent.sdk.api

import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

abstract class Callback<T> : DisposableSingleObserver<T>() {
  override fun onError(e: Throwable) {}

  fun cancel() = dispose()
}


fun get(callback: Callback<Unit>) = Single.just(Unit)
    .subscribeWith(callback)

fun foo() {
  get(object : Callback<Unit>() {
    override fun onSuccess(t: Unit) {}
  })
}
