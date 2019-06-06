package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class ExchangeRate(
    @SerializedName("base") val base: AssetAmount,
    @SerializedName("quote") val quote: AssetAmount
) {
  companion object {
    val EMPTY = ExchangeRate(AssetAmount(0), AssetAmount(0))

    /**
     * quote & base asset ids cannot be the same, for quote any id can be used since it is modified to created asset id upon creation
     * @param base base value in DCT
     * @param quote quote value in UIA
     */
    @JvmStatic fun forCreateOp(base: Long, quote: Long): ExchangeRate = ExchangeRate(AssetAmount(base), AssetAmount(quote, AssetObjectId()))

  }
}
