package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

// todo wait for proposal operation
internal class GetProposedTransactions(
    account: AccountObjectId
) : BaseRequest<List<Any>>(
    ApiGroup.DATABASE,
    "get_proposed_transactions",
    TypeToken.getParameterized(List::class.java, Any::class.java).type,
    listOf(account)
)
