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
     * @param dct base value in DCT
     * @param uia quote value in UIA
     */
    @JvmStatic fun forCreateOp(dct: Long, uia: Long): ExchangeRate = ExchangeRate(AssetAmount(dct), AssetAmount(uia, "1.3.1".toChainObject()))

  }
}

data class ExchangeRateValues(
    val base: Long,
    val quote: Long
)
