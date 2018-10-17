package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
import io.reactivex.Single

class PurchaseApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * search consumer open and history purchases
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
  ): Single<Purchase> = GetBuyingByUri(consumer, uri).toRequest()

  fun getOpenPurchases(): Single<List<Purchase>> = GetOpenBuyings.toRequest()

  fun getOpenPurchases(uri: String): Single<List<Purchase>> = GetOpenBuyingsByUri(uri).toRequest()

  fun getOpenPurchases(accountId: ChainObject): Single<List<Purchase>> = GetOpenBuyingsByConsumer(accountId).toRequest()

  fun getHistoryPurchases(accountId: ChainObject): Single<List<Purchase>> = GetHistoryBuyingsByConsumer(accountId).toRequest()

  // todo wait for add feedback OP so we can test
  fun searchFeedback(
      uri: String,
      count: Int,
      user: String? = null,
      startId: ChainObject = ObjectType.NULL_OBJECT.genericId
  ): Single<List<Purchase>> = SearchFeedback(user, uri, startId, count).toRequest()

  // todo subscriptions: wait for subscribe operation to test
  fun getSubscription(id: ChainObject): Single<Subscription> = GetSubscription(id).toRequest()

  fun listActiveSubscriptionsByConsumer(consumer: ChainObject, count: Int): Single<List<Subscription>> =
      ListActiveSubscriptionsByConsumer(consumer, count).toRequest()

  fun listActiveSubscriptionsByAuthor(author: ChainObject, count: Int): Single<List<Subscription>> =
      ListActiveSubscriptionsByAuthor(author, count).toRequest()

  fun listSubscriptionsByConsumer(consumer: ChainObject, count: Int): Single<List<Subscription>> =
      ListSubscriptionsByConsumer(consumer, count).toRequest()

  fun listSubscriptionsByAuthor(author: ChainObject, count: Int): Single<List<Subscription>> =
      ListSubscriptionsByAuthor(author, count).toRequest()
}
