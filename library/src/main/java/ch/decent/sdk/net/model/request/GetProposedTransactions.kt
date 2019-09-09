package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Proposal
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetProposedTransactions(
    account: AccountObjectId
) : BaseRequest<List<Proposal>>(
    ApiGroup.DATABASE,
    "get_proposed_transactions",
    TypeToken.getParameterized(List::class.java, Any::class.java).type,
    listOf(account)
)
