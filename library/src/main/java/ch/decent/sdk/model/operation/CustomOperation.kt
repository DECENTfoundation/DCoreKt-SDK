package ch.decent.sdk.model.operation

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.utils.unhex
import com.google.gson.annotations.SerializedName

/**
 * Custom operation
 *
 * @param type custom operation subtype
 * @param payer account which pays for the fee
 * @param requiredAuths accounts required to authorize this operation with signatures
 * @param data data payload encoded in hex string
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
open class CustomOperation constructor(
    type: CustomOperationType,
    @SerializedName("payer") val payer: ChainObject,
    @SerializedName("required_auths") val requiredAuths: List<ChainObject>,
    @SerializedName("data") val data: String,
    fee: Fee = Fee()
) : BaseOperation(OperationType.CUSTOM_OPERATION, fee) {

  @SerializedName("id") val id: Int = type.ordinal

  override fun toString(): String {
    return "CustomOperation(payer=$payer, requiredAuths=$requiredAuths, data=${String(data.unhex())}, id=${CustomOperationType.values()[id]})"
  }

}
