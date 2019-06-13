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
  @JvmStatic val testnetWs = "wss://testnet-api.dcore.io"
  @JvmStatic val testnetHttp = "https://testnet-api.dcore.io/"
  @JvmStatic val stageWs = "wss://stagesocket.decentgo.com:8090"
  @JvmStatic val stageHttp = "https://stagesocket.decentgo.com"
  @JvmStatic val dockerWs = "ws://localhost:8090/"
  @JvmStatic val dockerHttp = "http://localhost:8090/"
  @JvmStatic val wsUrl = dockerWs
  @JvmStatic val httpUrl = dockerHttp

  @JvmStatic fun client(logger: Logger = LoggerFactory.getLogger("OkHttpClient")): OkHttpClient =
      TrustAllCerts.wrap(OkHttpClient.Builder())
          .addInterceptor(HttpLoggingInterceptor { logger.info(it) }.setLevel(HttpLoggingInterceptor.Level.BODY))
          .build()

  @JvmStatic val account = "1.2.27".toChainObject()
  @JvmStatic val account2 = "1.2.28".toChainObject()
  @JvmStatic val accountName = "public-account-9"
  @JvmStatic val accountName2 = "public-account-10"
  @JvmStatic val private = "5Hxwqx6JJUBYWjQNt8DomTNJ6r6YK8wDJym4CMAH1zGctFyQtzt"
  @JvmStatic val private2 = "5JMpT5C75rcAmuUB81mqVBXbmL1BKea4MYwVK6voMQLvigLKfrE"
  @JvmStatic val public = "DCT6TjLhr8uESvgtxrbWuXNAN3vcqzBMw5eyEup3PMiD2gnVxeuTb"
  @JvmStatic val public2 = "DCT5PwcSiigfTPTwubadt85enxMFC18TtVoti3gnTbG7TN9f9R3Fp"
  @JvmStatic val credentials = Credentials(account, private)

  @JvmStatic val createAccount = "account-test"
  @JvmStatic val createAsset = "TEST"
  @JvmStatic val createAssetId = "1.3.3".toChainObject()
  @JvmStatic val createUri = "http://hello.world.io"
  @JvmStatic val createContentId = "2.13.0".toChainObject()
  @JvmStatic val createContentId2 = "2.13.1".toChainObject()
  @JvmStatic val createPurchaseId = "2.12.0".toChainObject()
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
