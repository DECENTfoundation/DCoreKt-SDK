package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.model.SearchPurchasesOrder
import ch.decent.sdk.net.model.request.*
import io.reactivex.Single

class PurchaseApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get a list of history purchases for consumer id.
   *
   * @param accountId consumer account object id, 1.2.*
   *
   * @return a list of history purchases
   */
  fun getAllHistory(accountId: ChainObject): Single<List<Purchase>> = GetHistoryBuyingsByConsumer(accountId).toRequest()

  /**
   * Get a list of open purchases.
   *
   * @return a list of open purchases
   */
  fun getAllOpen(): Single<List<Purchase>> = GetOpenBuyings.toRequest()

  /**
   * Get a list of open purchases for content URI.
   *
   * @param uri content uri
   *
   * @return a list of open purchases
   */
  fun getAllOpenByUri(uri: String): Single<List<Purchase>> = GetOpenBuyingsByUri(uri).toRequest()

  /**
   * Get a list of open purchases for consumer id.
   *
   * @param accountId consumer account object id, 1.2.*
   *
   * @return a list of open purchases
   */
  fun getAllOpenByAccount(accountId: ChainObject): Single<List<Purchase>> = GetOpenBuyingsByConsumer(accountId).toRequest()

  /**
   * Get consumer purchase by content uri.
   *
   * @param consumer object id of the account, 1.2.*
   * @param uri a uri of the content
   *
   * @return an account if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun get(
      consumer: ChainObject,
      uri: String
  ): Single<Purchase> = GetBuyingByUri(consumer, uri).toRequest()

  /**
   * Search consumer open and history purchases.
   *
   * @param consumer object id of the account, 1.2.*
   * @param term search term
   * @param from object id of the history object to start from, use [ObjectType.NULL_OBJECT.genericId] to ignore
   * @param order [SearchPurchasesOrder]
   * @param limit number of entries, max 100
   *
   * @return list of purchases
   */
  @JvmOverloads
  fun findAll(
      consumer: ChainObject,
      term: String = "",
      from: ChainObject = ObjectType.NULL_OBJECT.genericId,
      order: SearchPurchasesOrder = SearchPurchasesOrder.PURCHASED_DESC,
      limit: Int = 100
  ): Single<List<Purchase>> = SearchBuyings(consumer, order, from, term, limit).toRequest()

  /**
   * Search for feedback.
   *
   * @param uri content URI
   * @param user feedback author account name
   * @param count count	maximum number of feedback objects to fetch
   * @param startId the id of feedback object to start searching from
   *
   * @return a list of purchase objects
   */
  // todo wait for add feedback OP so we can test
  fun findAllForFeedback(
      uri: String,
      user: String? = null,
      count: Int = 100,
      startId: ChainObject = ObjectType.NULL_OBJECT.genericId
  ): Single<List<Purchase>> = SearchFeedback(user, uri, startId, count).toRequest()
}
