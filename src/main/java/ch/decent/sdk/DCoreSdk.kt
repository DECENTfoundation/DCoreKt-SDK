package ch.decent.sdk

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.*
import ch.decent.sdk.model.SearchAccountHistoryOrder
import ch.decent.sdk.model.SearchPurchasesOrder
import ch.decent.sdk.net.model.request.*
import ch.decent.sdk.net.rpc.RpcEndpoints
import ch.decent.sdk.net.ws.RxWebSocket
import ch.decent.sdk.utils.publicElGamal
import com.google.gson.GsonBuilder
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import okhttp3.OkHttpClient
import org.slf4j.Logger
import org.threeten.bp.LocalDateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class DCoreSdk private constructor(
    private val client: OkHttpClient,
    webSocketUrl: String?,
    restUrl: String?,
    private val logger: Logger?
) : DCoreApiRx {

  private val rxWebSocket: RxWebSocket? = webSocketUrl?.let { RxWebSocket(client, it, gsonBuilder.create(), logger) }
  private val service: RpcEndpoints? = restUrl?.let {
    Retrofit.Builder()
        .baseUrl(it)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
        .client(client)
        .build()
        .create(RpcEndpoints::class.java)
  }

  init {
    require(restUrl?.isNotBlank() == true || webSocketUrl?.isNotBlank() == true, { "at least one url must be set" })
  }

  private fun <T, U : BaseRequest<T>> U.toRequest(rpc: (RpcEndpoints.(r: U) -> Single<T>)? = null): Single<T> {
    return if (rpc != null && service != null && rxWebSocket?.connected == false) {
      rpc(service, this)
    } else {
      rxWebSocket?.request(this) ?: Single.error(IllegalAccessException("websocket API not available, no url set"))
    }
  }

  fun prepareTransaction(op: List<BaseOperation>): Single<Transaction> =
      Single.zip(
          GetDynamicGlobalProps.toRequest(),
          GetRequiredFees(op).toRequest(),
          BiFunction { props: DynamicGlobalProps, fees: List<AssetAmount> ->
            Transaction(BlockData(props), op.mapIndexed { idx, op -> op.apply { fee = fees[idx] } })
          }
      )

  override fun getFees(op: List<BaseOperation>): Single<List<AssetAmount>> =
      GetRequiredFees(op).toRequest { this.getFees(it).map { it.result() } }

  override fun getMiners(minerIds: Set<ChainObject>): Single<List<Miner>> =
      GetMiners(minerIds).toRequest { getMiners(it).map { it.result() } }

  override fun getBalance(accountId: ChainObject): Single<List<AssetAmount>> =
      GetAccountBalances(accountId).toRequest { getAccountBalances(it).map { it.result() } }

  override fun createAccount(keyPair: ECKeyPair, registrar: ChainObject, name: String, owner: Authority, active: Authority, options: Options) =
      makeTransactionWithCallback(keyPair, AccountCreateOperation(registrar, name, owner, active, options))

  override fun getAccountByName(name: String): Single<Account> =
      GetAccountByName(name).toRequest { getAccountByName(it).map { it.result(GetAccountByName(name).description()) } }

  override fun getAccountById(accountId: ChainObject): Single<Account> =
      GetAccountById(accountId).toRequest { getAccountById(it).map { it.result() } }.map { it.firstOrNull() ?: throw ObjectNotFoundException(GetAccountById(accountId).description()) }

  override fun searchAccountHistory(accountId: ChainObject, order: SearchAccountHistoryOrder, from: ChainObject, limit: Int): Single<List<TransactionDetail>> =
      SearchAccountHistory(accountId, order, from, limit).toRequest { searchAccountHistory(it).map { it.result() } }

  override fun searchPurchases(consumer: ChainObject, order: SearchPurchasesOrder, from: ChainObject, term: String, limit: Int): Single<List<Purchase>> =
      SearchBuyings(consumer, order, from, term, limit).toRequest { searchBuyings(it).map { it.result() } }

  override fun getPurchase(consumer: ChainObject, uri: String): Single<Purchase> =
      GetBuyingByUri(consumer, uri).toRequest { getBuyingsByUri(it).map { it.result(GetBuyingByUri(consumer, uri).description()) } }

  override fun getContent(contentId: ChainObject): Single<Content> =
      GetContentById(contentId).toRequest { getContent(it).map { it.result() } }.map {
        it.firstOrNull() ?: throw ObjectNotFoundException(GetContentById(contentId).description())
      }

  override fun getContent(uri: String): Single<Content> =
      GetContentByUri(uri).toRequest { getContent(it).map { it.result(GetContentByUri(uri).description()) } }

  override fun getAssets(assetIds: List<ChainObject>): Single<List<Asset>> =
      GetAssets(assetIds).toRequest { getAssets(it).map { it.result() } }

  override fun lookupAssets(assetSymbols: List<String>): Single<List<Asset>> =
      LookupAssets(assetSymbols).toRequest { lookupAssets(it).map { it.result() } }

  override fun getAccountHistory(accountId: ChainObject, limit: Int, startId: ChainObject, stopId: ChainObject): Single<List<OperationHistory>> =
      GetAccountHistory(accountId, stopId, limit, startId).toRequest()

  override fun getRecentTransaction(trxId: String): Single<ProcessedTransaction> =
      GetRecentTransactionById(trxId).toRequest { getRecentTransaction(it).map { it.result(GetRecentTransactionById(trxId).description()) } }

  override fun getTransaction(blockNum: Long, trxInBlock: Long): Single<ProcessedTransaction> =
      GetTransaction(blockNum, trxInBlock).toRequest { getTransaction(it).map { it.result(GetTransaction(blockNum, trxInBlock).description()) } }

  override fun transfer(keyPair: ECKeyPair, from: ChainObject, to: ChainObject, amount: AssetAmount, memo: String?, encrypted: Boolean): Single<TransactionConfirmation> =
      getAccountById(to)
          .flatMap { recipient ->
            val msg = if (memo.isNullOrBlank()) null else {
              if (encrypted) Memo(memo!!, keyPair, recipient.active.keyAuths.first().value) else Memo(memo!!)
            }
            makeTransactionWithCallback(keyPair, TransferOperation(from, to, amount, msg))
          }

  override fun buyContent(keyPair: ECKeyPair, content: Content, consumer: ChainObject): Single<TransactionConfirmation> =
      makeTransactionWithCallback(keyPair, BuyContentOperation(content.uri, consumer, content.price(),
          if (URL(content.uri).protocol != "ipfs") PubKey() else keyPair.publicElGamal()))

  override fun voteForMiners(keyPair: ECKeyPair, accountId: ChainObject, voteIds: Set<String>): Single<TransactionConfirmation> =
      getAccountById(accountId)
          .flatMap {
            makeTransactionWithCallback(keyPair, AccountUpdateOperation(
                it.id, options = it.options.copy(votes = voteIds)
            ))
          }

  fun makeTransactionWithCallback(keyPair: ECKeyPair, op: BaseOperation): Single<TransactionConfirmation> = makeTransactionWithCallback(keyPair, listOf(op))

  fun makeTransactionWithCallback(keyPair: ECKeyPair, op: List<BaseOperation>): Single<TransactionConfirmation> =
      prepareTransaction(op)
          .map { it.withSignature(keyPair) }
          .flatMap {
            val callId = rxWebSocket!!.callId
            rxWebSocket.request(BroadcastTransactionWithCallback(it, callId))
          }

  fun makeTransaction(keyPair: ECKeyPair, op: List<BaseOperation>): Single<Unit> =
      prepareTransaction(op)
          .map { it.withSignature(keyPair) }
          .flatMap { BroadcastTransaction(it).toRequest() }

  companion object {
    /**
     * Specifies expiration time of transactions, after expiry the transaction will be removed form recent's pool
     */
    @JvmStatic var transactionExpiration = 30

    @JvmStatic val gsonBuilder = GsonBuilder()
        .disableHtmlEscaping()
        .registerTypeAdapterFactory(OperationTypeFactory)
        .registerTypeAdapterFactory(SynopsisAdapterFactory)
        .registerTypeAdapter(ChainObject::class.java, ChainObjectAdapter)
        .registerTypeAdapter(Address::class.java, AddressAdapter)
        .registerTypeAdapter(LocalDateTime::class.java, DateTimeAdapter)
        .registerTypeAdapter(AuthMap::class.java, AuthMapAdapter)
        .registerTypeAdapter(PubKey::class.java, PubKeyAdapter)

    fun createApiRx(client: OkHttpClient, webSocketUrl: String? = null, restUrl: String? = null, logger: Logger? = null): DCoreApiRx =
        DCoreSdk(client, webSocketUrl, restUrl, logger)

    @JvmOverloads @JvmStatic
    fun createApi(client: OkHttpClient, webSocketUrl: String? = null, restUrl: String? = null, logger: Logger? = null): DCoreApi = object : DCoreApi {
      override val apiRx: DCoreApiRx = createApiRx(client, webSocketUrl, restUrl, logger)
    }

  }
}

