package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AssetData
import ch.decent.sdk.model.AssetDataObjectId
import com.google.gson.reflect.TypeToken

internal class GetAssetsData(
    assetDataIds: List<AssetDataObjectId>
) : GetObjects<List<AssetData>>(
    assetDataIds,
    TypeToken.getParameterized(List::class.java, AssetData::class.java).type
)
