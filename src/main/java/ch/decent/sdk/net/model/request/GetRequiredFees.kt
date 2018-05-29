package ch.decent.sdk.net.model.request

import ch.decent.sdk.Globals
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.BaseOperation
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

class GetRequiredFees(
    operations: List<BaseOperation>,
    assetId: ChainObject = Globals.DCT.id
): BaseRequest<List<AssetAmount>>(
    ApiGroup.DATABASE,
    "get_required_fees",
    TypeToken.getParameterized(List::class.java, AssetAmount::class.java).type,
    listOf(operations.map { listOf(it.type.ordinal, it) }, assetId)
)