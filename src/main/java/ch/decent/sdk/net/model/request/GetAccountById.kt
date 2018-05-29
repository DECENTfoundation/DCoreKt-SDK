package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Account
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import com.google.gson.reflect.TypeToken

public class GetAccountById(
    accountId: ChainObject
) : GetObjects<List<Account>>(
    listOf(accountId),
    TypeToken.getParameterized(List::class.java, Account::class.java).type
) {

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT)
  }
}