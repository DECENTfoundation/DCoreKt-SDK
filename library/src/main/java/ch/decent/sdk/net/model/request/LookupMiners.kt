package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class LookupMiners(
    lowerBound: String,
    limit: Int = 1000
) : BaseRequest<Map<String, ChainObject>>(
    ApiGroup.DATABASE,
    "lookup_miners",
    TypeToken.getParameterized(Map::class.java, String::class.java, ChainObject::class.java).type,
    listOf(lowerBound, limit))