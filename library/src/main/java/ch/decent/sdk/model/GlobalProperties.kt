package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class GlobalProperties(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("parameters") val parameters: GlobalParameters,
    @SerializedName("next_available_vote_id") val nextAvailableVoteId: Long,
    @SerializedName("active_miners") val activeMiners: List<ChainObject>
)