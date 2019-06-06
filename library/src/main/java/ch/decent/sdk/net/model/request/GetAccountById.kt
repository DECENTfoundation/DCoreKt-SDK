package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Account
import ch.decent.sdk.model.AccountObjectId
import com.google.gson.reflect.TypeToken

internal class GetAccountById(
    accountIds: List<AccountObjectId>
) : GetObjects<List<Account>>(
    accountIds,
    TypeToken.getParameterized(List::class.java, Account::class.java).type
)
