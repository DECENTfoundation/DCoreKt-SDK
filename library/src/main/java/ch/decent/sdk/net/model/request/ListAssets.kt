package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Asset
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken

internal class ListAssets(
    lowerBound: String,
    limit: Int = REQ_LIMIT_MAX
) : BaseRequest<List<Asset>>(
    ApiGroup.DATABASE,
    "list_assets",
    TypeToken.getParameterized(List::class.java, Asset::class.java).type,
    listOf(lowerBound, limit)
)
