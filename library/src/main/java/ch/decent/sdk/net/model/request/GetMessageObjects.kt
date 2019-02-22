package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Message
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetMessageObjects(
    sender: ChainObject?,
    receiver: ChainObject?,
    maxCount: Int
) : BaseRequest<List<Message>>(
    ApiGroup.MESSAGING,
    "get_message_objects",
    TypeToken.getParameterized(List::class.java, Message::class.java).type,
    listOf(sender, receiver, maxCount)
)