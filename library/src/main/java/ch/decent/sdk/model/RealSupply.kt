package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class RealSupply(
    @SerializedName("account_balances") val accountBalances: BigInteger,
    @SerializedName("vesting_balances") val vestingBalances: BigInteger,
    @SerializedName("escrows") val escrows: BigInteger,
    @SerializedName("pools") val pools: BigInteger
)