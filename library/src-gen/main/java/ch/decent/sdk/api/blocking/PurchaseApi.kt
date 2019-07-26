@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.PurchaseObjectId
import ch.decent.sdk.model.SearchPurchasesOrder
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import kotlin.Byte
import kotlin.Int
import kotlin.String
import kotlin.Suppress

class PurchaseApi internal constructor(
  private val api: ch.decent.sdk.api.PurchaseApi
) {
  fun getAllHistory(accountId: AccountObjectId) = api.getAllHistory(accountId).blockingGet()
  fun getAllOpen() = api.getAllOpen().blockingGet()
  fun getAllOpenByUri(uri: String) = api.getAllOpenByUri(uri).blockingGet()
  fun getAllOpenByAccount(accountId: AccountObjectId) =
      api.getAllOpenByAccount(accountId).blockingGet()
  fun get(consumer: AccountObjectId, uri: String) = api.get(consumer, uri).blockingGet()
  fun findAll(
    consumer: AccountObjectId,
    term: String = "",
    from: PurchaseObjectId? = null,
    order: SearchPurchasesOrder = SearchPurchasesOrder.PURCHASED_DESC,
    limit: Int = REQ_LIMIT_MAX
  ) = api.findAll(consumer, term, from, order, limit).blockingGet()
  fun findAllForFeedback(
    uri: String,
    user: String? = null,
    count: Int = REQ_LIMIT_MAX,
    startId: PurchaseObjectId? = null
  ) = api.findAllForFeedback(uri, user, count, startId).blockingGet()
  fun createRateAndCommentOperation(
    uri: String,
    consumer: AccountObjectId,
    rating: Byte,
    comment: String,
    fee: Fee = Fee()
  ) = api.createRateAndCommentOperation(uri, consumer, rating, comment, fee).blockingGet()
  fun rateAndComment(
    credentials: Credentials,
    uri: String,
    rating: Byte,
    comment: String,
    fee: Fee = Fee()
  ) = api.rateAndComment(credentials, uri, rating, comment, fee).blockingGet()}
