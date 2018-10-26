package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class ChainParameters(
    @SerializedName("min_miner_count") val minMinerCount: Int,
    @SerializedName("num_special_accounts") val specialAccounts: Int,
    @SerializedName("num_special_assets") val specialAssets: Int

)