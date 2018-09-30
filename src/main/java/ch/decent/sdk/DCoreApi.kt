package ch.decent.sdk

import ch.decent.sdk.api.*
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
import io.reactivex.Single
import java.math.BigInteger

class DCoreApi internal constructor(private val core: DCoreSdk) {

  internal fun <T> BaseRequest<T>.toRequest(): Single<T> = core.makeRequest(this)

  val account = object : AccountApi {
    override fun getAccountByName(name: String): Single<Account> = GetAccountByName(name).toRequest()

    override fun getAccountsByIds(accountIds: List<ChainObject>): Single<List<Account>> = GetAccountById(accountIds).toRequest()

    override fun getAccountIdsByKeys(keys: List<Address>): Single<List<List<ChainObject>>> = GetKeyReferences(keys).run {
      toRequest().doOnSuccess { if (it.size == 1 && it.first().isEmpty()) throw ObjectNotFoundException(description()) }
    }

    override fun searchAccountHistory(accountId: ChainObject, order: SearchAccountHistoryOrder, from: ChainObject, limit: Int): Single<List<TransactionDetail>> =
        SearchAccountHistory(accountId, order, from, limit).toRequest()
  }

  val asset = object : AssetApi {
    override fun getAssets(assetIds: List<ChainObject>): Single<List<Asset>> = GetAssets(assetIds).toRequest()

    override fun lookupAssets(assetSymbols: List<String>): Single<List<Asset>> = LookupAssets(assetSymbols).toRequest()

    override fun getFees(op: List<BaseOperation>): Single<List<AssetAmount>> = GetRequiredFees(op).toRequest()
  }

  val authority = object : AuthorityApi {}

  val balance = object : BalanceApi {
    override fun getBalance(account: ChainObject, assets: Set<ChainObject>): Single<List<AssetAmount>> = GetAccountBalances(account, assets).toRequest()

    override fun getBalance(accountReference: String, assets: Set<ChainObject>): Single<List<AssetAmount>> = when {
      Account.isValidName(accountReference) -> GetNamedAccountBalances(accountReference, assets).toRequest()
      else -> account.getAccountId(accountReference).flatMap { getBalance(it, assets) }
    }

    override fun getBalanceWithAsset(account: ChainObject, assetSymbols: Set<String>): Single<Map<Asset, BigInteger>> =
        asset.lookupAssets(assetSymbols.toList()).flatMap { assets ->
          getBalance(account, assets.map { it.id }.toSet()).map {
            it.associate { balance -> assets.single { it.id == balance.assetId } to balance.amount }
          }
        }

    override fun getBalanceWithAsset(accountReference: String, assetSymbols: Set<String>): Single<Map<Asset, BigInteger>> =
        account.getAccountId(accountReference).flatMap { getBalanceWithAsset(it, assetSymbols) }
  }

  val block = object : BlockApi {}

  val broadcast = object : BroadcastApi {
    override fun broadcast(privateKey: ECKeyPair, operations: List<BaseOperation>, expiration: Int): Single<Unit> =
        core.broadcast(privateKey, operations, expiration)

    override fun broadcastWithCallback(privateKey: ECKeyPair, operations: List<BaseOperation>, expiration: Int): Single<TransactionConfirmation> =
        core.broadcastWithCallback(privateKey, operations, expiration)

    override fun broadcast(transaction: Transaction): Single<Unit> = BroadcastTransaction(transaction).toRequest()

    override fun broadcastWithCallback(transaction: Transaction): Single<TransactionConfirmation> = core.broadcastWithCallback(transaction)
  }

  val content = object : ContentApi {
    override fun getContent(contentId: ChainObject): Single<Content> = GetContentById(contentId).toRequest().map { it.single() }

    override fun getContent(uri: String): Single<Content> = GetContentByUri(uri).toRequest()
  }

  val general = object : GeneralApi {}

  val history = object : HistoryApi {
    override fun getAccountHistory(accountId: ChainObject, limit: Int, startId: ChainObject, stopId: ChainObject): Single<List<OperationHistory>> =
        GetAccountHistory(accountId, stopId, limit, startId).toRequest()
  }

  val mining = object : MiningApi {
    override fun getMiners(minerIds: Set<ChainObject>): Single<List<Miner>> = GetMiners(minerIds).toRequest()

    override fun lookupMiners(term: String, limit: Int): Single<List<MinerId>> = LookupMinerAccounts(term, limit).toRequest()
  }

  val purchase = object : PurchaseApi {
    override fun searchPurchases(consumer: ChainObject, term: String, order: SearchPurchasesOrder, from: ChainObject, limit: Int): Single<List<Purchase>> =
        SearchBuyings(consumer, order, from, term, limit).toRequest()

    override fun getPurchase(consumer: ChainObject, uri: String): Single<Purchase> =
        GetBuyingByUri(consumer, uri).toRequest()
  }

  val seeders = object : SeedersApi {}

  val subscription = object : SubscriptionApi {}

  val transaction = object : TransactionApi {
    override fun getRecentTransaction(trxId: String): Single<ProcessedTransaction> = GetRecentTransactionById(trxId).toRequest()

    override fun getTransaction(blockNum: Long, trxInBlock: Long): Single<ProcessedTransaction> =
        GetTransaction(blockNum, trxInBlock).toRequest()

    override fun createTransaction(operations: List<BaseOperation>, expiration: Int): Single<Transaction> =
        core.prepareTransaction(operations, expiration)
  }

  val operations = object : OperationsHelper {

    override fun createTransfer(credentials: Credentials, to: String, amount: AssetAmount, memo: String?, encrypted: Boolean, fee: AssetAmount): Single<TransferOperation> =
        account.getAccount(to).map { receiver ->
          if (memo.isNullOrBlank() || !encrypted) {
            TransferOperation(credentials.account, receiver.id, amount, memo?.let { Memo(it) }, fee)
          } else {
            TransferOperation(credentials.account, receiver.id, amount, Memo(memo!!, credentials.keyPair, receiver.active.keyAuths.first().value), fee)
          }
        }

    override fun createBuyContent(credentials: Credentials, contentId: ChainObject): Single<BuyContentOperation> =
        content.getContent(contentId).map { BuyContentOperation(credentials, it) }

    override fun createBuyContent(credentials: Credentials, uri: String): Single<BuyContentOperation> =
        content.getContent(uri).map { BuyContentOperation(credentials, it) }

    override fun createVote(account: ChainObject, minerIds: Set<ChainObject>): Single<AccountUpdateOperation> =
        mining.getMiners(minerIds).flatMap { miners ->
          this@DCoreApi.account.getAccountsByIds(listOf(account)).map { AccountUpdateOperation(it.first(), miners.map { it.voteId }.toSet()) }
        }

  }
}