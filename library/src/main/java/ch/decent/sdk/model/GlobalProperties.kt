package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName

data class GlobalProperties(
    @SerializedName("id") val id: GlobalPropertyObjectId,
    @SerializedName("parameters") val parameters: GlobalParameters,
    @SerializedName("next_available_vote_id") @UInt32 val nextAvailableVoteId: Long,
    @SerializedName("active_miners") val activeMiners: List<MinerObjectId>
)
