package ch.decent.sdk

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.net.TrustAllCerts
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Helpers {
  @JvmStatic val wsUrl = "wss://testnet-api.dcore.io"
  @JvmStatic val restUrl = "https://testnet-api.dcore.io/"
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
}

fun Any.print() = println(this.toString())
