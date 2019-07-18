package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.NftDataObjectId
import com.google.gson.annotations.SerializedName

class NftTransferOperation @JvmOverloads constructor(
    @SerializedName("from") val from: AccountObjectId,
    @SerializedName("to") val to: AccountObjectId,
    @SerializedName("nft_data_id") val id: NftDataObjectId,
    @SerializedName("memo") var memo: Memo? = null,
    fee: Fee = Fee()
) : BaseOperation(OperationType.NFT_TRANSFER, fee) {

  override fun toString(): String {
    return "NftTransferOperation(from=$from, to=$to, id=$id, memo=$memo)"
  }

}
