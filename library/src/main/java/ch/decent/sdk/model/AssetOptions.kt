package ch.decent.sdk.model

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.types.Int64
import com.google.gson.annotations.SerializedName

data class AssetOptions(
    @SerializedName("core_exchange_rate") val exchangeRate: ExchangeRate = ExchangeRate.forCreateOp(1, 1),
    @SerializedName("max_supply") @Int64 val maxSupply: Long = DCoreConstants.MAX_SHARE_SUPPLY,
    @SerializedName("is_exchangeable") val exchangeable: Boolean = true,
    @SerializedName("extensions") val extensions: FixedMaxSupply? = FixedMaxSupply(false)
)
