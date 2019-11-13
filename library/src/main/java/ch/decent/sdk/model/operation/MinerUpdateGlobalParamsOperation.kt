package ch.decent.sdk.model.operation

import ch.decent.sdk.model.ChainParameters
import ch.decent.sdk.model.Fee
import com.google.gson.annotations.SerializedName

class MinerUpdateGlobalParamsOperation(
    @SerializedName("new_parameters") val params: ChainParameters,
    fee: Fee = Fee()
) : BaseOperation(OperationType.MINER_UPDATE_GLOBAL_PARAMETERS_OPERATION, fee)
