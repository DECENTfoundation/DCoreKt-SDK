package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class ExchangeRate(
    @SerializedName("base") val base: AssetAmount,
    @SerializedName("quote") val quote: AssetAmount
) {
  companion object {
    val EMPTY = ExchangeRate(AssetAmount(0), AssetAmount(0))
  }
}
