package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

class GetAssets(
    assets: List<ChainObject>
) : BaseRequest<List<Asset>>(
    ApiGroup.DATABASE,
    "get_assets",
    TypeToken.getParameterized(List::class.java, Asset::class.java).type,
    listOf(assets)
) {

  init {
    check(assets.all { it.objectType == ObjectType.ASSET_OBJECT }, { "not an asset object type" })
  }
}