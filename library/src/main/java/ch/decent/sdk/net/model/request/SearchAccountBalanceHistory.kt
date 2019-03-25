package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.BalanceChange
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class SearchAccountBalanceHistory(
    accountId: ChainObject,
    assets: List<ChainObject> = emptyList(),
    recipientAccount: ChainObject? = null,
    fromBlock: Long = 0,
    toBlock: Long = 0,
    startOffset: Long = 0,
    limit: Int = 100
) : BaseRequest<List<BalanceChange>>(
    ApiGroup.HISTORY,
    "search_account_balance_history",
    TypeToken.getParameterized(List::class.java, BalanceChange::class.java).type,
    listOf(accountId, assets, recipientAccount, fromBlock, toBlock, startOffset, limit)
) {
  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
    require(assets.all { it.objectType == ObjectType.ASSET_OBJECT }) { "not a valid asset object id" }
    require(recipientAccount?.objectType?.equals(ObjectType.ACCOUNT_OBJECT) ?: true) { "not a valid account object id" }
  }
}