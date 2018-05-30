package ch.decent.sdk.net.services

import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.net.TrustAllCerts
import ch.decent.sdk.net.rpc.RpcEndpoints
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseRestApiTest : MockServer {

  protected lateinit var service: RpcEndpoints
  protected var mockServer: MockWebServer? = null

  @Before fun init() {
    val shouldRunMockServer = runMockServer()
    if (shouldRunMockServer) {
      mockServer = MockWebServer().apply { start() }
    }
    service = Retrofit.Builder()
        .baseUrl(if (shouldRunMockServer) mockServer!!.url("/").toString() else "https://stage.decentgo.com:8090/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(GsonConverterFactory.create(DCoreSdk.gsonBuilder.create()))
        .client(
            TrustAllCerts.wrap(OkHttpClient.Builder())
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .build()
        .create(RpcEndpoints::class.java)
  }

  @After fun finish() {
    mockServer?.shutdown()
  }
}