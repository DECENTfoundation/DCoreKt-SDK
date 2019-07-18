@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.model.PurchaseObjectId
import ch.decent.sdk.model.SearchPurchasesOrder
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.operation.LeaveRatingAndCommentOperation
import ch.decent.sdk.net.model.request.GetBuyingByUri
import ch.decent.sdk.net.model.request.GetHistoryBuyingsByConsumer
import ch.decent.sdk.net.model.request.GetOpenBuyings
import ch.decent.sdk.net.model.request.GetOpenBuyingsByConsumer
import ch.decent.sdk.net.model.request.GetOpenBuyingsByUri
import ch.decent.sdk.net.model.request.SearchBuyings
import ch.decent.sdk.net.model.request.SearchFeedback
import io.reactivex.Single

class PurchaseApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get a list of history purchases for consumer id.
   *
   * @param accountId consumer account object id, 1.2.*
   *
   * @return a list of history purchases
   */
  fun getAllHistory(accountId: AccountObjectId): Single<List<Purchase>> = GetHistoryBuyingsByConsumer(accountId).toRequest()

  /**
   * Get a list of open purchases.
   *
   * @return a list of open purchases
   */
  fun getAllOpen(): Single<List<Purchase>> = GetOpenBuyings.toRequest()

  /**
   * Get a list of open purchases for content URI.
   *
   * @param uri content uri
   *
   * @return a list of open purchases
   */
  fun getAllOpenByUri(uri: String): Single<List<Purchase>> = GetOpenBuyingsByUri(uri).toRequest()

  /**
   * Get a list of open purchases for consumer id.
   *
   * @param accountId consumer account object id, 1.2.*
   *
   * @return a list of open purchases
   */
  fun getAllOpenByAccount(accountId: AccountObjectId): Single<List<Purchase>> = GetOpenBuyingsByConsumer(accountId).toRequest()

  /**
   * Get consumer purchase by content uri.
   *
   * @param consumer object id of the account, 1.2.*
   * @param uri a uri of the content
   *
   * @return an account if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun get(
      consumer: AccountObjectId,
      uri: String
  ): Single<Purchase> = GetBuyingByUri(consumer, uri).toRequest()

  /**
   * Search consumer open and history purchases.
   *
   * @param consumer object id of the account, 1.2.*
   * @param term search term
   * @param from object id of the history object to start from, use [ObjectType.NULL_OBJECT.genericId] to ignore
   * @param order [SearchPurchasesOrder]
   * @param limit number of entries, max 100
   *
   * @return list of purchases
   */
  @JvmOverloads
  fun findAll(
      consumer: AccountObjectId,
      term: String = "",
      from: PurchaseObjectId? = null,
      order: SearchPurchasesOrder = SearchPurchasesOrder.PURCHASED_DESC,
      limit: Int = 100
  ): Single<List<Purchase>> = SearchBuyings(consumer, order, from, term, limit).toRequest()

  /**
   * Search for feedback.
   *
   * @param uri content URI
   * @param user feedback author account name
   * @param count count	maximum number of feedback objects to fetch
   * @param startId the id of purchase object to start searching from
   *
   * @return a list of purchase objects
   */
  @JvmOverloads
  fun findAllForFeedback(
      uri: String,
      user: String? = null,
      count: Int = 100,
      startId: PurchaseObjectId? = null
  ): Single<List<Purchase>> = SearchFeedback(user, uri, startId, count).toRequest()

  /**
   * Create a rate and comment content operation.
   *
   * @param uri a uri of the content
   * @param consumer object id of the account, 1.2.*
   * @param rating 1-5 stars
   * @param comment max 100 chars
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   *
   * @return a rate and comment content operation
   */
  @JvmOverloads
  fun createRateAndCommentOperation(
      uri: String,
      consumer: AccountObjectId,
      rating: Byte,
      comment: String,
      fee: Fee = Fee()
  ): Single<LeaveRatingAndCommentOperation> = Single.just(LeaveRatingAndCommentOperation(uri, consumer, rating, comment, fee))

  /**
   * Rate and comment content operation.
   *
   * @param credentials account credentials
   * @param uri a uri of the content
   * @param rating 1-5 stars
   * @param comment max 100 chars
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   *
   * @return a rate and comment content operation
   */
  @JvmOverloads
  fun rateAndComment(
      credentials: Credentials,
      uri: String,
      rating: Byte,
      comment: String,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createRateAndCommentOperation(uri, credentials.account, rating, comment, fee)
      .broadcast(credentials)

}
