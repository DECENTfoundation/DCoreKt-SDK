package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetAccountReferences(
    accountId: ChainObject
) : BaseRequest<List<ChainObject>>(
    ApiGroup.DATABASE,
    "get_account_references",
    TypeToken.getParameterized(List::class.java, ChainObject::class.java).type,
    listOf(accountId)
)