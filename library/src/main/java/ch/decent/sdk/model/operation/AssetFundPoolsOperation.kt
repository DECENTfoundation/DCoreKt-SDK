package ch.decent.sdk.model.operation

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.ObjectType
import com.google.gson.annotations.SerializedName

/**
 * Fund asset pool operation constructor. Any account can fund a pool.
 *
 * @param from account id funding the pool
 * @param uia the uia asset value to fund
 * @param dct the dct asset value to fund
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class AssetFundPoolsOperation(
    @SerializedName("from_account") val from: ChainObject,
    @SerializedName("uia_asset") val uia: AssetAmount,
    @SerializedName("dct_asset") val dct: AssetAmount,
    fee: Fee = Fee()
) : BaseOperation(OperationType.ASSET_FUND_POOLS_OPERATION, fee) {

  init {
    require(from.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
    require(dct.assetId == DCoreConstants.DCT_ASSET_ID) { "not an DCT asset id" }
  }

  override fun toString(): String {
    return "AssetFundPoolsOperation(from=$from, uia=$uia, dct=$dct)"
  }

}
