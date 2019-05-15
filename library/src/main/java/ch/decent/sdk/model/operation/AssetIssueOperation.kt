package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.ObjectType
import com.google.gson.annotations.SerializedName

/**
 * Issue asset operation constructor. Only the issuer of the asset can issue some funds until maxSupply is reached.
 *
 * @param issuer asset issuer account id
 * @param assetToIssue asset amount to issue
 * @param issueToAccount account id receiving the created funds
 * @param memo optional memo for receiver
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class AssetIssueOperation @JvmOverloads constructor(
    @SerializedName("issuer") val issuer: ChainObject,
    @SerializedName("asset_to_issue") val assetToIssue: AssetAmount,
    @SerializedName("issue_to_account") val issueToAccount: ChainObject,
    @SerializedName("memo") val memo: Memo? = null,
    fee: Fee = Fee()
) : BaseOperation(OperationType.ASSET_ISSUE_OPERATION, fee) {

  init {
    require(issuer.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(issueToAccount.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
  }

  override fun toString(): String {
    return "AssetIssueOperation(issuer=$issuer, assetToIssue=$assetToIssue, issueToAccount=$issueToAccount, memo=$memo)"
  }

}
