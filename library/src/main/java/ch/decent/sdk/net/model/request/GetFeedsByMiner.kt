package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken

// todo what is this ?
internal class GetFeedsByMiner(
    account: ChainObject,
    count: Int = REQ_LIMIT_MAX
) : BaseRequest<List<Any>>(
    ApiGroup.DATABASE,
    "get_feeds_by_miner",
    TypeToken.getParameterized(List::class.java, Any::class.java).type,
    listOf(account, count)
) {
  init {
    require(account.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid asset object id" }
  }
}
