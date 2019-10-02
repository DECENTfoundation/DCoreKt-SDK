package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

// todo policy..
data class VestingBalance(
    @SerializedName("id") val id: VestingBalanceObjectId,
    @SerializedName("owner") val owner: AccountObjectId,
    @SerializedName("balance") val balance: AssetAmount,
    @SerializedName("policy") val policy: StaticVariant2<LinearVestingPolicy, CddVestingPolicy>
)
