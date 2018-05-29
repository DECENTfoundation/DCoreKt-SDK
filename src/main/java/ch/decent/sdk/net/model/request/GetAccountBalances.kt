package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

class GetAccountBalances(
    accountId: ChainObject,
    assets: Set<String> = emptySet()
): BaseRequest<List<AssetAmount>>(
    ApiGroup.DATABASE,
    "get_account_balances",
    TypeToken.getParameterized(List::class.java, AssetAmount::class.java).type,
    listOf(accountId.objectId, assets)
) {

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT)
  }
}