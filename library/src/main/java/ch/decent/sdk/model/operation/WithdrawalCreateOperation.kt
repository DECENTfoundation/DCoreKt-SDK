package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

class WithdrawalCreateOperation(
    @SerializedName("withdraw_from_account") val accountFrom: AccountObjectId,
    @SerializedName("authorized_account") val accountTo: AccountObjectId,
    @SerializedName("withdrawal_limit") val withdrawalLimit: AssetAmount,
    @SerializedName("withdrawal_period_sec") @UInt32 val withdrawalPeriodSec: Long,
    @SerializedName("periods_until_expiration") @UInt32 val periodsUntilExpiration: Long,
    @SerializedName("period_start_time") val periodStartTime: LocalDateTime,
    fee: Fee = Fee()
) : BaseOperation(OperationType.WITHDRAW_PERMISSION_CREATE_OPERATION, fee)
