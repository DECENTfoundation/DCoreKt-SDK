package ch.decent.sdk.net.model.request

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.Account
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.ObjectId
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.isValidId
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
    require(Account.isValidName(nameOrId) || nameOrId.isValidId<AccountObjectId>()) { "not a valid account name or id" }
  }
}
