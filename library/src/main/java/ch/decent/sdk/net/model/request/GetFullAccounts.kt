package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.FullAccount
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetFullAccounts(
    namesOrIds: List<String>,
    subscribe: Boolean
) : BaseRequest<Map<String, FullAccount>>(
    ApiGroup.DATABASE,
    "get_full_accounts",
    TypeToken.getParameterized(Map::class.java, String::class.java, FullAccount::class.java).type,
    listOf(namesOrIds, subscribe)
)