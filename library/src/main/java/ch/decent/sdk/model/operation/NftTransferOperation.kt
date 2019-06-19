package ch.decent.sdk.model.operation

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.ObjectType
import com.google.gson.annotations.SerializedName

class NftTransferOperation @JvmOverloads constructor(
    @SerializedName("from") val from: ChainObject,
    @SerializedName("to") val to: ChainObject,
    @SerializedName("nft_data_id") val id: ChainObject,
    @SerializedName("memo") var memo: Memo? = null,
    fee: Fee = Fee()
) : BaseOperation(OperationType.NFT_TRANSFER, fee) {

  init {
    require(from.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(to.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(id.objectType == ObjectType.NFT_DATA_OBJECT) { "not a valid nft data object id" }
  }

  override fun toString(): String {
    return "NftTransferOperation(from=$from, to=$to, id=$id, memo=$memo)"
  }

}
