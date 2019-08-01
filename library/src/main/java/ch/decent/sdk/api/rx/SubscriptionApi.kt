@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api.rx

import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Subscription
import ch.decent.sdk.net.model.request.GetSubscription
import ch.decent.sdk.net.model.request.ListActiveSubscriptionsByAuthor
import ch.decent.sdk.net.model.request.ListActiveSubscriptionsByConsumer
import ch.decent.sdk.net.model.request.ListSubscriptionsByAuthor
import ch.decent.sdk.net.model.request.ListSubscriptionsByConsumer
import io.reactivex.Single

class SubscriptionApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get a subscription object by ID.
   *
   * @param id subscription object id, 2.15.*
   *
   * @return the subscription object corresponding to the provided ID, [ObjectNotFoundException] otherwise
   */
  // todo subscriptions: wait for subscribe operation to test
  fun get(id: ChainObject): Single<Subscription> = GetSubscription(id).toRequest()

  /**
   * Get a list of active (not expired) subscriptions by account (consumer).
   *
   * @param consumer consumer account object id, 1.2.*
   * @param count maximum number of subscription objects to fetch (must not exceed 100)
   *
   * @return a list of active subscription objects
   */
  fun getAllActiveByConsumer(consumer: ChainObject, count: Int): Single<List<Subscription>> =
      ListActiveSubscriptionsByConsumer(consumer, count).toRequest()

  /**
   * Get a list of active (not expired) subscriptions by account (author).
   *
   * @param author author account object id, 1.2.*
   * @param count maximum number of subscription objects to fetch (must not exceed 100)
   *
   * @return a list of active subscription objects
   */
  fun getAllActiveByAuthor(author: ChainObject, count: Int): Single<List<Subscription>> =
      ListActiveSubscriptionsByAuthor(author, count).toRequest()

  /**
   * Get a list of subscriptions by account (consumer).
   *
   * @param consumer consumer account object id, 1.2.*
   * @param count maximum number of subscription objects to fetch (must not exceed 100)
   *
   * @return a list of subscription objects
   */
  fun getAllByConsumer(consumer: ChainObject, count: Int): Single<List<Subscription>> =
      ListSubscriptionsByConsumer(consumer, count).toRequest()

  /**
   * Get a list of subscriptions by account (author).
   *
   * @param author author account object id, 1.2.*
   * @param count maximum number of subscription objects to fetch (must not exceed 100)
   *
   * @return a list of subscription objects
   */
  fun getAllByAuthor(author: ChainObject, count: Int): Single<List<Subscription>> =
      ListSubscriptionsByAuthor(author, count).toRequest()
}
