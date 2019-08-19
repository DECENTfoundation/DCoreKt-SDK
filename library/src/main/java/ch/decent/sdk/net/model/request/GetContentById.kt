package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Content
import ch.decent.sdk.model.ContentObjectId
import com.google.gson.reflect.TypeToken

internal class GetContentById(
    contentId: List<ContentObjectId>
) : GetObjects<List<Content>>(
    contentId,
    TypeToken.getParameterized(List::class.java, Content::class.java).type
)
