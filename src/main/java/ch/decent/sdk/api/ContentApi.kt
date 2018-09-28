package ch.decent.sdk.api

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Content
import io.reactivex.Single

interface ContentApi {

  /**
   * get content by id
   *
   * @param contentId object id of the content, 2.13.*
   *
   * @return a content if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getContent(contentId: ChainObject): Single<Content>

  /**
   * get content by uri
   *
   * @param uri Uri of the content
   *
   * @return a content if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getContent(uri: String): Single<Content>

}