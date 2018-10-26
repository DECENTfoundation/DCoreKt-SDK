package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class BlockHeader(
    @SerializedName("previous") val previous: String,
    @SerializedName("timestamp") val timestamp: LocalDateTime,
    @SerializedName("miner") val miner: ChainObject,
    @SerializedName("transaction_merkle_root") val transactionMerkleRoot: String

)