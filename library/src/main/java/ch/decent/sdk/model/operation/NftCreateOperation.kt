package ch.decent.sdk.model.operation

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.NftDataType
import ch.decent.sdk.model.NftOptions
import com.google.gson.annotations.SerializedName

class NftCreateOperation @JvmOverloads constructor(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("options") val options: NftOptions,
    @SerializedName("definitions") val definitions: List<NftDataType>,
    @SerializedName("transferable") val transferable: Boolean,
    fee: Fee = Fee()
) : BaseOperation(OperationType.NFT_CREATE_DEFINITION, fee) {

  init {
    require(Asset.isValidSymbol(symbol)) { "invalid nft symbol: $symbol" }
    require(options.description.length <= DCoreConstants.UIA_DESCRIPTION_MAX_CHARS)
    { "description cannot be longer then ${DCoreConstants.UIA_DESCRIPTION_MAX_CHARS} chars" }
    require(definitions.all { it.modifiable == NftDataType.Modifiable.NOBODY || it.name != null }) { "modifiable data type must have name" }
    require(definitions.all { it.name == null || it.name.length <= DCoreConstants.NFT_NAME_MAX_CHARS })
    { "name cannot be longer then ${DCoreConstants.NFT_NAME_MAX_CHARS} chars" }
    require(options.maxSupply <= 0xFFFFFFFF)
  }

  override fun toString(): String {
    return "NftCreateOperation(symbol='$symbol', options=$options, definitions=$definitions, transferable=$transferable)"
  }
}
