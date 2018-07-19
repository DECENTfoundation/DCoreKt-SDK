package ch.decent.sdk

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.*
import ch.decent.sdk.net.serialization.VoteId
import io.reactivex.Single
import java.math.BigDecimal

interface DCoreApiRx {

  /**
   * get account balance
   *
   * @param accountId object id of the account, 1.2.*
   * @return list of amounts for different assets
   */
  fun getBalance(accountId: ChainObject): Single<List<AssetAmount>>

  /**
   * get account balance
   *
   * @param accountName name of the account
   * @return list of amounts for different assets
   */
  fun getBalance(accountName: String): Single<List<AssetAmount>> =
      getAccountByName(accountName).flatMap { getBalance(it.id) }

  /**
   * get account balance
   *
   * @param accountName name of the account
   * @param assetSymbol symbol of the asset eg. DCT
   *
   * @return amount owned for specified asset or [ch.decent.sdk.exception.ObjectNotFoundException] if asset does not exist
   */
  fun getBalance(accountName: String, assetSymbol: String): Single<BigDecimal> =
      lookupAssets(listOf(assetSymbol))
          .map { it.first() }
          .flatMap { asset ->
            getBalance(accountName).map { it.find { it.assetId == asset.id }?.let { asset.fromBase(it.amount) } ?: BigDecimal.ZERO }
          }

  /**
   * get assets by id
   *
   * @param assetIds asset id eg. DCT id is 1.3.0
   *
   * @return list of assets or empty
   */
  fun getAssets(assetIds: List<ChainObject>): Single<List<Asset>>

  /**
   * lookup assets by symbol
   *
   * @param assetSymbols asset symbols eg. DCT
   *
   * @return list of assets or empty
   */
  fun lookupAssets(assetSymbols: List<String>): Single<List<Asset>>

  /**
   * get Account object by name
   *
   * @param name the name of the account
   * @return an account if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getAccountByName(name: String): Single<Account>

  /**
   * get Account object by id
   *
   * @param accountId object id of the account, 1.2.*
   * @return an account if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getAccountById(accountId: ChainObject): Single<Account>

  /**
   * get account's object id by public key address
   *
   * @param address formatted public key of the account, eg. DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4
   * @return an account's object id if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getAccountIdByAddress(address: Address): Single<ChainObject>

  /**
   * get Account object by public key address
   *
   * @param address formatted public key of the account, eg. DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4
   * @return an account if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getAccountByAddress(address: Address): Single<Account> = getAccountIdByAddress(address).flatMap { getAccountById(it) }

  /**
   * search account history
   *
   * @param accountId object id of the account, 1.2.*
   * @param order
   * @param from object id of the history object to start from, use 0.0.0 to ignore
   * @param limit number of entries, max 100
   */
  fun searchAccountHistory(
      accountId: ChainObject,
      order: SearchAccountHistoryOrder = SearchAccountHistoryOrder.TIME_DESC,
      from: ChainObject = ChainObject.NONE,
      limit: Int = 100
  ): Single<List<TransactionDetail>>

  /**
   * search consumer open and history purchases
   *
   * @param consumer object id of the account, 1.2.*
   * @param order
   * @param from object id of the history object to start from, use 0.0.0 to ignore
   * @param term
   * @param limit number of entries, max 100
   */
  fun searchPurchases(
      consumer: ChainObject,
      order: SearchPurchasesOrder = SearchPurchasesOrder.PURCHASED_DESC,
      from: ChainObject = ChainObject.NONE,
      term: String = "",
      limit: Int = 100
  ): Single<List<Purchase>>

  /**
   * get consumer buying by content uri
   *
   * @param consumer object id of the account, 1.2.*
   * @param uri a uri of the content
   *
   * @return an account if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getPurchase(
      consumer: ChainObject,
      uri: String
  ): Single<Purchase>

  /**
   * get content by id
   *
   * @param contentId object id of the content, 2.13.*
   *
   * @return a content if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getContent(contentId: ChainObject): Single<Content>

  /**
   * get content by uri
   *
   * @param uri Uri of the content
   *
   * @return a content if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getContent(uri: String): Single<Content>

  /**
   * get account history
   *
   * @param accountId object id of the account, 1.2.*
   * @param limit number of entries, max 100
   * @param startId id of the history object to start from, use 1.7.0 to ignore
   * @param stopId id of the history object to stop at, use 1.7.0 to ignore
   *
   * @return list of history operations
   */
  fun getAccountHistory(
      accountId: ChainObject,
      limit: Int = 100,
      startId: ChainObject = ChainObject.parse("1.7.0"),
      stopId: ChainObject = ChainObject.parse("1.7.0")
  ): Single<List<OperationHistory>>

  /**
   * get account history
   *
   * @param account account name
   *
   * @return list of history operations, first 100 entries
   */
  fun getAccountHistory(
      account: String
  ): Single<List<OperationHistory>> = getAccountByName(account).flatMap { getAccountHistory(it.id) }

  /**
   * If the transaction has not expired, this method will return the transaction for the given ID or it will return [ch.decent.sdk.exception.ObjectNotFoundException].
   * Just because it is not known does not mean it wasn't included in the blockchain. The ID can be retrieved from [Transaction] or [TransactionConfirmation] objects.
   * You can set up a custom expiration value in [DCoreSdk.transactionExpiration]
   *
   * @param trxId transaction id
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getRecentTransaction(trxId: String): Single<ProcessedTransaction>

  /**
   * get applied transaction
   *
   * @param blockNum block number
   * @param trxInBlock position of the transaction in block
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getTransaction(blockNum: Long, trxInBlock: Long): Single<ProcessedTransaction>

  /**
   * get applied transaction
   *
   * @param confirmation confirmation returned from transaction broadcast
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getTransaction(confirmation: TransactionConfirmation): Single<ProcessedTransaction> = getTransaction(confirmation.blockNum, confirmation.trxNum)

  /**
   * make a transfer
   *
   * @param keyPair private keys
   * @param from object id of the sender account, 1.2.*
   * @param to object id of the receiver account, 1.2.*
   * @param amount amount to send with asset type
   * @param memo optional message
   * @param encrypted encrypted is visible only for sender and receiver, unencrypted is visible publicly
   *
   * @return a transaction confirmation
   */
  fun transfer(
      keyPair: ECKeyPair,
      from: ChainObject,
      to: ChainObject,
      amount: AssetAmount,
      memo: String? = null,
      encrypted: Boolean = true,
      fee: AssetAmount = AssetAmount.FEE_UNSET
  ): Single<TransactionConfirmation>

  /**
   * make a transfer
   *
   * @param credentials user credentials
   * @param to object id of the receiver account, 1.2.*
   * @param amount amount to send with asset type
   * @param memo optional message
   * @param encrypted encrypted is visible only for sender and receiver, unencrypted is visible publicly
   *
   * @return a transaction confirmation
   */
  fun transfer(
      credentials: Credentials,
      to: ChainObject,
      amount: AssetAmount,
      memo: String? = null,
      encrypted: Boolean = true
  ): Single<TransactionConfirmation> = transfer(credentials.keyPair, credentials.account, to, amount, memo, encrypted)

  /**
   * make a transfer
   *
   * @param credentials user credentials
   * @param to object id of the receiver account (1.2.*) or account name or public key
   * @param amount amount to send with asset type
   * @param memo optional message
   * @param encrypted encrypted is visible only for sender and receiver, unencrypted is visible publicly
   *
   * @return a transaction confirmation
   */
  fun transfer(
      credentials: Credentials,
      to: String,
      amount: AssetAmount,
      memo: String? = null,
      encrypted: Boolean = true,
      fee: AssetAmount = AssetAmount.FEE_UNSET
  ): Single<TransactionConfirmation> = when {
    ChainObject.isValid(to) -> Single.just(to.toChainObject())
    Address.isValid(to) -> getAccountIdByAddress(Address.decode(to))
    Account.isValidName(to) -> getAccountByName(to).map { it.id }
    else -> throw IllegalArgumentException("not a valid receiver")
  }.run { flatMap { transfer(credentials.keyPair, credentials.account, it, amount, memo, encrypted, fee) } }


  /**
   * make a transfer to content
   *
   * @param credentials user credentials
   * @param to object id of the receiver content, 2.13.*
   * @param amount amount to send with asset type
   *
   * @return a transaction confirmation
   */
  fun transfer(
      credentials: Credentials,
      to: ChainObject,
      amount: AssetAmount
  ): Single<TransactionConfirmation> = transfer(credentials.keyPair, credentials.account, to, amount)

  /**
   * make a transfer
   *
   * @param credentials user credentials
   * @param to receiver account name, id or public key address
   * @param amount amount to send in DCT
   * @param memo optional message
   * @param encrypted encrypted is visible only for sender and receiver, unencrypted is visible publicly
   *
   * @return a transaction confirmation
   */
  fun transfer(
      credentials: Credentials,
      to: String,
      amount: Double,
      memo: String? = null,
      encrypted: Boolean = true
  ): Single<TransactionConfirmation> = transfer(credentials, to, Globals.DCT.amount(amount), memo, encrypted)

  /**
   * buy a content
   *
   * @param keyPair private keys
   * @param content
   * @param consumer object id of the consumer account, 1.2.*
   *
   * @return a transaction confirmation
   */
  fun buyContent(
      keyPair: ECKeyPair,
      content: Content,
      consumer: ChainObject
  ): Single<TransactionConfirmation>

  /**
   * buy a content by id
   *
   * @param keyPair private keys
   * @param contentId object id of the content, 2.13.*
   * @param consumer object id of the consumer account, 1.2.*
   *
   * @return a transaction confirmation
   */
  fun buyContent(
      keyPair: ECKeyPair,
      contentId: ChainObject,
      consumer: ChainObject
  ): Single<TransactionConfirmation> = getContent(contentId).flatMap { buyContent(keyPair, it, consumer) }

  /**
   * buy a content by uri
   *
   * @param keyPair private keys
   * @param uri Uri of the content
   * @param consumer object id of the consumer account, 1.2.*
   *
   * @return a transaction confirmation
   */
  fun buyContent(
      keyPair: ECKeyPair,
      uri: String,
      consumer: ChainObject
  ): Single<TransactionConfirmation> = getContent(uri).flatMap { buyContent(keyPair, it, consumer) }

  /**
   * buy a content
   *
   * @param credentials user credentials
   * @param content
   *
   * @return a transaction confirmation
   */
  fun buyContent(
      credentials: Credentials,
      content: Content
  ): Single<TransactionConfirmation> = buyContent(credentials.keyPair, content, credentials.account)

  /**
   * Returns fees for operation
   *
   * @param op list of operations
   *
   * @return a list of fee asset amounts
   */
  fun getFees(op: List<BaseOperation>): Single<List<AssetAmount>>

  /**
   * Returns fee for operation
   *
   * @param op operation
   *
   * @return a fee asset amount
   */
  fun getFees(op: BaseOperation): Single<AssetAmount> = getFees(listOf(op)).map { it.first() }

  /**
   * vote for miner
   *
   * @param keyPair private keys
   * @param accountId account id
   * @param voteIds list of miner vote ids
   *
   * @return a transaction confirmation
   */
  fun voteForMiners(
      keyPair: ECKeyPair,
      accountId: ChainObject,
      voteIds: Set<VoteId>
  ): Single<TransactionConfirmation>

  /**
   * vote for miner
   *
   * @param credentials account credentials
   * @param voteIds list of miner vote ids
   *
   * @return a transaction confirmation
   */
  fun voteForMiners(
      credentials: Credentials,
      voteIds: Set<VoteId>
  ): Single<TransactionConfirmation> = voteForMiners(credentials.keyPair, credentials.account, voteIds)

  /**
   * vote for miner
   *
   * @param credentials account credentials
   * @param minerIds list of miner account ids
   *
   * @return a transaction confirmation
   */
  fun voteForMinersByIds(
      credentials: Credentials,
      minerIds: Set<ChainObject>
  ): Single<TransactionConfirmation> = getMiners(minerIds).map { it.map { it.voteId }.toSet() }.flatMap { voteForMiners(credentials, it) }

  /**
   * Returns list of miners by their Ids
   *
   * @param minerIds miner ids
   *
   * @return a list of miners
   */
  fun getMiners(minerIds: Set<ChainObject>): Single<List<Miner>>

  /**
   * Creates new account
   *
   * @param keyPair private keys
   * @param registrar account id of user registering a new account
   * @param name user name of the new account
   * @param owner public keys
   * @param active public keys
   * @param options user options
   *
   * @return a transaction confirmation
   */
  fun createAccount(
      keyPair: ECKeyPair,
      registrar: ChainObject,
      name: String,
      owner: Authority,
      active: Authority,
      options: Options
  ): Single<TransactionConfirmation>
}