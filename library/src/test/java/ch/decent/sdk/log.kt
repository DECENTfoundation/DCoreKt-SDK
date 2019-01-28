package ch.decent.sdk

import io.reactivex.*
import org.slf4j.LoggerFactory

/**
 * @author marian on 23.2.2017.
 */
fun <T> Flowable<T>.log(tag: String): Flowable<T> =
    this.compose {
      it.doOnSubscribe { LoggerFactory.getLogger("RxLog").debug("$tag #onSubscribe") }
          .doOnNext { value -> LoggerFactory.getLogger("RxLog").debug("$tag #onNext: $value") }
          .doOnComplete { LoggerFactory.getLogger("RxLog").debug("$tag #onComplete") }
          .doOnError { error -> LoggerFactory.getLogger("RxLog").error("$tag #onError", error) }
          .doOnCancel { LoggerFactory.getLogger("RxLog").debug("$tag #onCancel") }
          .doOnRequest { value -> LoggerFactory.getLogger("RxLog").debug("$tag #onRequest:$value") }
    }

fun <T> Observable<T>.log(tag: String): Observable<T> =
    this.compose {
      it.doOnSubscribe { LoggerFactory.getLogger("RxLog").debug("$tag #onSubscribe") }
          .doOnNext { value -> LoggerFactory.getLogger("RxLog").debug("$tag #onNext:$value") }
          .doOnComplete { LoggerFactory.getLogger("RxLog").debug("$tag #onComplete") }
          .doOnError { error -> LoggerFactory.getLogger("RxLog").error("$tag #onError", error) }
          .doOnDispose { LoggerFactory.getLogger("RxLog").debug("$tag #onDispose") }
    }

fun <T> Maybe<T>.log(tag: String): Maybe<T> =
    this.compose {
      it.doOnSubscribe { LoggerFactory.getLogger("RxLog").debug("$tag #onSubscribe") }
          .doOnSuccess { value -> LoggerFactory.getLogger("RxLog").debug("$tag #onSuccess:$value") }
          .doOnComplete { LoggerFactory.getLogger("RxLog").debug("$tag #onComplete") }
          .doOnError { error -> LoggerFactory.getLogger("RxLog").error("$tag #onError", error) }
          .doOnDispose { LoggerFactory.getLogger("RxLog").debug("$tag #onDispose") }
    }

fun <T> Single<T>.log(tag: String): Single<T> =
    this.compose {
      it.doOnSubscribe { LoggerFactory.getLogger("RxLog").debug("$tag #onSubscribe") }
          .doOnSuccess { value -> LoggerFactory.getLogger("RxLog").debug("$tag #onSuccess:$value") }
          .doOnError { error -> LoggerFactory.getLogger("RxLog").error("$tag #onError", error) }
          .doOnDispose { LoggerFactory.getLogger("RxLog").debug("$tag #onDispose") }
    }

fun Completable.log(tag: String): Completable =
    this.compose {
      it.doOnSubscribe { LoggerFactory.getLogger("RxLog").debug("$tag #onSubscribe") }
          .doOnComplete { LoggerFactory.getLogger("RxLog").debug("$tag #onComplete") }
          .doOnError { error -> LoggerFactory.getLogger("RxLog").error("$tag #onError", error) }
          .doOnDispose { LoggerFactory.getLogger("RxLog").debug("$tag #onDispose") }
    }