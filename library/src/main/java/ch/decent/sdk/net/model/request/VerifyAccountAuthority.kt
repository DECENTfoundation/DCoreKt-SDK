package ch.decent.sdk.net.model.request

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.Account
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.net.model.ApiGroup

internal class VerifyAccountAuthority(
    nameOrId: String,
    keys: List<Address>
) : BaseRequest<Boolean>(
    ApiGroup.DATABASE,
    "verify_account_authority",
    Boolean::class.java,
    listOf(nameOrId, keys)
) {
  init {
    require(Account.isValidName(nameOrId) || ChainObject.isValid(nameOrId)) { "not a valid account name or id" }
  }
}
