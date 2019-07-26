@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.SubscriptionObjectId
import kotlin.Int
import kotlin.Suppress

class SubscriptionApi internal constructor(
  private val api: ch.decent.sdk.api.SubscriptionApi
) {
  fun get(id: SubscriptionObjectId) = api.get(id).blockingGet()
  fun getAllActiveByConsumer(consumer: AccountObjectId, count: Int) =
      api.getAllActiveByConsumer(consumer, count).blockingGet()
  fun getAllActiveByAuthor(author: AccountObjectId, count: Int) = api.getAllActiveByAuthor(author,
      count).blockingGet()
  fun getAllByConsumer(consumer: AccountObjectId, count: Int) = api.getAllByConsumer(consumer,
      count).blockingGet()
  fun getAllByAuthor(author: AccountObjectId, count: Int) = api.getAllByAuthor(author,
      count).blockingGet()}
