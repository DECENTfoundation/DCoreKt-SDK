package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class FullAccount(
    @SerializedName("account") val account: Account,
    @SerializedName("statistics") val statistics: AccountStatistics,
    @SerializedName("registrar_name") val registrarName: String,
    @SerializedName("votes") val votes: List<Miner>,
    @SerializedName("balances") val balances: List<AccountBalance>,
    @SerializedName("vesting_balances") val vestingBalances: List<Any>, //todo
    @SerializedName("proposals") val proposals: List<Any> //todo
)