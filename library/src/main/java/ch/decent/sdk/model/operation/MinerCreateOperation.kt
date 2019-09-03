package ch.decent.sdk.model.operation

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import com.google.gson.annotations.SerializedName

class MinerCreateOperation @JvmOverloads constructor(
    @SerializedName("miner_account") val account: AccountObjectId,
    @SerializedName("url") val url: String,
    @SerializedName("block_signing_key") val signingKey: Address,
    fee: Fee = Fee()
) : BaseOperation(OperationType.MINER_CREATE_OPERATION, fee) {

  override fun toString(): String {
    return "MinerCreateOperation(account=$account, url='$url', signingKey=$signingKey)"
  }
}
