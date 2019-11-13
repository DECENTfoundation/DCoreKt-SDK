package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.CddVestingPolicy
import ch.decent.sdk.model.CddVestingPolicyCreate
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.LinearVestingPolicy
import ch.decent.sdk.model.LinearVestingPolicyCreate
import ch.decent.sdk.model.StaticVariant2
import com.google.gson.annotations.SerializedName

class VestingBalanceCreateOperation(
    @SerializedName("creator") val creator: AccountObjectId,
    @SerializedName("owner") val owner: AccountObjectId,
    @SerializedName("amount") val amount: AssetAmount,
    @SerializedName("policy") val policy: StaticVariant2<LinearVestingPolicyCreate, CddVestingPolicyCreate>,
    fee: Fee = Fee()
) : BaseOperation(OperationType.VESTING_BALANCE_CREATE_OPERATION, fee) {

  override fun toString(): String {
    return "VestingBalanceCreateOperation(creator=$creator, owner=$owner, amount=$amount, policy=$policy)"
  }
}
