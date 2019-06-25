package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt16
import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName

data class ChainParameters(
    @SerializedName("min_miner_count") @UInt16 val minMinerCount: Int,
    @SerializedName("num_special_accounts") @UInt32 val specialAccounts: Long,
    @SerializedName("num_special_assets") @UInt32 val specialAssets: Long
)
