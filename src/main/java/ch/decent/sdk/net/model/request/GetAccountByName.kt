package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Account
import ch.decent.sdk.net.model.ApiGroup

internal class GetAccountByName(
    name: String
) : BaseRequest<Account>(
    ApiGroup.DATABASE,
    "get_account_by_name",
    Account::class.java,
    listOf(name)
)