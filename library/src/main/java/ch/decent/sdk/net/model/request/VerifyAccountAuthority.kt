package ch.decent.sdk.net.model.request

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.Account
import ch.decent.sdk.net.model.ApiGroup

internal class VerifyAccountAuthority(
    account: String,
    keys: List<Address>
) : BaseRequest<Boolean>(
    ApiGroup.DATABASE,
    "verify_account_authority",
    Boolean::class.java,
    listOf(account, keys)
) {
  init {
    require(Account.isValidName(account)) { "not a valid account name" }
  }
}