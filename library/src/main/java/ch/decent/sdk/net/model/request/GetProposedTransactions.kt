package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

// todo wait for proposal operation
internal class GetProposedTransactions(
    account: ChainObject
) : BaseRequest<List<Any>>(
    ApiGroup.DATABASE,
    "get_proposed_transactions",
    TypeToken.getParameterized(List::class.java, Any::class.java).type,
    listOf(account)
) {
  init {
    require(account.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
  }
}
