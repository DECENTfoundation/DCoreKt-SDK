package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class TransactionConfirmation(
    @SerializedName("id") val id: String,
    @SerializedName("block_num") val blockNum: Long,
    @SerializedName("trx_num") val trxNum: Long,
    @SerializedName("trx") val transaction: ProcessedTransaction
)
