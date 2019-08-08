package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.AssetObjectId
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetAssets(
    assets: List<AssetObjectId>
) : BaseRequest<List<Asset>>(
    ApiGroup.DATABASE,
    "get_assets",
    TypeToken.getParameterized(List::class.java, Asset::class.java).type,
    listOf(assets)
)
