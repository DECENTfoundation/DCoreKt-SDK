package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Purchase
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal object GetOpenBuyings : BaseRequest<List<Purchase>>(
    ApiGroup.DATABASE,
    "get_open_buyings",
    TypeToken.getParameterized(List::class.java, Purchase::class.java).type
)