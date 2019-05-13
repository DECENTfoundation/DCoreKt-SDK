package ch.decent.sdk.net.model.request

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetKeyReferences(
    address: List<Address>
) : BaseRequest<List<List<ChainObject>>>(
    ApiGroup.DATABASE,
    "get_key_references",
    TypeToken.getParameterized(List::class.java, TypeToken.getParameterized(List::class.java, ChainObject::class.java).type).type,
    listOf(address)
)
