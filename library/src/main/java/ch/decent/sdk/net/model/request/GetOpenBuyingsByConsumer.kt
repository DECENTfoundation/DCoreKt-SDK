package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetOpenBuyingsByConsumer(
    consumer: AccountObjectId
) : BaseRequest<List<Purchase>>(
    ApiGroup.DATABASE,
    "get_open_buyings_by_consumer",
    TypeToken.getParameterized(List::class.java, Purchase::class.java).type,
    listOf(consumer)
)
