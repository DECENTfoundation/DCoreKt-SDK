package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName

data class TransactionConfirmation(
    @SerializedName("id") val id: String,
    @SerializedName("block_num") @UInt32 val blockNum: Long,
    @SerializedName("trx_num") @UInt32 val trxNum: Long,
    @SerializedName("trx") val transaction: ProcessedTransaction
)
