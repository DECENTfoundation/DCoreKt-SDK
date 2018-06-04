package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class LookupAssets(
    assets: List<String>
) : BaseRequest<List<Asset>>(
    ApiGroup.DATABASE,
    "lookup_asset_symbols",
    TypeToken.getParameterized(List::class.java, Asset::class.java).type,
    listOf(assets)
)