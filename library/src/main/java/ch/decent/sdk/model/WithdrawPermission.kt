package ch.decent.sdk.model

import ch.decent.sdk.model.types.Int64
import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class WithdrawPermission(
    @SerializedName("id") val id: WithdrawPermissionObjectId,
    @SerializedName("withdraw_from_account") val accountFrom: AccountObjectId,
    @SerializedName("authorized_account") val accountTo: AccountObjectId,
    @SerializedName("withdrawal_limit") val withdrawalLimit: AssetAmount,
    @SerializedName("withdrawal_period_sec") @UInt32 val withdrawalPeriodSec: Long,
    @SerializedName("period_start_time") val periodStartTime: LocalDateTime,
    @SerializedName("expiration") val expiration: LocalDateTime,
    @SerializedName("claimed_this_period") @Int64 val claimedThisPeriod: Long
)
