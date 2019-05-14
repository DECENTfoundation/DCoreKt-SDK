package ch.decent.sdk.model

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.types.Int64

/**
 * Fee asset amount.
 *
 * @param assetId asset id of the fee, by default DCT
 * @param amount fee amount, if null the fee amount will be fetched from DCore
 */
data class Fee @JvmOverloads constructor(
    val assetId: ChainObject = DCoreConstants.DCT_ASSET_ID,
    @Int64 val amount: Long? = null
) {

  init {
    require((amount ?: 0) >= 0) { "amount must be greater or equal to 0" }
    require(assetId.objectType == ObjectType.ASSET_OBJECT) { "object id is not an asset" }
  }

}
