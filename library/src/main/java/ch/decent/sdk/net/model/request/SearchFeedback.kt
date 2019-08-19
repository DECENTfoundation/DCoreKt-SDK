package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Account
import ch.decent.sdk.model.NullObjectId
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.model.PurchaseObjectId
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken

internal class SearchFeedback(
    user: String?,
    uri: String,
    startId: PurchaseObjectId? = null,
    count: Int = REQ_LIMIT_MAX
) : BaseRequest<List<Purchase>>(
    ApiGroup.DATABASE,
    "search_feedback",
    TypeToken.getParameterized(List::class.java, Purchase::class.java).type,
    listOf(user, uri, startId ?: NullObjectId, count)
) {

  init {
    require(user?.let { Account.isValidName(it) } ?: true) { "not a valid account name" }
  }
}
