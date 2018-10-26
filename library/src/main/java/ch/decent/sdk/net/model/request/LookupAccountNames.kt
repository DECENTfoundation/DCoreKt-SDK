package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Account
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class LookupAccountNames(
    names: List<String>
) : BaseRequest<List<Account>>(
    ApiGroup.DATABASE,
    "lookup_account_names",
    TypeToken.getParameterized(List::class.java, Account::class.java).type,
    listOf(names))