package ch.decent.sdk.model

import java.text.NumberFormat

data class AssetWithAmount(
    val asset: Asset,
    val amount: AssetAmount
) : AssetFormatter by asset {

  fun fromRaw() = fromRaw(amount.amount)

  fun format(formatter: NumberFormat) = formatter.format(fromRaw(amount.amount)) + " $symbol"

  fun format() = defaultFormatter.format(fromRaw(amount.amount)) + " $symbol"

  fun format(formatter: NumberFormat.() -> Unit) = defaultFormatter.apply(formatter).format(fromRaw(amount.amount)) + " $symbol"

}