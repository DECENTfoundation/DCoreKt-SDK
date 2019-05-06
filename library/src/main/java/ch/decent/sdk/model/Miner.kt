package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class Miner(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("miner_account") val minerAccount: ChainObject,
    @SerializedName("last_aslot") val lastAslot: Int,
    @SerializedName("signing_key") val signingKey: Address,
    @SerializedName("pay_vb") val payVb: ChainObject,
    @SerializedName("vote_id") val voteId: String,
    @SerializedName("total_votes") val totalVotes: BigInteger,
    @SerializedName("url") val url: String,
    @SerializedName("total_missed") val totalMissed: Int,
    @SerializedName("last_confirmed_block_num") val lastConfirmedBlockNum: Long
)
