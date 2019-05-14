package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.ObjectType
import com.google.gson.annotations.SerializedName

/**
 * Reserve funds operation constructor. Return issued funds to the issuer of the asset.
 *
 * @param payer account id providing the funds
 * @param amount asset amount to remove from current supply
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class AssetReserveOperation(
    @SerializedName("payer") val payer: ChainObject,
    @SerializedName("amount_to_reserve") val amount: AssetAmount,
    fee: Fee = Fee()
) : BaseOperation(OperationType.ASSET_RESERVE_OPERATION, fee) {

  init {
    require(payer.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
  }

  override fun toString(): String {
    return "AssetReserveOperation(payer=$payer, amount=$amount)"
  }

}
