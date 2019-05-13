package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt64
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class MinerVotes(
    @SerializedName("account_name") val account: String,
    @SerializedName("votes") @UInt64 val votes: BigInteger
)
