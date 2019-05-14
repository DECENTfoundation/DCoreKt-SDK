package ch.decent.sdk.model.operation

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.Account
import ch.decent.sdk.model.AccountOptions
import ch.decent.sdk.model.Authority
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.ObjectType
import com.google.gson.annotations.SerializedName

/**
 * Request to create account operation constructor
 *
 * @param registrar registrar
 * @param name account name
 * @param owner owner authority
 * @param active active authority
 * @param options account options
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class AccountCreateOperation @JvmOverloads constructor(
    @SerializedName("registrar") val registrar: ChainObject,
    @SerializedName("name") val name: String,
    @SerializedName("owner") val owner: Authority,
    @SerializedName("active") val active: Authority,
    @SerializedName("options") val options: AccountOptions,
    fee: Fee = Fee()
) : BaseOperation(OperationType.ACCOUNT_CREATE_OPERATION, fee) {

  init {
    require(registrar.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
    require(Account.isValidName(name)) { "not a valid name" }
  }

  @JvmOverloads
  constructor(registrar: ChainObject, name: String, public: Address, fee: Fee = Fee()) :
      this(registrar, name, Authority(public), Authority(public), AccountOptions(public), fee)

  override fun toString(): String {
    return "AccountCreateOperation(registrar=$registrar, name='$name', owner=$owner, active=$active, options=$options)"
  }
}
