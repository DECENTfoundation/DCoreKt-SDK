package ch.decent.sdk

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.AddressAdapter
import ch.decent.sdk.model.AuthMap
import ch.decent.sdk.model.AuthMapAdapter
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ChainObjectAdapter
import ch.decent.sdk.model.CoAuthors
import ch.decent.sdk.model.CoAuthorsAdapter
import ch.decent.sdk.model.DateTimeAdapter
import ch.decent.sdk.model.DynamicGlobalProps
import ch.decent.sdk.model.FeeParamAdapter
import ch.decent.sdk.model.FeeParameter
import ch.decent.sdk.model.MapAdapterFactory
import ch.decent.sdk.model.MinerId
import ch.decent.sdk.model.MinerIdAdapter
import ch.decent.sdk.model.NftModelAdapter
import ch.decent.sdk.model.OperationTypeAdapter
import ch.decent.sdk.model.OperationTypeFactory
import ch.decent.sdk.model.PubKey
import ch.decent.sdk.model.PubKeyAdapter
import ch.decent.sdk.model.RawNft
import ch.decent.sdk.model.StaticVariantFactory
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.VoteId
import ch.decent.sdk.model.VoteIdAdapter
import ch.decent.sdk.model.operation.BaseOperation
import ch.decent.sdk.model.operation.OperationType
import ch.decent.sdk.net.model.request.BaseRequest
import ch.decent.sdk.net.model.request.GetChainId
import ch.decent.sdk.net.model.request.GetDynamicGlobalProps
import ch.decent.sdk.net.model.request.GetRequiredFees
import ch.decent.sdk.net.model.request.WithCallback
import ch.decent.sdk.net.rpc.RpcService
import ch.decent.sdk.net.ws.RxWebSocket
import com.google.gson.GsonBuilder
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import okhttp3.OkHttpClient
import org.slf4j.Logger
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

class DCoreSdk private constructor(
    private val client: OkHttpClient,
    webSocketUrl: String? = null,
    restUrl: String? = null,
    private val logger: Logger? = null
) {
  internal val gson = gsonBuilder.create()
  private val rxWebSocket: RxWebSocket? = webSocketUrl?.let { RxWebSocket(client, it, gson, logger) }
  private val rpc: RpcService? = restUrl?.let { RpcService(it, client, gson) }
  private val chainId = makeRequest(GetChainId).cache()

  init {
    require(restUrl?.isNotBlank() == true || webSocketUrl?.isNotBlank() == true) { "at least one url must be set" }
  }

  internal fun prepareTransaction(operations: List<BaseOperation>, expiration: Duration): Single<Transaction> =
      chainId.flatMap { id ->
        makeRequest(GetDynamicGlobalProps).zipWith(
            operations.partition { it.fetchFee }.let { (noFees, fees) ->
              if (noFees.isNotEmpty()) {
                val feeRequests = noFees.groupBy { it.fee.assetId }.map { (assetId, ops) ->
                  makeRequest(GetRequiredFees(ops, assetId)).map { ops.mapIndexed { idx, op -> op.apply { fee = it[idx] } } }
                }
                Single.merge(feeRequests).toList().map { it.flatten() + fees }
              } else {
                Single.just(fees)
              }
            }, BiFunction { props: DynamicGlobalProps, ops: List<BaseOperation> -> Transaction.create(ops, id, props, expiration) })
      }

  internal fun <T, R> makeRequestStream(request: T): Flowable<R> where T : BaseRequest<R>, T : WithCallback =
      rxWebSocket?.requestStream(request) ?: Flowable.error(IllegalArgumentException("callbacks not available through HTTP API"))

  internal fun <T> makeRequest(request: BaseRequest<T>): Single<T> {
    check(rxWebSocket != null || rpc != null)
    return when {
      request is WithCallback && rxWebSocket != null -> rxWebSocket.request(request)
      request is WithCallback && rxWebSocket == null -> Single.error(IllegalArgumentException("callbacks not available through HTTP API"))
      rxWebSocket?.connected ?: false -> rxWebSocket!!.request(request)
      rpc != null -> rpc.request(request)
      else -> rxWebSocket!!.request(request)
    }
  }

  internal fun setTimeout(seconds: Long) {
    rxWebSocket?.timeout = seconds
  }

  companion object {
    @JvmStatic val gsonBuilder = GsonBuilder()
        .disableHtmlEscaping()
        .registerTypeAdapterFactory(OperationTypeFactory)
        .registerTypeAdapterFactory(StaticVariantFactory)
        .registerTypeAdapterFactory(MapAdapterFactory)
        .registerTypeAdapter(ChainObject::class.java, ChainObjectAdapter)
        .registerTypeAdapter(Address::class.java, AddressAdapter)
        .registerTypeAdapter(LocalDateTime::class.java, DateTimeAdapter)
        .registerTypeAdapter(AuthMap::class.java, AuthMapAdapter)
        .registerTypeAdapter(PubKey::class.java, PubKeyAdapter)
        .registerTypeAdapter(MinerId::class.java, MinerIdAdapter)
        .registerTypeAdapter(FeeParameter::class.java, FeeParamAdapter)
        .registerTypeAdapter(OperationType::class.java, OperationTypeAdapter)
        .registerTypeAdapter(VoteId::class.java, VoteIdAdapter)
        .registerTypeAdapter(CoAuthors::class.java, CoAuthorsAdapter)
        .registerTypeAdapter(RawNft::class.java, NftModelAdapter)

    @JvmStatic @JvmOverloads
    fun createForHttp(client: OkHttpClient, url: String, logger: Logger? = null): DCoreApi =
        DCoreApi(DCoreSdk(client, restUrl = url, logger = logger))

    @JvmStatic @JvmOverloads
    fun createForWebSocket(client: OkHttpClient, url: String, logger: Logger? = null): DCoreApi =
        DCoreApi(DCoreSdk(client, webSocketUrl = url, logger = logger))

    @JvmStatic @JvmOverloads
    fun create(client: OkHttpClient, webSocketUrl: String, httpUrl: String, logger: Logger? = null): DCoreApi =
        DCoreApi(DCoreSdk(client, webSocketUrl, httpUrl, logger))
  }
}
