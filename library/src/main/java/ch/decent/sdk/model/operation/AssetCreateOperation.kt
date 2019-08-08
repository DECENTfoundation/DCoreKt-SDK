package ch.decent.sdk.model.operation

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.DCoreConstants.PRECISION_ALLOWED
import ch.decent.sdk.DCoreConstants.UIA_DESCRIPTION_MAX_CHARS
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.AssetOptions
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.MonitoredAssetOptions
import ch.decent.sdk.model.types.UInt8
import com.google.gson.annotations.SerializedName

/**
 * Create Asset operation constructor.
 *
 * @param issuer account id issuing the asset
 * @param symbol the string symbol, 3-16 uppercase chars
 * @param precision base unit precision, decimal places used in string representation
 * @param description optional description
 * @param options asset options
 * @param monitoredOptions options for monitored asset
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class AssetCreateOperation @JvmOverloads constructor(
    @SerializedName("issuer") val issuer: AccountObjectId,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("precision") @UInt8 val precision: Byte,
    @SerializedName("description") val description: String,
    @SerializedName("options") val options: AssetOptions,
    @SerializedName("monitored_asset_opts") val monitoredOptions: MonitoredAssetOptions? = null,
    fee: Fee = Fee()
) : BaseOperation(OperationType.ASSET_CREATE_OPERATION, fee) {

  init {
    require(Asset.isValidSymbol(symbol)) { "invalid asset symbol: $symbol" }
    require(precision in PRECISION_ALLOWED) { "precision must be in range of 0-12" }
    require(description.length <= UIA_DESCRIPTION_MAX_CHARS) { "description cannot be longer then $UIA_DESCRIPTION_MAX_CHARS chars" }
    require(options.maxSupply <= DCoreConstants.MAX_SHARE_SUPPLY) { "max supply max value overflow" }
  }

  override fun toString(): String {
    return "AssetCreateOperation(issuer=$issuer, symbol='$symbol', precision=$precision, " +
        "description='$description', options=$options, monitoredOptions=$monitoredOptions)"
  }

}
