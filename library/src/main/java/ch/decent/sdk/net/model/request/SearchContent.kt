package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Account
import ch.decent.sdk.model.ContentObjectId
import ch.decent.sdk.model.ContentSummary
import ch.decent.sdk.model.SearchContentOrder
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken

internal class SearchContent(
    term: String,
    order: SearchContentOrder = SearchContentOrder.CREATED_DESC,
    user: String,
    regionCode: String,
    type: String,
    startId: ContentObjectId? = null,
    limit: Int = REQ_LIMIT_MAX
) : BaseRequest<List<ContentSummary>>(
    ApiGroup.DATABASE,
    "search_content",
    TypeToken.getParameterized(List::class.java, ContentSummary::class.java).type,
    listOf(term, order.value, user, regionCode, type, startId, limit)
) {

  init {
    require(Account.isValidName(user) || user.isBlank()) { "not a valid account name" }
  }
}
