package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Purchase
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetOpenBuyingsByUri(
    uri: String
) : BaseRequest<List<Purchase>>(
    ApiGroup.DATABASE,
    "get_open_buyings_by_URI",
    TypeToken.getParameterized(List::class.java, Purchase::class.java).type,
    listOf(uri)
)
