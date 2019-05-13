package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt64
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class MinerVotingInfo(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
    @SerializedName("total_votes") @UInt64 val votes: BigInteger,
    @SerializedName("voted") val voted: Boolean
)
