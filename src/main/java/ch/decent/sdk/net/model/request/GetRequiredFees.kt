package ch.decent.sdk.net.model.request

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.BaseOperation
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetRequiredFees(
    operations: List<BaseOperation>,
    assetId: ChainObject = DCoreConstants.DCT.id
): BaseRequest<List<AssetAmount>>(
    ApiGroup.DATABASE,
    "get_required_fees",
    TypeToken.getParameterized(List::class.java, AssetAmount::class.java).type,
    listOf(operations.map { listOf(it.type.ordinal, it) }, assetId)
)