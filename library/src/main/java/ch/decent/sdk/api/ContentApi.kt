@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.ApplicationType
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.BaseOperation
import ch.decent.sdk.model.CategoryType
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Content
import ch.decent.sdk.model.ContentKeys
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.PubKey
import ch.decent.sdk.model.PurchaseContentOperation
import ch.decent.sdk.model.Regions
import ch.decent.sdk.model.SearchContentOrder
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.TransferOperation
import ch.decent.sdk.model.contentType
import ch.decent.sdk.net.model.request.GenerateContentKeys
import ch.decent.sdk.net.model.request.GetContentById
import ch.decent.sdk.net.model.request.GetContentByUri
import ch.decent.sdk.net.model.request.ListPublishingManagers
import ch.decent.sdk.net.model.request.RestoreEncryptionKey
import ch.decent.sdk.net.model.request.SearchContent
import io.reactivex.Single

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
   * @return a purchase content operation
   */
  fun createPurchaseOperation(
      credentials: Credentials,
      contentId: ChainObject
  ): Single<PurchaseContentOperation> =
      get(contentId).map { PurchaseContentOperation(credentials, it) }


  /**
   * Create a purchase content operation.
   *
   * @param credentials account credentials
   * @param uri uri of the content
   * @return a purchase content operation
   */
  fun createPurchaseOperation(
      credentials: Credentials,
      uri: String
  ): Single<PurchaseContentOperation> =
      get(uri).map { PurchaseContentOperation(credentials, it) }


  /**
   * Purchase a content.
   *
   * @param credentials account credentials
   * @param contentId object id of the content, 2.13.*
   * @return a transaction confirmation
   */
  fun purchase(
      credentials: Credentials,
      contentId: ChainObject
  ): Single<TransactionConfirmation> = createPurchaseOperation(credentials, contentId).flatMap {
    api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
  }

  /**
   * Purchase a content.
   *
   * @param credentials account credentials
   * @param uri uri of the content
   * @return a transaction confirmation
   */
  fun purchase(
      credentials: Credentials,
      uri: String
  ): Single<TransactionConfirmation> = createPurchaseOperation(credentials, uri).flatMap {
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
   * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
   *
   * @return a transfer operation
   */
  @JvmOverloads
  fun createTransfer(
      credentials: Credentials,
      id: ChainObject,
      amount: AssetAmount,
      memo: String? = null,
      fee: AssetAmount = BaseOperation.FEE_UNSET
  ): Single<TransferOperation> = Single.just(TransferOperation(credentials.account, id, amount, memo?.let { Memo(it) }, fee))

  /**
   * Transfers an amount of one asset to content. Amount is transferred to author and co-authors of the content, if they are specified.
   * Fees are paid by the "from" account.
   *
   * @param credentials account credentials
   * @param id content id
   * @param amount amount to send with asset type
   * @param memo optional unencrypted message
   * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun transfer(
      credentials: Credentials,
      id: ChainObject,
      amount: AssetAmount,
      memo: String? = null,
      fee: AssetAmount = BaseOperation.FEE_UNSET
  ): Single<TransactionConfirmation> =
      createTransfer(credentials, id, amount, memo, fee).flatMap {
        api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
      }

}
