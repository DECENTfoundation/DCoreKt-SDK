package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Content
import ch.decent.sdk.net.model.request.GetBuyingByUri
import ch.decent.sdk.net.model.request.GetContentById
import ch.decent.sdk.net.model.request.GetContentByUri
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

}