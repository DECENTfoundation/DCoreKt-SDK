package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken

internal class LookupMinerAccounts(
    lookupTerm: String = "",
    limit: Int = 100
) : BaseRequest<List<JsonArray>>(
    ApiGroup.DATABASE,
    "lookup_miner_accounts",
    TypeToken.getParameterized(List::class.java, JsonArray::class.java).type,
    listOf(lookupTerm, limit))