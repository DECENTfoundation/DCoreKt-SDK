package ch.decent.sdk.model

import ch.decent.sdk.model.types.Int64
import com.google.gson.annotations.SerializedName

data class RealSupply(
    @SerializedName("account_balances") @Int64 val accountBalances: Long,
    @SerializedName("vesting_balances") @Int64 val vestingBalances: Long,
    @SerializedName("escrows") @Int64 val escrows: Long,
    @SerializedName("pools") @Int64 val pools: Long
)
