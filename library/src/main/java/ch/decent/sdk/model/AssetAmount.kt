package ch.decent.sdk.model

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.types.Int64
import com.google.gson.annotations.SerializedName

data class AssetAmount @JvmOverloads constructor(
    @SerializedName("amount") @Int64 val amount: Long,
    @SerializedName("asset_id") val assetId: AssetObjectId = DCoreConstants.DCT_ASSET_ID
) {

  init {
    require(amount >= 0) { "amount must be greater or equal to 0" }
  }
}
