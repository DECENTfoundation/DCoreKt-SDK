package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class MinerVotingInfo(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
    @SerializedName("total_votes") val votes: BigInteger,
    @SerializedName("voted") val voted: Boolean
)