package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class PriceFeed(
    @SerializedName("core_exchange_rate") val coreExchangeRate: ExchangeRate
)
