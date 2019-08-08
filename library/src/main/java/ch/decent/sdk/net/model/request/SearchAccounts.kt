package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Account
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.NullObjectId
import ch.decent.sdk.model.SearchAccountsOrder
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX_1K
import com.google.gson.reflect.TypeToken

internal class SearchAccounts(
    searchTerm: String,
    order: SearchAccountsOrder = SearchAccountsOrder.NAME_DESC,
    id: AccountObjectId? = null,
    limit: Int = REQ_LIMIT_MAX_1K
) : BaseRequest<List<Account>>(
    ApiGroup.DATABASE,
    "search_accounts",
    TypeToken.getParameterized(List::class.java, Account::class.java).type,
    listOf(searchTerm, order.value, id ?: NullObjectId, limit)
)
