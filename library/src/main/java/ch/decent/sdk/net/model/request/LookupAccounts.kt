package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX_1K
import com.google.gson.reflect.TypeToken

internal class LookupAccounts(
    lowerBound: String,
    limit: Int = REQ_LIMIT_MAX_1K
) : BaseRequest<Map<String, AccountObjectId>>(
    ApiGroup.DATABASE,
    "lookup_accounts",
    TypeToken.getParameterized(Map::class.java, String::class.java, AccountObjectId::class.java).type,
    listOf(lowerBound, limit)
)
