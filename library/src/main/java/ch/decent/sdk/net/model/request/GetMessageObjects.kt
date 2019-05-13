package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.MessageResponse
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetMessageObjects(
    sender: ChainObject?,
    receiver: ChainObject?,
    maxCount: Int
) : BaseRequest<List<MessageResponse>>(
    ApiGroup.MESSAGING,
    "get_message_objects",
    TypeToken.getParameterized(List::class.java, MessageResponse::class.java).type,
    listOf(sender, receiver, maxCount)
) {
  init {
    require(sender?.objectType?.equals(ObjectType.ACCOUNT_OBJECT) ?: true) { "not a valid account object id" }
    require(receiver?.objectType?.equals(ObjectType.ACCOUNT_OBJECT) ?: true) { "not a valid account object id" }
  }
}
