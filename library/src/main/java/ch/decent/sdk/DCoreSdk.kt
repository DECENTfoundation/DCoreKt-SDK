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
  private val chainId = GetChainId.toRequest().cache()

  init {
    require(restUrl?.isNotBlank() == true || webSocketUrl?.isNotBlank() == true) { "at least one url must be set" }
  }

  private fun <T> BaseRequest<T>.toRequest(): Single<T> = makeRequest(this)

  internal fun prepareTransaction(operations: List<BaseOperation>, expiration: Int): Single<Transaction> =
      chainId.flatMap { id ->
        GetDynamicGlobalProps.toRequest().zipWith(
            operations.partition { it.fee !== BaseOperation.FEE_UNSET }.let { (fees, noFees) ->
              if (noFees.isNotEmpty()) {
                GetRequiredFees(noFees).toRequest().map { noFees.mapIndexed { idx, op -> op.apply { fee = it[idx] } } + fees }
              } else {
                Single.just(fees)
              }
            }, BiFunction { props: DynamicGlobalProps, ops: List<BaseOperation> -> Transaction(BlockData(props, expiration), ops, id) })
      }

  internal fun <T> makeRequest(request: BaseRequest<T>): Single<T> {
    check(rxWebSocket != null || rpc != null)
    return if (rxWebSocket != null && (rpc == null || rxWebSocket.connected || request.apiGroup != ApiGroup.DATABASE)) {
      rxWebSocket.request(request)
    } else {
      if (rpc == null || request.apiGroup != ApiGroup.DATABASE) Single.error(IllegalArgumentException("not available through HTTP API"))
      else rpc.request(request)
    }
  }

  internal fun broadcastWithCallback(transaction: Transaction) =
      with(rxWebSocket!!.callId) { BroadcastTransactionWithCallback(transaction, this).toRequest() }

  fun broadcastWithCallback(keyPair: ECKeyPair, operations: List<BaseOperation>, expiration: Int = DCoreConstants.DEFAULT_EXPIRATION): Single<TransactionConfirmation> =
      prepareTransaction(operations, expiration)
          .map { it.withSignature(keyPair) }
          .flatMap { broadcastWithCallback(it) }

  fun broadcast(keyPair: ECKeyPair, operations: List<BaseOperation>, expiration: Int = DCoreConstants.DEFAULT_EXPIRATION): Single<Unit> =
      prepareTransaction(operations, expiration)
          .map { it.withSignature(keyPair) }
          .flatMap { BroadcastTransaction(it).toRequest() }

  companion object {
    @JvmStatic val gsonBuilder = GsonBuilder()
        .disableHtmlEscaping()
        .registerTypeAdapterFactory(OperationTypeFactory)
        .registerTypeAdapter(ChainObject::class.java, ChainObjectAdapter)
        .registerTypeAdapter(Address::class.java, AddressAdapter)
        .registerTypeAdapter(LocalDateTime::class.java, DateTimeAdapter)
        .registerTypeAdapter(AuthMap::class.java, AuthMapAdapter)
        .registerTypeAdapter(PubKey::class.java, PubKeyAdapter)
        .registerTypeAdapter(MinerId::class.java, MinerIdAdapter)

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