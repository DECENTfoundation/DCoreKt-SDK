package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Account
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class LookupAccounts(
    lookupTerm: String,
    limit: Int = 1000
) : BaseRequest<List<Account>>(
    ApiGroup.DATABASE,
    "lookup_accounts",
    TypeToken.getParameterized(List::class.java, Account::class.java).type,
    listOf(lookupTerm, limit))