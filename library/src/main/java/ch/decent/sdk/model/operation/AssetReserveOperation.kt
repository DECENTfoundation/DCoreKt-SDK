package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.Fee
import com.google.gson.annotations.SerializedName

/**
 * Reserve funds operation constructor. Return issued funds to the issuer of the asset.
 *
 * @param payer account id providing the funds
 * @param amount asset amount to remove from current supply
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class AssetReserveOperation @JvmOverloads constructor(
    @SerializedName("payer") val payer: AccountObjectId,
    @SerializedName("amount_to_reserve") val amount: AssetAmount,
    fee: Fee = Fee()
) : BaseOperation(OperationType.ASSET_RESERVE_OPERATION, fee) {

  override fun toString(): String {
    return "AssetReserveOperation(payer=$payer, amount=$amount)"
  }

}
