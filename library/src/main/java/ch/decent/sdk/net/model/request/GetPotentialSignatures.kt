package ch.decent.sdk.net.model.request

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken
import java.math.BigInteger

internal class GetPotentialSignatures(
    transaction: Transaction
) : BaseRequest<List<Address>>(
    ApiGroup.DATABASE,
    "get_potential_signatures",
    TypeToken.getParameterized(List::class.java, Address::class.java).type,
    listOf(transaction)
)