package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class SignedBlock(
    @SerializedName("previous") val previous: String,
    @SerializedName("timestamp") val timestamp: LocalDateTime,
    @SerializedName("miner") val miner: MinerObjectId,
    @SerializedName("transaction_merkle_root") val transactionMerkleRoot: String,
    @SerializedName("miner_signature") val minerSignature: String,
    @SerializedName("transactions") val transactions: List<ProcessedTransaction>,
    @SerializedName("extensions") val extensions: List<Any>
)
