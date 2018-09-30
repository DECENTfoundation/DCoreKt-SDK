package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.MinerId
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class LookupMinerAccounts(
    lookupTerm: String = "",
    limit: Int = 1000
) : BaseRequest<List<MinerId>>(
    ApiGroup.DATABASE,
    "lookup_miner_accounts",
    TypeToken.getParameterized(List::class.java, MinerId::class.java).type,
    listOf(lookupTerm, limit))