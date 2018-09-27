package ch.decent.sdk

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.net.model.request.*
import ch.decent.sdk.net.rpc.RpcService
import ch.decent.sdk.net.ws.RxWebSocket
import com.google.gson.GsonBuilder
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import okhttp3.OkHttpClient
import org.slf4j.Logger
import org.threeten.bp.LocalDateTime

class DCoreSdk private constructor(
    private val client: OkHttpClient,
    webSocketUrl: String? = null,
    restUrl: String? = null,
    private val logger: Logger? = null
) {

  private val rxWebSocket: RxWebSocket? = webSocketUrl?.let { RxWebSocket(client, it, gsonBuilder.create(), logger) }
  private val rpc: RpcService? = restUrl?.let { RpcService(it, client, gsonBuilder.create()) }

  init {
    require(restUrl?.isNotBlank() == true || webSocketUrl?.isNotBlank() == true) { "at least one url must be set" }
  }

  private fun <T> BaseRequest<T>.toRequest(): Single<T> = makeRequest(this)

  private fun prepareTransaction(operations: List<BaseOperation>, expiration: Int): Single<Transaction> =
      operations.partition { it.fee !== BaseOperation.FEE_UNSET }.let { (fees, noFees) ->
        if (noFees.isNotEmpty()) {
          GetRequiredFees(noFees).toRequest().map { noFees.mapIndexed { idx, op -> op.apply { fee = it[idx] } } + fees }
        } else {
          Single.just(fees)
        }
      }.zipWith(
          GetDynamicGlobalProps.toRequest(),
          BiFunction { ops, props -> Transaction(BlockData(props, expiration), ops) }
      )

  internal fun <T> makeRequest(request: BaseRequest<T>): Single<T> {
    check(rxWebSocket != null || rpc != null)
    return if (rxWebSocket != null && (rpc == null || rxWebSocket.connected || request.apiGroup != ApiGroup.DATABASE)) {
      rxWebSocket.request(request)
    } else {
      require(rpc != null && request.apiGroup == ApiGroup.DATABASE) { "not available through HTTP API" }
      rpc!!.request(request)
    }
  }

  fun broadcastWithCallback(keyPair: ECKeyPair, operations: List<BaseOperation>, expiration: Int = defaultExpiration): Single<TransactionConfirmation> =
      prepareTransaction(operations, expiration)
          .map { it.withSignature(keyPair) }
          .flatMap { with(rxWebSocket!!.callId) { BroadcastTransactionWithCallback(it, this).toRequest() } }

  fun broadcast(keyPair: ECKeyPair, operations: List<BaseOperation>, expiration: Int = defaultExpiration): Single<Unit> =
      prepareTransaction(operations, expiration)
          .map { it.withSignature(keyPair) }
          .flatMap { BroadcastTransaction(it).toRequest() }

  companion object {
    /**
     * Specifies expiration time of transactions, after expiry the transaction will be removed form recent's pool
     */
    const val defaultExpiration = 30 //seconds

    @JvmStatic val gsonBuilder = GsonBuilder()
        .disableHtmlEscaping()
        .registerTypeAdapterFactory(OperationTypeFactory)
        .registerTypeAdapter(ChainObject::class.java, ChainObjectAdapter)
        .registerTypeAdapter(Address::class.java, AddressAdapter)
        .registerTypeAdapter(LocalDateTime::class.java, DateTimeAdapter)
        .registerTypeAdapter(AuthMap::class.java, AuthMapAdapter)
        .registerTypeAdapter(PubKey::class.java, PubKeyAdapter)

    fun createForHttp(client: OkHttpClient, url: String, logger: Logger? = null): DCoreApi =
        DCoreApi(DCoreSdk(client, restUrl = url, logger = logger))

    fun createForWebSocket(client: OkHttpClient, url: String, logger: Logger? = null): DCoreApi =
        DCoreApi(DCoreSdk(client, webSocketUrl = url, logger = logger))

    fun create(client: OkHttpClient, webSocketUrl: String, httpUrl: String, logger: Logger? = null): DCoreApi =
        DCoreApi(DCoreSdk(client, webSocketUrl, httpUrl, logger))
  }
}

