package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.NftDataObjectId
import com.google.gson.annotations.SerializedName

class NftUpdateDataOperation @JvmOverloads constructor(
    @SerializedName("modifier") val modifier: AccountObjectId,
    @SerializedName("nft_data_id") val id: NftDataObjectId,
    @SerializedName("data") val data: MutableMap<String, Any>,
    fee: Fee = Fee()
) : BaseOperation(OperationType.NFT_UPDATE_DATA, fee) {

  init {
    require(data.isNotEmpty() && data.keys.all { it.isNotBlank() }) { "data cannot be empty or have empty keys" }
  }

  override fun toString(): String {
    return "NftUpdateDataOperation(modifier=$modifier, id=$id, data=$data)"
  }
}
