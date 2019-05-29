package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.MessageResponse
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetMessageObjects(
    sender: AccountObjectId?,
    receiver: AccountObjectId?,
    maxCount: Int
) : BaseRequest<List<MessageResponse>>(
    ApiGroup.MESSAGING,
    "get_message_objects",
    TypeToken.getParameterized(List::class.java, MessageResponse::class.java).type,
    listOf(sender, receiver, maxCount)
)
