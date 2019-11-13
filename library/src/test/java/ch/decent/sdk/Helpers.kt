package ch.decent.sdk

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetObjectId
import ch.decent.sdk.model.ContentObjectId
import ch.decent.sdk.model.MessagingObjectId
import ch.decent.sdk.model.PurchaseObjectId
import ch.decent.sdk.model.toObjectId
import ch.decent.sdk.net.TrustAllCerts
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Helpers {
  @JvmStatic val testnetWs = "wss://testnet-socket.dcore.io/"
  @JvmStatic val testnetHttp = "https://testnet.dcore.io/"
  @JvmStatic val stageWs = "wss://stagesocket.decentgo.com:8090"
  @JvmStatic val stageHttp = "https://stagesocket.decentgo.com"
  @JvmStatic val mainWs = "wss://api.decent.ch"
  @JvmStatic val mainHttp = "https://api.decent.ch"
  @JvmStatic val dockerWs = "ws://localhost:8090/"
  @JvmStatic val dockerHttp = "http://localhost:8090/"
  @JvmStatic val wsUrl = dockerWs
  @JvmStatic val httpUrl = dockerHttp

  @JvmStatic fun client(logger: Logger = LoggerFactory.getLogger("OkHttpClient")): OkHttpClient =
      TrustAllCerts.wrap(OkHttpClient.Builder())
          .addInterceptor(HttpLoggingInterceptor { logger.info(it) }.setLevel(HttpLoggingInterceptor.Level.BODY))
          .build()

  @JvmStatic val account = "1.2.27".toObjectId<AccountObjectId>()
  @JvmStatic val account2 = "1.2.28".toObjectId<AccountObjectId>()
  @JvmStatic val accountName = "public-account-9"
  @JvmStatic val accountName2 = "public-account-10"
  @JvmStatic val private = "5Hxwqx6JJUBYWjQNt8DomTNJ6r6YK8wDJym4CMAH1zGctFyQtzt"
  @JvmStatic val private2 = "5JMpT5C75rcAmuUB81mqVBXbmL1BKea4MYwVK6voMQLvigLKfrE"
  @JvmStatic val public = "DCT6TjLhr8uESvgtxrbWuXNAN3vcqzBMw5eyEup3PMiD2gnVxeuTb"
  @JvmStatic val public2 = "DCT5PwcSiigfTPTwubadt85enxMFC18TtVoti3gnTbG7TN9f9R3Fp"
  @JvmStatic val credentials = Credentials(account, private)
  @JvmStatic val credentials2 = Credentials(account2, private2)

  @JvmStatic val createAccount = "account-test"
  @JvmStatic val createAsset = "TEST"
  @JvmStatic val createAssetId = "1.3.3".toObjectId<AssetObjectId>()
  @JvmStatic val createUri = "http://hello.world.io"
  @JvmStatic val createContentId = "2.13.0".toObjectId<ContentObjectId>()
  @JvmStatic val createContentId2 = "2.13.1".toObjectId<ContentObjectId>()
  @JvmStatic val createPurchaseId = "2.12.0".toObjectId<PurchaseObjectId>()
  @JvmStatic val messageId1 = "2.18.0".toObjectId<MessagingObjectId>()
  @JvmStatic val messageId2 = "2.18.1".toObjectId<MessagingObjectId>()
  @JvmStatic val createNft = "APPLE"
  @JvmStatic val createNftNested = "$createNft.NESTED"
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
  test.values().print()
}
