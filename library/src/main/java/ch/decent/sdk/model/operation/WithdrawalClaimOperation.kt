package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.WithdrawPermissionObjectId
import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

class WithdrawalClaimOperation(
    @SerializedName("withdraw_permission") val withdrawalId: WithdrawPermissionObjectId,
    @SerializedName("withdraw_from_account") val accountFrom: AccountObjectId,
    @SerializedName("withdraw_to_account") val accountTo: AccountObjectId,
    @SerializedName("amount_to_withdraw") val amount: AssetAmount,
    @SerializedName("memo") val memo: Memo? = null,
    fee: Fee = Fee()
) : BaseOperation(OperationType.WITHDRAW_PERMISSION_CLAIM_OPERATION, fee)
