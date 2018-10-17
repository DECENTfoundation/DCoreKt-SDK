package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Asset
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class ListAssets(
    lowerBound: String,
    limit: Int = 100
) : BaseRequest<List<Asset>>(
    ApiGroup.DATABASE,
    "list_assets",
    TypeToken.getParameterized(List::class.java, Asset::class.java).type,
    listOf(lowerBound, limit)
)