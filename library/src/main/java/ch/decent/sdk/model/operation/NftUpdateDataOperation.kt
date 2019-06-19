package ch.decent.sdk.model.operation

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.ObjectType
import com.google.gson.annotations.SerializedName

class NftUpdateDataOperation @JvmOverloads constructor(
    @SerializedName("modifier") val modifier: ChainObject,
    @SerializedName("nft_data_id") val id: ChainObject,
    @SerializedName("data") val data: MutableMap<String, Any>,
    fee: Fee = Fee()
) : BaseOperation(OperationType.NFT_UPDATE_DATA, fee) {

  init {
    require(modifier.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(id.objectType == ObjectType.NFT_DATA_OBJECT) { "not a valid nft object id" }
    require(data.isNotEmpty() && data.keys.all { it.isNotBlank() }) { "data cannot be empty or have empty keys" }
  }

  override fun toString(): String {
    return "NftUpdateDataOperation(modifier=$modifier, id=$id, data=$data)"
  }
}
