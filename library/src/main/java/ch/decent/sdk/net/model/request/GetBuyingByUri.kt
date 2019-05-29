package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.net.model.ApiGroup

internal class GetBuyingByUri(
    consumer: AccountObjectId,
    uri: String
) : BaseRequest<Purchase>(
    ApiGroup.DATABASE,
    "get_buying_by_consumer_URI",
    Purchase::class.java,
    listOf(consumer, uri)
)
