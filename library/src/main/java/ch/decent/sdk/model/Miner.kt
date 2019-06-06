package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.types.Int64
import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.model.types.UInt64
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class Miner(
    @SerializedName("id") val id: MinerObjectId,
    @SerializedName("miner_account") val minerAccount: AccountObjectId,
    @SerializedName("last_aslot") @UInt64 val lastAslot: BigInteger,
    @SerializedName("signing_key") val signingKey: Address,
    @SerializedName("pay_vb") val payVb: VestingBalanceObjectId?,
    @SerializedName("vote_id") val voteId: String,
    @SerializedName("total_votes") @UInt64 val totalVotes: BigInteger,
    @SerializedName("url") val url: String,
    @SerializedName("total_missed") @Int64 val totalMissed: Long,
    @SerializedName("last_confirmed_block_num") @UInt32 val lastConfirmedBlockNum: Long,
    @SerializedName("vote_ranking") @UInt32 val voteRanking: Long
)
