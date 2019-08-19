package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.MessageResponse
import ch.decent.sdk.model.MessagingObjectId
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetMessages(
    messages: List<MessagingObjectId>
) : BaseRequest<List<MessageResponse>>(
    ApiGroup.MESSAGING,
    "get_messages",
    TypeToken.getParameterized(List::class.java, MessageResponse::class.java).type,
    listOf(messages)
)
