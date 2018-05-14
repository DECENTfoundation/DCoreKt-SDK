package ch.decent.sdk.model

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class OperationHistory(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("op") val op: BaseOperation,
    @SerializedName("result") val res: JsonArray,
    @SerializedName("block_num") val blockNum: Long,
    @SerializedName("trx_num") val trxNum: Long,
    @SerializedName("op_in_trx") val opNum: Long,
    @SerializedName("virtual_op") val virtualOp: Long
) {

}