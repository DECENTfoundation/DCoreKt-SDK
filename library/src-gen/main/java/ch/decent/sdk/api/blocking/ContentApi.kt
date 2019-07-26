@file:Suppress(
    "TooManyFunctions",
    "LongParameterList"
)

package ch.decent.sdk.api.blocking

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.CoAuthors
import ch.decent.sdk.model.ContentObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.PubKey
import ch.decent.sdk.model.PurchaseObjectId
import ch.decent.sdk.model.RegionalPrice
import ch.decent.sdk.model.Regions
import ch.decent.sdk.model.SearchContentOrder
import ch.decent.sdk.model.Synopsis
import ch.decent.sdk.model.contentType
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import org.threeten.bp.LocalDateTime

class ContentApi internal constructor(
    private val api: ch.decent.sdk.api.ContentApi
) {
  fun generateKeys(seeders: List<AccountObjectId>) = api.generateKeys(seeders).blockingGet()
  fun get(contentId: ContentObjectId) = api.get(contentId).blockingGet()
  fun getAll(contentId: List<ContentObjectId>) = api.getAll(contentId).blockingGet()
  fun get(uri: String) = api.get(uri).blockingGet()
  fun listAllPublishersRelative(lowerBound: String, limit: Int = REQ_LIMIT_MAX) =
      api.listAllPublishersRelative(lowerBound, limit).blockingGet()

  fun restoreEncryptionKey(elGamalPrivate: PubKey, purchaseId: PurchaseObjectId) =
      api.restoreEncryptionKey(elGamalPrivate, purchaseId).blockingGet()

  fun findAll(
      term: String,
      order: SearchContentOrder = SearchContentOrder.CREATED_DESC,
      user: String = "",
      regionCode: String = Regions.ALL.code,
      type: String = contentType(),
      startId: ContentObjectId? = null,
      limit: Int = REQ_LIMIT_MAX
  ) = api.findAll(term, order, user, regionCode, type, startId, limit).blockingGet()

  fun createPurchaseOperation(
      credentials: Credentials,
      contentId: ContentObjectId,
      fee: Fee = Fee()
  ) = api.createPurchaseOperation(credentials, contentId, fee).blockingGet()

  fun createPurchaseOperation(
      credentials: Credentials,
      uri: String,
      fee: Fee = Fee()
  ) = api.createPurchaseOperation(credentials, uri, fee).blockingGet()

  fun purchase(
      credentials: Credentials,
      contentId: ContentObjectId,
      fee: Fee = Fee()
  ) = api.purchase(credentials, contentId, fee).blockingGet()

  fun purchase(
      credentials: Credentials,
      uri: String,
      fee: Fee = Fee()
  ) = api.purchase(credentials, uri, fee).blockingGet()

  fun createTransfer(
      credentials: Credentials,
      id: ContentObjectId,
      amount: AssetAmount,
      memo: String? = null,
      fee: Fee = Fee()
  ) = api.createTransfer(credentials, id, amount, memo, fee).blockingGet()

  fun transfer(
      credentials: Credentials,
      id: ContentObjectId,
      amount: AssetAmount,
      memo: String? = null,
      fee: Fee = Fee()
  ) = api.transfer(credentials, id, amount, memo, fee).blockingGet()

  fun createRemoveContentOperation(content: ContentObjectId, fee: Fee = Fee()) =
      api.createRemoveContentOperation(content, fee).blockingGet()

  fun createRemoveContentOperation(content: String, fee: Fee = Fee()) =
      api.createRemoveContentOperation(content, fee).blockingGet()

  fun remove(
      credentials: Credentials,
      content: ContentObjectId,
      fee: Fee = Fee()
  ) = api.remove(credentials, content, fee).blockingGet()

  fun remove(
      credentials: Credentials,
      content: String,
      fee: Fee = Fee()
  ) = api.remove(credentials, content, fee).blockingGet()

  fun createAddContentOperation(
      author: AccountObjectId,
      coAuthors: CoAuthors,
      uri: String,
      price: List<RegionalPrice>,
      expiration: LocalDateTime,
      synopsis: Synopsis,
      fee: Fee = Fee()
  ) = api.createAddContentOperation(author, coAuthors, uri, price, expiration, synopsis,
      fee).blockingGet()

  fun add(
      credentials: Credentials,
      coAuthors: CoAuthors,
      uri: String,
      price: List<RegionalPrice>,
      expiration: LocalDateTime,
      synopsis: Synopsis,
      fee: Fee = Fee()
  ) = api.add(credentials, coAuthors, uri, price, expiration, synopsis, fee).blockingGet()

  fun createUpdateContentOperation(content: ContentObjectId, fee: Fee = Fee()) =
      api.createUpdateContentOperation(content, fee).blockingGet()

  fun createUpdateContentOperation(content: String, fee: Fee = Fee()) =
      api.createUpdateContentOperation(content, fee).blockingGet()

  fun update(
      credentials: Credentials,
      content: ContentObjectId,
      synopsis: Synopsis? = null,
      price: List<RegionalPrice>? = null,
      coAuthors: CoAuthors? = null,
      fee: Fee = Fee()
  ) = api.update(credentials, content, synopsis, price, coAuthors, fee).blockingGet()

  fun update(
      credentials: Credentials,
      content: String,
      synopsis: Synopsis? = null,
      price: List<RegionalPrice>? = null,
      coAuthors: CoAuthors? = null,
      fee: Fee = Fee()
  ) = api.update(credentials, content, synopsis, price, coAuthors, fee).blockingGet()
}
