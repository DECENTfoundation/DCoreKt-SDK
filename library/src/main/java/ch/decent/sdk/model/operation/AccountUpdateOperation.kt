package ch.decent.sdk.model.operation

import ch.decent.sdk.model.Account
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AccountOptions
import ch.decent.sdk.model.Authority
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.VoteId
import com.google.gson.annotations.SerializedName

/**
 * Request to account update operation constructor
 *
 * @param accountId account
 * @param owner owner authority
 * @param active active authority
 * @param options account options
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class AccountUpdateOperation @JvmOverloads constructor(
    @SerializedName("account") val accountId: AccountObjectId,
    @SerializedName("owner") var owner: Authority? = null,
    @SerializedName("active") var active: Authority? = null,
    @SerializedName("new_options") var options: AccountOptions? = null,
    fee: Fee = Fee()
) : BaseOperation(OperationType.ACCOUNT_UPDATE_OPERATION, fee) {

  constructor(account: Account, fee: Fee) : this(account.id, account.owner, account.active, account.options, fee)

  constructor(account: Account, votes: Set<VoteId>, fee: Fee) : this(account.id, options = account.options.copy(votes = votes), fee = fee)

  override fun toString(): String {
    return "AccountUpdateOperation(accountId=$accountId, owner=$owner, active=$active, options=$options)"
  }

}
