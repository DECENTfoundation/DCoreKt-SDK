package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Account
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import com.google.gson.reflect.TypeToken

internal class GetAccountById(
    accountIds: List<ChainObject>
) : GetObjects<List<Account>>(
    accountIds,
    TypeToken.getParameterized(List::class.java, Account::class.java).type
) {

  init {
    require(accountIds.all { it.objectType == ObjectType.ACCOUNT_OBJECT }) { "not a valid account object id" }
  }
}