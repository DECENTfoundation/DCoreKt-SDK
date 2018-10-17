package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class MinerVotes(
    @SerializedName("account_name") val account: String,
    @SerializedName("votes") val votes: BigInteger
)