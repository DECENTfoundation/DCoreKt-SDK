package ch.decent.sdk

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.SearchAccountHistoryOrder
import ch.decent.sdk.net.model.SearchPurchasesOrder
import io.reactivex.Single

interface DCoreApi {

  /**
   * get account balance
   *
   * @param accountId object id of the account, 1.2.*
   * @return list of amounts for different assets
   */
  fun getBalance(accountId: ChainObject): Single<List<AssetAmount>>

  /**
   * get Account object by name
   *
   * @param name the name of the account
   * @return an account if found, ObjectNotFoundException otherwise
   */
  fun getAccountByName(name: String): Single<Account>

  /**
   * get Account object by id
   *
   * @param accountId object id of the account, 1.2.*
   * @return an account if found, ObjectNotFoundException otherwise
   */
  fun getAccountById(accountId: ChainObject): Single<Account>

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
   * @return an account if found, ObjectNotFoundException otherwise
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
   * @return a content if found, ObjectNotFoundException otherwise
   */
  fun getContent(contentId: ChainObject): Single<Content>

  /**
   * get content by uri
   *
   * @param uri Uri of the content
   *
   * @return a content if found, ObjectNotFoundException otherwise
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
   * If the transaction has not expired, this method will return the transaction for the given ID or it will return null if it is not known.
   * Just because it is not known does not mean it wasn't included in the blockchain.
   *
   * @param trxId transaction id
   *
   * @return a transaction if found, ObjectNotFoundException otherwise
   */
  fun getRecentTransaction(trxId: String): Single<ProcessedTransaction>

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
      encrypted: Boolean = true
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
   * @param to receiver account name
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
  ): Single<TransactionConfirmation> = getAccountByName(to).flatMap { transfer(credentials.keyPair, credentials.account, it.id, Globals.DCT.amount(amount), memo, encrypted) }

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
   * @param voteIds list of miners ids
   *
   * @return a transaction confirmation
   */
  fun voteForMiners(
      keyPair: ECKeyPair,
      accountId: ChainObject,
      voteIds: Set<String>
  ): Single<TransactionConfirmation>

  /**
   * vote for miner
   *
   * @param credentials account credentials
   * @param voteIds list of vote ids
   *
   * @return a transaction confirmation
   */
  fun voteForMiners(
      credentials: Credentials,
      voteIds: Set<String>): Single<TransactionConfirmation> = voteForMiners(credentials.keyPair, credentials.account, voteIds)

  /**
   * vote for miner
   *
   * @param keyPair private keys
   * @param accountId account id
   * @param minerIds list of miner ids
   *
   * @return a transaction confirmation
   */
  fun voteForMinersByIds(
      keyPair: ECKeyPair,
      accountId: ChainObject,
      minerIds: Set<ChainObject>
  ): Single<TransactionConfirmation> = getMiners(minerIds).map { it.map { it.voteId }.toSet() }.flatMap { voteForMiners(keyPair, accountId, it) }

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
   * @param userId user ID
   * @param name user name
   *
   * @return a transaction confirmation
   */
  fun createAccount(
      keyPair: ECKeyPair,
      userId: ChainObject,
      name: String,
      owner: Authority,
      active: Authority,
      options: Options): Single<TransactionConfirmation>
}