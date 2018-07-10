package ch.decent.sdk

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.net.TrustAllCerts
import ch.decent.sdk.net.ws.RxWebSocket
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.LoggerFactory

val url = "wss://stagesocket.decentgo.com:8090"
val restUrl = "https://stage.decentgo.com/"
val client: OkHttpClient = TrustAllCerts.wrap(OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)))
    .build()

val account = ChainObject.parse("1.2.34")
val account2 = ChainObject.parse("1.2.35")
val accountName = "u961279ec8b7ae7bd62f304f7c1c3d345"
val accountName2 = "u3a7b78084e7d3956442d5a4d439dad51"
val private = "5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn"
val private2 = "5JVHeRffGsKGyDf7T9i9dBbzVHQrYprYeaBQo2VCSytj7BxpMCq"
val public = "DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz"
val public2 = "DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP"

fun Any.print() = println(this.toString())