package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

// todo policy..
data class VestingBalance(
    @SerializedName("owner") val owner: ChainObject,
    @SerializedName("balance") val balance: AssetAmount,
    @SerializedName("policy") val policy: Any
)