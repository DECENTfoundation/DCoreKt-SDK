package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Content
import ch.decent.sdk.model.ObjectType
import com.google.gson.reflect.TypeToken

internal class GetContentById(
    contentId: ChainObject
) : GetObjects<List<Content>>(
    listOf(contentId),
    TypeToken.getParameterized(List::class.java, Content::class.java).type
) {

  init {
    require(contentId.objectType == ObjectType.CONTENT_OBJECT) { "not a valid content object id" }
  }
}