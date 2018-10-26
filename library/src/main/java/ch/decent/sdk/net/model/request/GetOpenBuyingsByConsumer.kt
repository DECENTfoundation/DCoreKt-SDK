package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetOpenBuyingsByConsumer(
    consumer: ChainObject
) : BaseRequest<List<Purchase>>(
    ApiGroup.DATABASE,
    "get_open_buyings_by_consumer",
    TypeToken.getParameterized(List::class.java, Purchase::class.java).type,
    listOf(consumer)
) {
  init {
    require(consumer.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid asset object id" }
  }
}