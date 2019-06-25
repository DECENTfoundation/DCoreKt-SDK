package ch.decent.sdk.model

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.types.Int64
import com.google.gson.annotations.SerializedName

data class AssetAmount @JvmOverloads constructor(
    @SerializedName("amount") @Int64 val amount: Long,
    @SerializedName("asset_id") val assetId: ChainObject = DCoreConstants.DCT_ASSET_ID
) {

  constructor(amount: Long, asset: String) : this(amount, asset.toChainObject())

  init {
    require(amount >= 0) { "amount must be greater or equal to 0" }
    require(assetId.objectType == ObjectType.ASSET_OBJECT) { "object id is not an asset" }
  }
}
