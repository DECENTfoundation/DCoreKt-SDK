package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.OpWrapper
import ch.decent.sdk.model.RequiredFee
import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

class ProposalCreateOperation(
    @SerializedName("fee_paying_account") val payer: AccountObjectId,
    @SerializedName("proposed_ops") val operations: List<OpWrapper>,
    @SerializedName("expiration_time") val expirationTime: LocalDateTime,
    @SerializedName("review_period_seconds") @UInt32 val reviewPeriodSeconds: Long? = null,
    fee: Fee = Fee()
) : BaseOperation(OperationType.PROPOSAL_CREATE_OPERATION, fee) {

  override fun setFee(fee: RequiredFee) {
    super.setFee(fee)
    operations.forEachIndexed { idx, op -> op.op.setFee(fee.innerFees[idx]) }
  }
}
