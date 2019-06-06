package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.MinerObjectId
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken

internal class GetFeedsByMiner(
    account: AccountObjectId,
    count: Int = REQ_LIMIT_MAX
) : BaseRequest<List<Any>>(
    ApiGroup.DATABASE,
    "get_feeds_by_miner",
    TypeToken.getParameterized(List::class.java, Any::class.java).type,
    listOf(account, count)
)
