package ch.decent.sdk.net.rpc

import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
import ch.decent.sdk.net.model.response.RpcListResponse
import ch.decent.sdk.net.model.response.RpcResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface RpcEndpoints {

//  cannot be blank url string
  @POST("rpc")
  fun lookupAccounts(@Body request: LookupAccounts): Single<RpcListResponse<List<String>>>

  @POST("rpc")
  fun getAccountByName(@Body request: GetAccountByName): Single<RpcResponse<Account>>

  @POST("rpc")
  fun getAccountById(@Body request: GetAccountById): Single<RpcListResponse<Account>>

  @POST("rpc")
  fun getMiners(@Body request: GetMiners): Single<RpcListResponse<Miner>>

  @POST("rpc")
  fun getAccountBalances(@Body request: GetAccountBalances): Single<RpcListResponse<AssetAmount>>

  @POST("rpc")
  fun getAssets(@Body request: GetAssets): Single<RpcListResponse<Asset>>

  @POST("rpc")
  fun searchAccountHistory(@Body request: SearchAccountHistory): Single<RpcListResponse<TransactionDetail>>

  @POST("rpc")
  fun getBuyingsByUri(@Body request: GetBuyingByUri): Single<RpcResponse<Purchase>>

  @POST("rpc")
  fun searchBuyings(@Body request: SearchBuyings): Single<RpcListResponse<Purchase>>

  @POST("rpc")
  fun getContent(@Body request: GetContentById): Single<RpcListResponse<Content>>

  @POST("rpc")
  fun getContent(@Body request: GetContentByUri): Single<RpcResponse<Content>>

  @POST("rpc")
  fun getFees(@Body request: GetRequiredFees): Single<RpcListResponse<AssetAmount>>

  @POST("rpc")
  fun getRecentTransaction(@Body request: GetRecentTransactionById): Single<RpcResponse<ProcessedTransaction>>
}