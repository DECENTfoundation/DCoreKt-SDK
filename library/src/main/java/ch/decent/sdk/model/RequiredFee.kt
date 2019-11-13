package ch.decent.sdk.model

data class RequiredFee(
    val fee: AssetAmount,
    val innerFees: List<RequiredFee> = emptyList()
)
