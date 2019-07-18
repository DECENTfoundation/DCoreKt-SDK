package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.NftObjectId
import ch.decent.sdk.net.serialization.Variant
import com.google.gson.annotations.SerializedName

class NftIssueOperation @JvmOverloads constructor(
    @SerializedName("issuer") val issuer: AccountObjectId,
    @SerializedName("nft_id") val id: NftObjectId,
    @SerializedName("to") val to: AccountObjectId,
    @SerializedName("data") var data: List<Variant> = emptyList(),
    @SerializedName("memo") var memo: Memo? = null,
    fee: Fee = Fee()
) : BaseOperation(OperationType.NFT_ISSUE, fee) {

  override fun toString(): String {
    return "NftIssueOperation(issuer=$issuer, to=$to, id=$id, data=$data, memo=$memo)"
  }
}
