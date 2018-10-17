package ch.decent.sdk.net.model.request

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetRequiredSignatures(
    transaction: Transaction,
    keys: List<Address>
) : BaseRequest<List<Address>>(
    ApiGroup.DATABASE,
    "get_required_signatures",
    TypeToken.getParameterized(List::class.java, Address::class.java).type,
    listOf(transaction, keys)
)