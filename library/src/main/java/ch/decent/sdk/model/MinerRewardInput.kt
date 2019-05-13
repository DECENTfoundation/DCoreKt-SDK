package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class MinerRewardInput(
    @SerializedName("time_to_maint") val timeToMaintenance: Long,
    @SerializedName("from_accumulated_fees") val fromAccumulatedFees: BigInteger,
    @SerializedName("block_interval") val blockInterval: Short
)
