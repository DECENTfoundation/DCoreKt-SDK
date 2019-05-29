package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.Fee
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
class AssetFundPoolsOperation @JvmOverloads constructor(
    @SerializedName("from_account") val from: AccountObjectId,
    @SerializedName("uia_asset") val uia: AssetAmount,
    @SerializedName("dct_asset") val dct: AssetAmount,
    fee: Fee = Fee()
) : BaseOperation(OperationType.ASSET_FUND_POOLS_OPERATION, fee) {

  override fun toString(): String {
    return "AssetFundPoolsOperation(from=$from, uia=$uia, dct=$dct)"
  }

}
