package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Account
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.AssetObjectId
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetNamedAccountBalances(
    account: String,
    assets: List<AssetObjectId> = emptyList()
) : BaseRequest<List<AssetAmount>>(
    ApiGroup.DATABASE,
    "get_named_account_balances",
    TypeToken.getParameterized(List::class.java, AssetAmount::class.java).type,
    listOf(account, assets)
) {

  init {
    require(Account.isValidName(account)) { "not a valid name: $account" }
  }
}
