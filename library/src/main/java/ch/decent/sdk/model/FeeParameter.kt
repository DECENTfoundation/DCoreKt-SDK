package ch.decent.sdk.model

data class FeeParameter(
    val fee: AssetAmount,
    val pricePerKb: AssetAmount? = null
)