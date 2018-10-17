package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
import io.reactivex.Single

class ContentApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * get content by id
   *
   * @param contentId object id of the content, 2.13.*
   *
   * @return a content if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getContent(contentId: ChainObject): Single<Content> = GetContentById(contentId).toRequest().map { it.single() }

  /**
   * get content by uri
   *
   * @param uri Uri of the content
   *
   * @return a content if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getContent(uri: String): Single<Content> = GetContentByUri(uri).toRequest()

  fun searchContent(
      term: String,
      order: SearchContentOrder = SearchContentOrder.CREATED_DESC,
      user: String = "",
      regionCode: String = "",
      type: String = "1.0.0",
      startId: ChainObject = ObjectType.NULL_OBJECT.genericId,
      limit: Int = 100
  ): Single<List<Content>> = SearchContent(term, order, user, regionCode, type, startId, limit).toRequest()

  // todo untested no data
  fun listPublishingManagers(lowerBound: String, limit: Int = 100): Single<List<ChainObject>> = ListPublishingManagers(lowerBound, limit).toRequest()

  fun generateContentKeys(seeders: List<ChainObject>): Single<ContentKeys> = GenerateContentKeys(seeders).toRequest()

  fun restoreEncryptionKey(elGamalPrivate: PubKey, purchaseId: ChainObject): Single<String> = RestoreEncryptionKey(elGamalPrivate, purchaseId).toRequest()

}