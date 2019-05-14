package ch.decent.sdk.model.operation

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.types.UInt8
import com.google.gson.annotations.SerializedName

/**
 * Update advanced options for the asset.
 *
 * @param issuer account id issuing the asset
 * @param assetToUpdate asset to update
 * @param precision new precision
 * @param fixedMaxSupply whether it should be allowed to change max supply, cannot be reverted once set to true
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class AssetUpdateAdvancedOperation(
    @SerializedName("issuer") val issuer: ChainObject,
    @SerializedName("asset_to_update") val assetToUpdate: ChainObject,
    @SerializedName("new_precision") @UInt8 val precision: Byte,
    @SerializedName("set_fixed_max_supply") val fixedMaxSupply: Boolean,
    fee: Fee = Fee()
) : BaseOperation(OperationType.UPDATE_USER_ISSUED_ASSET_ADVANCED, fee) {

  init {
    require(issuer.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(assetToUpdate.objectType == ObjectType.ASSET_OBJECT) { "not a valid asset object id" }
    require(precision in DCoreConstants.PRECISION_ALLOWED) { "precision must be in range of 0-12" }
  }

  override fun toString(): String {
    return "AssetUpdateAdvancedOperation(issuer=$issuer, assetToUpdate=$assetToUpdate, precision=$precision, fixedMaxSupply=$fixedMaxSupply)"
  }

}
