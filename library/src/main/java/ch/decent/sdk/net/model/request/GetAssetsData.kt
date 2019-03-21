package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AssetData
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import com.google.gson.reflect.TypeToken

internal class GetAssetsData(
    assetDataIds: List<ChainObject>
) : GetObjects<List<AssetData>>(
    assetDataIds,
    TypeToken.getParameterized(List::class.java, AssetData::class.java).type
) {
  init {
    require(assetDataIds.all { it.objectType == ObjectType.ASSET_DYNAMIC_DATA }) { "not a valid asset dynamic data object id" }
  }
}