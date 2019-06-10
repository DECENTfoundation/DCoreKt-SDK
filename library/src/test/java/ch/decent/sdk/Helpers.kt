package ch.decent.sdk

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.net.TrustAllCerts
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Helpers {
//  @JvmStatic val wsUrl = "wss://testnet-api.dcore.io"
//  @JvmStatic val restUrl = "https://testnet-api.dcore.io/"
  @JvmStatic val wsUrl = "ws://localhost:8090/"
  @JvmStatic val restUrl = "http://localhost:8090/"
  @JvmStatic fun client(logger: Logger = LoggerFactory.getLogger("OkHttpClient")): OkHttpClient =
      TrustAllCerts.wrap(OkHttpClient.Builder())
          .addInterceptor(HttpLoggingInterceptor { logger.info(it) }.setLevel(HttpLoggingInterceptor.Level.BODY))
          .build()

  @JvmStatic val account = "1.2.27".toChainObject()
  @JvmStatic val account2 = "1.2.28".toChainObject()
  @JvmStatic val accountName = "public-account-9"
  @JvmStatic val accountName2 = "public-account-10"
  @JvmStatic val private = "5JuJbrKZgAATcouJnwpaxPbHMAMDXSgUpQSfxTXzkSUufcnpTUa"
  @JvmStatic val private2 = "5JuJbrKZgAATcouJnwpaxPbHMAMDXSgUpQSfxTXzkSUufcnpTUa"
  @JvmStatic val public = "DCT82MTCQVa9TDFmz3ZwaLzsFAmCLoJzrtFugpF72vsbuE1CpCwKy"
  @JvmStatic val public2 = "DCT82MTCQVa9TDFmz3ZwaLzsFAmCLoJzrtFugpF72vsbuE1CpCwKy"
  @JvmStatic val credentials = Credentials(account, private)
}

fun Any.print() = println(this.toString())

fun <T> Single<T>.testCheck(check: TestObserver<T>.() -> Unit = {
  assertComplete()
  assertNoErrors()
}) {
  val test = this
      .subscribeOn(Schedulers.newThread())
      .test()

  test.awaitTerminalEvent()
  check(test)
}
