package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetAccountBalances(
    accountId: ChainObject,
    assets: List<ChainObject> = emptyList()
) : BaseRequest<List<AssetAmount>>(
    ApiGroup.DATABASE,
    "get_account_balances",
    TypeToken.getParameterized(List::class.java, AssetAmount::class.java).type,
    listOf(accountId.objectId, assets)
) {

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
  }
}