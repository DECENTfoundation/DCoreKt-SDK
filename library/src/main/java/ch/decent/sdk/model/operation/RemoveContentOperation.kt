package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import com.google.gson.annotations.SerializedName

/**
 * Remove content operation. Sets expiration to head block time, so the content cannot be purchased, but remains in database.
 *
 * @param author content author
 * @param uri content uri
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class RemoveContentOperation @JvmOverloads constructor(
    @SerializedName("author") val author: AccountObjectId,
    @SerializedName("URI") val uri: String,
    fee: Fee = Fee()
) : BaseOperation(OperationType.CONTENT_CANCELLATION_OPERATION, fee) {

  override fun toString(): String {
    return "RemoveContentOperation(author=$author, uri='$uri')"
  }

}
