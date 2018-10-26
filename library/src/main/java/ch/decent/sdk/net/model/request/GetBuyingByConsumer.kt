package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.net.model.ApiGroup

internal class GetBuyingByConsumer(
    consumer: ChainObject
) : BaseRequest<Purchase>(
    ApiGroup.DATABASE,
    "get_buying_by_consumer",
    Purchase::class.java,
    listOf(consumer)
) {
  init {
    require(consumer.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid asset object id" }
  }
}