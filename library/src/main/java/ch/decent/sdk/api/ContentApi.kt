package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
import io.reactivex.Single

class ContentApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get content by id.
   *
   * @param contentId object id of the content, 2.13.*
   *
   * @return a content if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getContent(contentId: ChainObject): Single<Content> = GetContentById(contentId).toRequest().map { it.single() }

  /**
   * Get content by uri.
   *
   * @param uri Uri of the content
   *
   * @return a content if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getContent(uri: String): Single<Content> = GetContentByUri(uri).toRequest()

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
  fun searchContent(
      term: String,
      order: SearchContentOrder = SearchContentOrder.CREATED_DESC,
      user: String = "",
      regionCode: String = Regions.ALL.code,
      type: String = contentType(ApplicationType.DECENT_CORE, CategoryType.NONE),
      startId: ChainObject = ObjectType.NULL_OBJECT.genericId,
      limit: Int = 100
  ): Single<List<Content>> = SearchContent(term, order, user, regionCode, type, startId, limit).toRequest()

  // todo untested no data
  /**
   * Get a list of accounts holding publishing manager status.
   *
   * @param lowerBound the name of the first account to return. If the named account does not exist, the list will start at the account that comes after lowerbound
   * @param limit the maximum number of accounts to return (max: 100)
   *
   * @return a list of publishing managers
   */
  fun listPublishingManagers(lowerBound: String, limit: Int = 100): Single<List<ChainObject>> = ListPublishingManagers(lowerBound, limit).toRequest()

  /**
   * Generate keys for new content submission.
   *
   * @param seeders list of seeder account IDs
   *
   * @return generated key and key parts
   */
  fun generateContentKeys(seeders: List<ChainObject>): Single<ContentKeys> = GenerateContentKeys(seeders).toRequest()

  /**
   * Restores encryption key from key parts stored in buying object.
   *
   * @param elGamalPrivate the private El Gamal key
   * @param purchaseId the purchase object
   *
   * @return AES encryption key
   */
  fun restoreEncryptionKey(elGamalPrivate: PubKey, purchaseId: ChainObject): Single<String> = RestoreEncryptionKey(elGamalPrivate, purchaseId).toRequest()

}