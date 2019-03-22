package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Account
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class SearchFeedback(
    user: String?,
    uri: String,
    startId: ChainObject = ObjectType.NULL_OBJECT.genericId,
    count: Int = 100
) : BaseRequest<List<Purchase>>(
    ApiGroup.DATABASE,
    "search_feedback",
    TypeToken.getParameterized(List::class.java, Purchase::class.java).type,
    listOf(user, uri, startId, count)
) {

  init {
    require(user?.let { Account.isValidName(it) } ?: true) { "not a valid account name" }
    require(startId == ObjectType.NULL_OBJECT.genericId || startId.objectType == ObjectType.PURCHASE_OBJECT) { "not a valid null or purchase object id" }
  }
}