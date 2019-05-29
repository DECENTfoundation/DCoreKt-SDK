package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetAccountReferences(
    accountId: AccountObjectId
) : BaseRequest<List<AccountObjectId>>(
    ApiGroup.DATABASE,
    "get_account_references",
    TypeToken.getParameterized(List::class.java, AccountObjectId::class.java).type,
    listOf(accountId)
)
