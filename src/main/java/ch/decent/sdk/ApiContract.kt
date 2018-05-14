package ch.decent.sdk

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.SearchAccountHistoryOrder
import ch.decent.sdk.net.model.SearchPurchasesOrder
import io.reactivex.Single

interface ApiContract {

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
}