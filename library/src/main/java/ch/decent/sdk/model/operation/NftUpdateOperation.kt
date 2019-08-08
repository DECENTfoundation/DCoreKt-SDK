package ch.decent.sdk.model.operation

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.NftObjectId
import ch.decent.sdk.model.NftOptions
import com.google.gson.annotations.SerializedName

class NftUpdateOperation @JvmOverloads constructor(
    @SerializedName("current_issuer") val issuer: AccountObjectId,
    @SerializedName("nft_id") val id: NftObjectId,
    @SerializedName("options") var options: NftOptions,
    fee: Fee = Fee()
) : BaseOperation(OperationType.NFT_UPDATE_DEFINITION, fee) {

  init {
    require(options.description.length <= DCoreConstants.UIA_DESCRIPTION_MAX_CHARS)
    { "description cannot be longer then ${DCoreConstants.UIA_DESCRIPTION_MAX_CHARS} chars" }
    require(options.maxSupply <= 0xFFFFFFFF)
  }

  override fun toString(): String {
    return "NftUpdateOperation(issuer=$issuer, id=$id, options=$options)"
  }
}
