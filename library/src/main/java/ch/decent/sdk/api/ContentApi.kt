@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.ApplicationType
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.CategoryType
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.CoAuthors
import ch.decent.sdk.model.Content
import ch.decent.sdk.model.ContentKeys
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.PubKey
import ch.decent.sdk.model.RegionalPrice
import ch.decent.sdk.model.Regions
import ch.decent.sdk.model.SearchContentOrder
import ch.decent.sdk.model.Synopsis
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.contentType
import ch.decent.sdk.model.operation.AddOrUpdateContentOperation
import ch.decent.sdk.model.operation.PurchaseContentOperation
import ch.decent.sdk.model.operation.RemoveContentOperation
import ch.decent.sdk.model.operation.TransferOperation
import ch.decent.sdk.net.model.request.GenerateContentKeys
import ch.decent.sdk.net.model.request.GetContentById
import ch.decent.sdk.net.model.request.GetContentByUri
import ch.decent.sdk.net.model.request.ListPublishingManagers
import ch.decent.sdk.net.model.request.RestoreEncryptionKey
import ch.decent.sdk.net.model.request.SearchContent
import io.reactivex.Single
import org.threeten.bp.LocalDateTime

class ContentApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Generate keys for new content submission.
   *
   * @param seeders list of seeder account IDs
   *
   * @return generated key and key parts
   */
  fun generateKeys(seeders: List<ChainObject>): Single<ContentKeys> = GenerateContentKeys(seeders).toRequest()

  /**
   * Get content by id.
   *
   * @param contentId object id of the content, 2.13.*
   *
   * @return a content if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun get(contentId: ChainObject): Single<Content> = GetContentById(listOf(contentId)).toRequest().map { it.single() }

  /**
   * Get contents by ids.
   *
   * @param contentId object ids of the contents, 2.13.*
   *
   * @return a content if found, empty list otherwise
   */
  fun getAll(contentId: List<ChainObject>): Single<List<Content>> = GetContentById(contentId).toRequest()
      .onErrorResumeNext { if (it is ObjectNotFoundException) Single.just(emptyList()) else Single.error(it) }

  /**
   * Get content by uri.
   *
   * @param uri Uri of the content
   *
   * @return a content if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun get(uri: String): Single<Content> = GetContentByUri(uri).toRequest()

  /**
   * Get a list of accounts holding publishing manager status.
   *
   * @param lowerBound the name of the first account to return. If the named account does not exist, the list will start at the account that comes after lowerbound
   * @param limit the maximum number of accounts to return (max: 100)
   *
   * @return a list of publishing managers
   */
  fun listAllPublishersRelative(lowerBound: String, limit: Int = 100): Single<List<ChainObject>> = ListPublishingManagers(lowerBound, limit).toRequest()

  /**
   * Restores encryption key from key parts stored in buying object.
   *
   * @param elGamalPrivate the private El Gamal key
   * @param purchaseId the purchase object
   *
   * @return AES encryption key
   */
  fun restoreEncryptionKey(elGamalPrivate: PubKey, purchaseId: ChainObject): Single<String> = RestoreEncryptionKey(elGamalPrivate, purchaseId).toRequest()

  /**
   * Search for term in contents (author, title and description).
   *
   * @param term search term
   * @param order ordering field. Available options are defined in [SearchContentOrder]
   * @param user content owner account name
   * @param regionCode two letter region code, defined in [Regions]
   * @param type the application and content type to be filtered, use [contentType] method
   * @param startId the id of content object to start searching from
   * @param limit maximum number of contents to fetch (must not exceed 100)
   *
   * @return the contents found
   */
  fun findAll(
      term: String,
      order: SearchContentOrder = SearchContentOrder.CREATED_DESC,
      user: String = "",
      regionCode: String = Regions.ALL.code,
      type: String = contentType(ApplicationType.DECENT_CORE, CategoryType.NONE),
      startId: ChainObject = ObjectType.NULL_OBJECT.genericId,
      limit: Int = 100
  ): Single<List<Content>> = SearchContent(term, order, user, regionCode, type, startId, limit).toRequest()

  /**
   * Create a purchase content operation.
   *
   * @param credentials account credentials
   * @param contentId object id of the content, 2.13.*
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   *
   * @return a purchase content operation
   */
  @JvmOverloads
  fun createPurchaseOperation(
      credentials: Credentials,
      contentId: ChainObject,
      fee: Fee = Fee()
  ): Single<PurchaseContentOperation> =
      get(contentId).map { PurchaseContentOperation(credentials, it, fee) }


  /**
   * Create a purchase content operation.
   *
   * @param credentials account credentials
   * @param uri uri of the content
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   *
   * @return a purchase content operation
   */
  @JvmOverloads
  fun createPurchaseOperation(
      credentials: Credentials,
      uri: String,
      fee: Fee = Fee()
  ): Single<PurchaseContentOperation> =
      get(uri).map { PurchaseContentOperation(credentials, it, fee) }


  /**
   * Purchase a content.
   *
   * @param credentials account credentials
   * @param contentId object id of the content, 2.13.*
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun purchase(
      credentials: Credentials,
      contentId: ChainObject,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createPurchaseOperation(credentials, contentId, fee).flatMap {
    api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
  }

  /**
   * Purchase a content.
   *
   * @param credentials account credentials
   * @param uri uri of the content
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun purchase(
      credentials: Credentials,
      uri: String,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createPurchaseOperation(credentials, uri, fee).flatMap {
    api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
  }

  /**
   * Create amount transfer operation of one asset to content. Amount is transferred to author and co-authors of the content, if they are specified.
   * Fees are paid by the "from" account.
   *
   * @param credentials account credentials
   * @param id content id
   * @param amount amount to send with asset type
   * @param memo optional unencrypted message
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   *
   * @return a transfer operation
   */
  @JvmOverloads
  fun createTransfer(
      credentials: Credentials,
      id: ChainObject,
      amount: AssetAmount,
      memo: String? = null,
      fee: Fee = Fee()
  ): Single<TransferOperation> = Single.just(TransferOperation(credentials.account, id, amount, memo?.let { Memo(it) }, fee))

  /**
   * Transfers an amount of one asset to content. Amount is transferred to author and co-authors of the content, if they are specified.
   * Fees are paid by the "from" account.
   *
   * @param credentials account credentials
   * @param id content id
   * @param amount amount to send with asset type
   * @param memo optional unencrypted message
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun transfer(
      credentials: Credentials,
      id: ChainObject,
      amount: AssetAmount,
      memo: String? = null,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> =
      createTransfer(credentials, id, amount, memo, fee).flatMap {
        api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
      }

  /**
   * Create remove content operation. Sets expiration to head block time, so the content cannot be purchased, but remains in database.
   *
   * @param content content id
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  fun createRemoveContentOperation(content: ChainObject, fee: Fee = Fee()): Single<RemoveContentOperation> =
      get(content).map { RemoveContentOperation(it.author, it.uri, fee) }

  /**
   * Create remove content operation. Sets expiration to head block time, so the content cannot be purchased, but remains in database.
   *
   * @param content content uri
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  fun createRemoveContentOperation(content: String, fee: Fee = Fee()): Single<RemoveContentOperation> =
      get(content).map { RemoveContentOperation(it.author, it.uri, fee) }

  /**
   * Remove content. Sets expiration to head block time, so the content cannot be purchased, but remains in database.
   *
   * @param credentials author credentials
   * @param content content id
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  fun remove(credentials: Credentials, content: ChainObject, fee: Fee = Fee()): Single<TransactionConfirmation> =
      createRemoveContentOperation(content, fee).flatMap {
        api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
      }

  /**
   * Remove content. Sets expiration to head block time, so the content cannot be purchased, but remains in database.
   *
   * @param credentials author credentials
   * @param content content uri
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  fun remove(credentials: Credentials, content: String, fee: Fee = Fee()): Single<TransactionConfirmation> =
      createRemoveContentOperation(content, fee).flatMap {
        api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
      }

  /**
   * Create request to submit content operation.
   *
   * @param author author of the content. If co-authors is not filled, this account will receive full payout
   * @param coAuthors if map is not empty, payout will be split - the parameter maps co-authors
   * to basis points split, e.g. author1:9000 (bp), author2:1000 (bp),
   * if author is omitted from this map, it is assigned 10000 (bp_total) minus sum of splits
   * @param uri URI where the content can be found
   * @param price list of regional prices
   * @param expiration content expiration time
   * @param synopsis JSON formatted structure containing content information
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  fun createAddContentOperation(
      author: ChainObject,
      coAuthors: CoAuthors,
      uri: String,
      price: List<RegionalPrice>,
      expiration: LocalDateTime,
      synopsis: Synopsis,
      fee: Fee = Fee()
  ): Single<AddOrUpdateContentOperation> = Single.just(AddOrUpdateContentOperation(
      author = author,
      coAuthors = coAuthors,
      uri = uri,
      price = price,
      expiration = expiration,
      synopsis = synopsis.json,
      fee = fee
  ))

  /**
   * Add content.
   *
   * @param credentials author credentials. If co-authors is not filled, this account will receive full payout
   * @param coAuthors if map is not empty, payout will be split - the parameter maps co-authors
   * to basis points split, e.g. author1:9000 (bp), author2:1000 (bp),
   * if author is omitted from this map, it is assigned 10000 (bp_total) minus sum of splits
   * @param uri URI where the content can be found
   * @param price list of regional prices
   * @param expiration content expiration time
   * @param synopsis JSON formatted structure containing content information
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  fun add(
      credentials: Credentials,
      coAuthors: CoAuthors,
      uri: String,
      price: List<RegionalPrice>,
      expiration: LocalDateTime,
      synopsis: Synopsis,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createAddContentOperation(credentials.account, coAuthors, uri, price, expiration, synopsis, fee).flatMap {
    api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
  }

  private fun createUpdateContentOperation(
      old: Content,
      synopsis: (old: Synopsis) -> Synopsis = { it },
      price: (old: List<RegionalPrice>) -> List<RegionalPrice> = { it },
      coAuthors: (old: CoAuthors) -> CoAuthors = { it },
      fee: Fee = Fee()
  ): AddOrUpdateContentOperation = AddOrUpdateContentOperation(
      old.size,
      old.author,
      coAuthors(old.coAuthors),
      old.uri,
      old.quorum,
      price(old.price.regionalPrice),
      old.hash,
      old.seederPrice.keys.toList(),
      old.keyParts.values.toList(),
      old.expiration,
      old.publishingFeeEscrow,
      synopsis(Synopsis.fromJson(old.synopsis)).json,
      old.custodyData,
      fee
  )

  /**
   * Create request to update content operation. Update parameters are functions that have current values as arguments.
   *
   * @param content content id
   * @param synopsis JSON formatted structure containing content information
   * @param price list of regional prices
   * @param coAuthors if map is not empty, payout will be split - the parameter maps co-authors
   * to basis points split, e.g. author1:9000 (bp), author2:1000 (bp),
   * if author is omitted from this map, it is assigned 10000 (bp_total) minus sum of splits
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  private fun createUpdateContentOperation(
      content: ChainObject,
      synopsis: (old: Synopsis) -> Synopsis = { it },
      price: (old: List<RegionalPrice>) -> List<RegionalPrice> = { it },
      coAuthors: (old: CoAuthors) -> CoAuthors = { it },
      fee: Fee = Fee()
  ): Single<AddOrUpdateContentOperation> = get(content).map { createUpdateContentOperation(it, synopsis, price, coAuthors, fee) }

  /**
   * Create request to update content operation. Update parameters are functions that have current values as arguments.
   *
   * @param content content uri
   * @param synopsis JSON formatted structure containing content information
   * @param price list of regional prices
   * @param coAuthors if map is not empty, payout will be split - the parameter maps co-authors
   * to basis points split, e.g. author1:9000 (bp), author2:1000 (bp),
   * if author is omitted from this map, it is assigned 10000 (bp_total) minus sum of splits
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  private fun createUpdateContentOperation(
      content: String,
      synopsis: (old: Synopsis) -> Synopsis = { it },
      price: (old: List<RegionalPrice>) -> List<RegionalPrice> = { it },
      coAuthors: (old: CoAuthors) -> CoAuthors = { it },
      fee: Fee = Fee()
  ): Single<AddOrUpdateContentOperation> = get(content).map { createUpdateContentOperation(it, synopsis, price, coAuthors, fee) }


  /**
   * Update content. Update parameters are functions that have current values as arguments.
   *
   * @param credentials author credentials
   * @param content content id
   * @param synopsis JSON formatted structure containing content information
   * @param price list of regional prices
   * @param coAuthors if map is not empty, payout will be split - the parameter maps co-authors
   * to basis points split, e.g. author1:9000 (bp), author2:1000 (bp),
   * if author is omitted from this map, it is assigned 10000 (bp_total) minus sum of splits
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  fun update(
      credentials: Credentials,
      content: ChainObject,
      synopsis: (old: Synopsis) -> Synopsis = { it },
      price: (old: List<RegionalPrice>) -> List<RegionalPrice> = { it },
      coAuthors: (old: CoAuthors) -> CoAuthors = { it },
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createUpdateContentOperation(content, synopsis, price, coAuthors, fee).flatMap {
    api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
  }

  /**
   * Update content. Update parameters are functions that have current values as arguments.
   *
   * @param credentials author credentials
   * @param content content uri
   * @param synopsis JSON formatted structure containing content information
   * @param price list of regional prices
   * @param coAuthors if map is not empty, payout will be split - the parameter maps co-authors
   * to basis points split, e.g. author1:9000 (bp), author2:1000 (bp),
   * if author is omitted from this map, it is assigned 10000 (bp_total) minus sum of splits
   * @param fee {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
   * When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  fun update(
      credentials: Credentials,
      content: String,
      synopsis: (old: Synopsis) -> Synopsis = { it },
      price: (old: List<RegionalPrice>) -> List<RegionalPrice> = { it },
      coAuthors: (old: CoAuthors) -> CoAuthors = { it },
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createUpdateContentOperation(content, synopsis, price, coAuthors, fee).flatMap {
    api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
  }
}
