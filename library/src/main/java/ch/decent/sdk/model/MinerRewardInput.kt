package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class MinerRewardInput(
    @SerializedName("time_to_maint") val timeToMaintenance: Long,
    @SerializedName("from_accumulated_fees") val from_accumulated_fees: BigInteger,
    @SerializedName("block_interval") val block_interval: Short
)