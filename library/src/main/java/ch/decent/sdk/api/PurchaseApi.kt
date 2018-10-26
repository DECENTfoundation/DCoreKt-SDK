package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
import io.reactivex.Single

class PurchaseApi internal constructor(api: DCoreApi) : BaseApi(api) {

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
  fun searchPurchases(
      consumer: ChainObject,
      term: String = "",
      from: ChainObject = ObjectType.NULL_OBJECT.genericId,
      order: SearchPurchasesOrder = SearchPurchasesOrder.PURCHASED_DESC,
      limit: Int = 100
  ): Single<List<Purchase>> = SearchBuyings(consumer, order, from, term, limit).toRequest()

  /**
   * Get consumer purchase by content uri.
   *
   * @param consumer object id of the account, 1.2.*
   * @param uri a uri of the content
   *
   * @return an account if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getPurchase(
      consumer: ChainObject,
      uri: String
  ): Single<Purchase> = GetBuyingByUri(consumer, uri).toRequest()

  /**
   * Get a list of open purchases.
   *
   * @return a list of open purchases
   */
  fun getOpenPurchases(): Single<List<Purchase>> = GetOpenBuyings.toRequest()

  /**
   * Get a list of open purchases for content URI.
   *
   * @param uri content uri
   *
   * @return a list of open purchases
   */
  fun getOpenPurchases(uri: String): Single<List<Purchase>> = GetOpenBuyingsByUri(uri).toRequest()

  /**
   * Get a list of open purchases for consumer id.
   *
   * @param accountId consumer account object id, 1.2.*
   *
   * @return a list of open purchases
   */
  fun getOpenPurchases(accountId: ChainObject): Single<List<Purchase>> = GetOpenBuyingsByConsumer(accountId).toRequest()

  /**
   * Get a list of history purchases for consumer id.
   *
   * @param accountId consumer account object id, 1.2.*
   *
   * @return a list of history purchases
   */
  fun getHistoryPurchases(accountId: ChainObject): Single<List<Purchase>> = GetHistoryBuyingsByConsumer(accountId).toRequest()

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
  fun searchFeedback(
      uri: String,
      user: String? = null,
      count: Int = 100,
      startId: ChainObject = ObjectType.NULL_OBJECT.genericId
  ): Single<List<Purchase>> = SearchFeedback(user, uri, startId, count).toRequest()

  /**
   * Get a subscription object by ID.
   *
   * @param id subscription object id, 2.15.*
   *
   * @return the subscription object corresponding to the provided ID, [ObjectNotFoundException] otherwise
   */
  // todo subscriptions: wait for subscribe operation to test
  fun getSubscription(id: ChainObject): Single<Subscription> = GetSubscription(id).toRequest()

  /**
   * Get a list of active (not expired) subscriptions by account (consumer).
   *
   * @param consumer consumer account object id, 1.2.*
   * @param count maximum number of subscription objects to fetch (must not exceed 100)
   *
   * @return a list of active subscription objects
   */
  fun listActiveSubscriptionsByConsumer(consumer: ChainObject, count: Int): Single<List<Subscription>> =
      ListActiveSubscriptionsByConsumer(consumer, count).toRequest()

  /**
   * Get a list of active (not expired) subscriptions by account (author).
   *
   * @param author author account object id, 1.2.*
   * @param count maximum number of subscription objects to fetch (must not exceed 100)
   *
   * @return a list of active subscription objects
   */
  fun listActiveSubscriptionsByAuthor(author: ChainObject, count: Int): Single<List<Subscription>> =
      ListActiveSubscriptionsByAuthor(author, count).toRequest()

  /**
   * Get a list of subscriptions by account (consumer).
   *
   * @param consumer consumer account object id, 1.2.*
   * @param count maximum number of subscription objects to fetch (must not exceed 100)
   *
   * @return a list of subscription objects
   */
  fun listSubscriptionsByConsumer(consumer: ChainObject, count: Int): Single<List<Subscription>> =
      ListSubscriptionsByConsumer(consumer, count).toRequest()

  /**
   * Get a list of subscriptions by account (author).
   *
   * @param author author account object id, 1.2.*
   * @param count maximum number of subscription objects to fetch (must not exceed 100)
   *
   * @return a list of subscription objects
   */
  fun listSubscriptionsByAuthor(author: ChainObject, count: Int): Single<List<Subscription>> =
      ListSubscriptionsByAuthor(author, count).toRequest()
}
