package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Content
import ch.decent.sdk.net.model.ApiGroup

class GetContentByUri(
    uri: String
) : BaseRequest<Content>(
    ApiGroup.DATABASE,
    "get_content",
    Content::class.java,
    listOf(uri)
)