package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.WithdrawPermissionObjectId
import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

class WithdrawalUpdateOperation(
    @SerializedName("permission_to_update") val withdrawalId: WithdrawPermissionObjectId,
    @SerializedName("withdraw_from_account") val accountFrom: AccountObjectId,
    @SerializedName("authorized_account") val accountTo: AccountObjectId,
    @SerializedName("withdrawal_limit") var withdrawalLimit: AssetAmount,
    @SerializedName("withdrawal_period_sec") @UInt32 var withdrawalPeriodSec: Long,
    @SerializedName("periods_until_expiration") @UInt32 var periodsUntilExpiration: Long,
    @SerializedName("period_start_time") var periodStartTime: LocalDateTime,
    fee: Fee = Fee()
) : BaseOperation(OperationType.WITHDRAW_PERMISSION_UPDATE_OPERATION, fee) {

  override fun toString(): String {
    return "WithdrawalUpdateOperation(withdrawalId=$withdrawalId, accountFrom=$accountFrom, accountTo=$accountTo, withdrawalLimit=$withdrawalLimit, withdrawalPeriodSec=$withdrawalPeriodSec, periodsUntilExpiration=$periodsUntilExpiration, periodStartTime=$periodStartTime)"
  }
}
