package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Account
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.SearchAccountsOrder
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX_1K
import com.google.gson.reflect.TypeToken

internal class SearchAccounts(
    searchTerm: String,
    order: SearchAccountsOrder = SearchAccountsOrder.NAME_DESC,
    id: ChainObject = ObjectType.NULL_OBJECT.genericId,
    limit: Int = REQ_LIMIT_MAX_1K
) : BaseRequest<List<Account>>(
    ApiGroup.DATABASE,
    "search_accounts",
    TypeToken.getParameterized(List::class.java, Account::class.java).type,
    listOf(searchTerm, order, id, limit)
) {
  init {
    require(id.objectType == ObjectType.NULL_OBJECT || id.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
  }
}
