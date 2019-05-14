package ch.decent.sdk.model.operation

import ch.decent.sdk.model.Account
import ch.decent.sdk.model.AccountOptions
import ch.decent.sdk.model.Authority
import ch.decent.sdk.model.ChainObject
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
    @SerializedName("account") val accountId: ChainObject,
    @SerializedName("owner") val owner: Authority? = null,
    @SerializedName("active") val active: Authority? = null,
    @SerializedName("new_options") val options: AccountOptions? = null,
    fee: Fee = Fee()
) : BaseOperation(OperationType.ACCOUNT_UPDATE_OPERATION, fee) {

  constructor(account: Account, votes: Set<VoteId>) : this(account.id, options = account.options.copy(votes = votes))

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
  }

  override fun toString(): String {
    return "AccountUpdateOperation(accountId=$accountId, owner=$owner, active=$active, options=$options)"
  }

}
