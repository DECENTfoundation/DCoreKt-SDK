package ch.decent.sdk.model

import ch.decent.sdk.model.types.Int64
import ch.decent.sdk.model.types.Int8
import com.google.gson.annotations.SerializedName

data class MinerRewardInput(
    @SerializedName("time_to_maint") @Int64 val timeToMaintenance: Long,
    @SerializedName("from_accumulated_fees") @Int64 val fromAccumulatedFees: Long,
    @SerializedName("block_interval") @Int8 val blockInterval: Byte
)
