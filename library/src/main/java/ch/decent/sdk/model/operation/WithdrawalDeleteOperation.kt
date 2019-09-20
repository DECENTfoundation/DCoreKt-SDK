package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.WithdrawPermissionObjectId
import com.google.gson.annotations.SerializedName

class WithdrawalDeleteOperation(
    @SerializedName("withdrawal_permission") val withdrawalId: WithdrawPermissionObjectId,
    @SerializedName("withdraw_from_account") val accountFrom: AccountObjectId,
    @SerializedName("authorized_account") val accountTo: AccountObjectId,
    fee: Fee = Fee()
) : BaseOperation(OperationType.WITHDRAW_PERMISSION_DELETE_OPERATION, fee)
