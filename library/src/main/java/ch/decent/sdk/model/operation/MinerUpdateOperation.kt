package ch.decent.sdk.model.operation

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.MinerObjectId
import com.google.gson.annotations.SerializedName

class MinerUpdateOperation @JvmOverloads constructor(
    @SerializedName("miner") val miner: MinerObjectId,
    @SerializedName("miner_account") val account: AccountObjectId,
    @SerializedName("new_url") var url: String? = null,
    @SerializedName("new_signing_key") var signingKey: Address? = null,
    fee: Fee = Fee()
) : BaseOperation(OperationType.MINER_UPDATE_OPERATION, fee) {

  override fun toString(): String {
    return "MinerUpdateOperation(miner=$miner, account=$account, url=$url, signingKey=$signingKey)"
  }
}
