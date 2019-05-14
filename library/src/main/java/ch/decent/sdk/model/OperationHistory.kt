package ch.decent.sdk.model

import ch.decent.sdk.model.operation.BaseOperation
import ch.decent.sdk.model.types.UInt16
import ch.decent.sdk.model.types.UInt32
import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class OperationHistory(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("op") val operation: BaseOperation,
    @SerializedName("result") val result: JsonArray,
    @SerializedName("block_num") @UInt32 val blockNum: Long,
    @SerializedName("trx_in_block") @UInt16 val trxInBlock: Int,
    @SerializedName("op_in_trx") @UInt16 val operationNumInTrx: Int,
    @SerializedName("virtual_op") @UInt16 val virtualOperation: Int
)
