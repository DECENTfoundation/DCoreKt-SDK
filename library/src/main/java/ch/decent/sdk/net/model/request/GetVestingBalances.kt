package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.VestingBalance
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetVestingBalances(
    accountId: AccountObjectId
) : BaseRequest<List<VestingBalance>>(
    ApiGroup.DATABASE,
    "get_vesting_balances",
    TypeToken.getParameterized(List::class.java, VestingBalance::class.java).type,
    listOf(accountId)
)
