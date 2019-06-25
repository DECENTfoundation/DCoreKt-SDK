package ch.decent.sdk.model.operation

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.ObjectType
import com.google.gson.annotations.SerializedName

/**
 * Claim fees operation constructor. Claim funds from asset pool, only the asset issuer can clam.
 *
 * @param issuer account id issuing the asset
 * @param uia the uia asset value to claim
 * @param dct the dct asset value to claim
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class AssetClaimFeesOperation @JvmOverloads constructor(
    @SerializedName("issuer") val issuer: ChainObject,
    @SerializedName("uia_asset") val uia: AssetAmount,
    @SerializedName("dct_asset") val dct: AssetAmount,
    fee: Fee = Fee()
) : BaseOperation(OperationType.ASSET_CLAIM_FEES_OPERATION, fee) {

  init {
    require(issuer.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
    require(dct.assetId == DCoreConstants.DCT_ASSET_ID) { "not an DCT asset id" }
  }

  override fun toString(): String {
    return "AssetClaimFeesOperation(issuer=$issuer, uia=$uia, dct=$dct)"
  }

}
