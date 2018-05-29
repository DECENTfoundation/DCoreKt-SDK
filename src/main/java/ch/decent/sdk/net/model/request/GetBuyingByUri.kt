package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.net.model.ApiGroup

class GetBuyingByUri(
    consumer: ChainObject,
    uri: String
): BaseRequest<Purchase>(
    ApiGroup.DATABASE,
    "get_buying_by_consumer_URI",
    Purchase::class.java,
    listOf(consumer, uri)
)