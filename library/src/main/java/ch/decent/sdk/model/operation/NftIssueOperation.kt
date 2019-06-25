package ch.decent.sdk.model.operation

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.serialization.Variant
import com.google.gson.annotations.SerializedName

class NftIssueOperation @JvmOverloads constructor(
    @SerializedName("issuer") val issuer: ChainObject,
    @SerializedName("nft_id") val id: ChainObject,
    @SerializedName("to") val to: ChainObject,
    @SerializedName("data") var data: List<Variant> = emptyList(),
    @SerializedName("memo") var memo: Memo? = null,
    fee: Fee = Fee()
) : BaseOperation(OperationType.NFT_ISSUE, fee) {

  init {
    require(issuer.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(to.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(id.objectType == ObjectType.NFT_OBJECT) { "not a valid nft object id" }
  }

  override fun toString(): String {
    return "NftIssueOperation(issuer=$issuer, to=$to, id=$id, data=$data, memo=$memo)"
  }
}
