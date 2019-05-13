package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.VestingBalance
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetVestingBalances(
    accountId: ChainObject
) : BaseRequest<List<VestingBalance>>(
    ApiGroup.DATABASE,
    "get_vesting_balances",
    TypeToken.getParameterized(List::class.java, VestingBalance::class.java).type,
    listOf(accountId.objectId)
) {

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
  }
}
