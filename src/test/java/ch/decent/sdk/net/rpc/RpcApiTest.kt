package ch.decent.sdk.net.rpc

import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.account
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.TransactionDetail
import ch.decent.sdk.net.TrustAllCerts
import ch.decent.sdk.net.model.request.GetAccountBalances
import ch.decent.sdk.net.model.request.LookupAccounts
import ch.decent.sdk.net.model.request.SearchAccountHistory
import ch.decent.sdk.net.model.response.RpcListResponse
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RpcApiTest {
  private val service = Retrofit.Builder()
      .baseUrl("https://stage.decentgo.com:8090/")
      .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
      .addConverterFactory(GsonConverterFactory.create(DCoreSdk.gsonBuilder.create()))
      .client(
          TrustAllCerts.wrap(OkHttpClient.Builder())
              .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
              .build()
      )
      .build()
      .create(RpcEndpoints::class.java)

  @Test fun `lookup all accounts`() {
    val observer = TestObserver<RpcListResponse<List<String>>>()

    service.lookupAccounts(LookupAccounts("", 1))
        .doOnSuccess { println(it) }
        .subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertNoErrors()
        .assertValueCount(1)
  }

  @Test fun `fetch account balance`() {
    val observer = TestObserver<RpcListResponse<AssetAmount>>()

    service.getAccountBalances(GetAccountBalances(account))
        .doOnSuccess { println(it) }
        .subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertNoErrors()
        .assertValueCount(1)
  }

  @Test fun `fetch account history`() {
    val observer = TestObserver<RpcListResponse<TransactionDetail>>()

    service.searchAccountHistory(SearchAccountHistory(account))
        .doOnSuccess { println(it) }
        .subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertNoErrors()
        .assertValueCount(1)
  }
}