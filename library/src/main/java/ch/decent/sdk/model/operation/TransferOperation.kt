package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.ObjectId
import ch.decent.sdk.model.ObjectType
import com.google.gson.annotations.SerializedName

/**
 * Transfer operation constructor
 *
 * @param from account object id of the sender
 * @param to account object id or content object id of the receiver
 * @param amount an [AssetAmount] to transfer
 * @param memo optional string note
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class TransferOperation @JvmOverloads constructor(
    @SerializedName("from") val from: AccountObjectId,
    @SerializedName("to") val to: ObjectId,
    @SerializedName("amount") val amount: AssetAmount,
    @SerializedName("memo") val memo: Memo? = null,
    fee: Fee = Fee()
) : BaseOperation(OperationType.TRANSFER2_OPERATION, fee) {

  init {
    require(to.objectType == ObjectType.ACCOUNT_OBJECT || to.objectType == ObjectType.CONTENT_OBJECT) { "not an account or content object id" }
  }

  override fun toString(): String {
    return "TransferOperation(from=$from, to=$to, amount=$amount, memo=$memo)"
  }

}
