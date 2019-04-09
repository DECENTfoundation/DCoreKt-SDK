@file:JvmName("Helpers")

package ch.decent.sdk

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.net.TrustAllCerts
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val url = "wss://testnet-api.dcore.io"
val restUrl = "https://testnet-api.dcore.io/"
fun client(logger: Logger = LoggerFactory.getLogger("OkHttpClient")): OkHttpClient =
    TrustAllCerts.wrap(OkHttpClient.Builder())
        .addInterceptor(HttpLoggingInterceptor { logger.info(it) }.setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

val account = ChainObject.parse("1.2.27")
val account2 = ChainObject.parse("1.2.28")
val accountName = "public-account-9"
val accountName2 = "public-account-10"
val private = "5Hxwqx6JJUBYWjQNt8DomTNJ6r6YK8wDJym4CMAH1zGctFyQtzt"
val private2 = "5JMpT5C75rcAmuUB81mqVBXbmL1BKea4MYwVK6voMQLvigLKfrE"
val public = "DCT6TjLhr8uESvgtxrbWuXNAN3vcqzBMw5eyEup3PMiD2gnVxeuTb"
val public2 = "DCT5PwcSiigfTPTwubadt85enxMFC18TtVoti3gnTbG7TN9f9R3Fp"

fun Any.print() = println(this.toString())