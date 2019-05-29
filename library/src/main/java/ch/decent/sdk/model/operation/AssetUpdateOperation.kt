package ch.decent.sdk.model.operation

import ch.decent.sdk.DCoreConstants.UIA_DESCRIPTION_MAX_CHARS
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetObjectId
import ch.decent.sdk.model.ExchangeRate
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.types.UInt64
import com.google.gson.annotations.SerializedName

/**
 * Update asset operation constructor.
 *
 * @param issuer account id issuing the asset
 * @param assetToUpdate asset to update
 * @param coreExchangeRate new exchange rate
 * @param newDescription new description
 * @param exchangeable enable converting the asset to DCT, so it can be used to pay for fees
 * @param maxSupply new max supply
 * @param newIssuer a new issuer account id
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class AssetUpdateOperation @JvmOverloads constructor(
    @SerializedName("issuer") val issuer: AccountObjectId,
    @SerializedName("asset_to_update") val assetToUpdate: AssetObjectId,
    @SerializedName("new_description") var newDescription: String,
    @SerializedName("new_issuer") val newIssuer: AccountObjectId?,
    @SerializedName("max_supply") @UInt64 var maxSupply: Long, // Asset.options.maxSupply is @Int64 therefore we use Long here
    @SerializedName("core_exchange_rate") var coreExchangeRate: ExchangeRate,
    @SerializedName("is_exchangeable") var exchangeable: Boolean,
    fee: Fee = Fee()
) : BaseOperation(OperationType.UPDATE_USER_ISSUED_ASSET_OPERATION, fee) {

  init {
    require(newDescription.length <= UIA_DESCRIPTION_MAX_CHARS) { "description cannot be longer then $UIA_DESCRIPTION_MAX_CHARS chars" }
//    require(maxSupply <= DCoreConstants.MAX_SHARE_SUPPLY) { "max supply max value overflow" }
  }

  override fun toString(): String {
    return "AssetUpdateOperation(issuer=$issuer, assetToUpdate=$assetToUpdate, " +
        "newDescription='$newDescription', newIssuer=$newIssuer, maxSupply=$maxSupply, coreExchangeRate=$coreExchangeRate, exchangeable=$exchangeable)"
  }

}
