package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.VestingBalanceObjectId
import ch.decent.sdk.model.WithdrawPermissionObjectId
import com.google.gson.annotations.SerializedName

class VestingBalanceWithdrawOperation(
    @SerializedName("vesting_balance") val id: VestingBalanceObjectId,
    @SerializedName("owner") val owner: AccountObjectId,
    @SerializedName("amount") val amount: AssetAmount,
    fee: Fee = Fee()
) : BaseOperation(OperationType.VESTING_BALANCE_WITHDRAW_OPERATION, fee) {

  override fun toString(): String {
    return "VestingBalanceWithdrawOperation(id=$id, owner=$owner, amount=$amount)"
  }
}
