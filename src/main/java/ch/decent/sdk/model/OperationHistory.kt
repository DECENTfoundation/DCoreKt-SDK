package ch.decent.sdk.model

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class OperationHistory(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("op") val operation: BaseOperation,
    @SerializedName("result") val result: JsonArray,
    @SerializedName("block_num") val blockNum: Long,
    @SerializedName("trx_in_block") val trxInBlock: Long,
    @SerializedName("op_in_trx") val operationNumInTrx: Long,
    @SerializedName("virtual_op") val virtualOperation: Long
)