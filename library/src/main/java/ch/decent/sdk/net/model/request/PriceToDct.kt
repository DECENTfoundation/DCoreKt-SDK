package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.net.model.ApiGroup

internal class PriceToDct(
    amount: AssetAmount
): BaseRequest<AssetAmount>(
    ApiGroup.DATABASE,
    "price_to_dct",
    AssetAmount::class.java,
    listOf(amount)
)
