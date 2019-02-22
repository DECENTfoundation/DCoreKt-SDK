package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.GetMessageObjects
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class MessagingApi internal constructor(api: DCoreApi) : BaseApi(api) {

  fun getAll(sender: ChainObject? = null, receiver: ChainObject? = null, maxCount: Int = 1000) =
      GetMessageObjects(sender, receiver, maxCount).toRequest()

  fun createMessageOperation(
      credentials: Credentials,
      to: ChainObject,
      message: String
  ) = Single.zip(
      api.accountApi.get(credentials.account),
      api.accountApi.get(to),
      BiFunction { sender: Account, recipient: Account -> sender to recipient }
  ).map { (sender, recipient) ->
    val msg = Memo(message, credentials, recipient)
    val payloadReceiver = MessagePayloadReceiver(recipient.id, recipient.options.memoKey, msg.nonce, msg.message)
    val payload = MessagePayload(sender.id, sender.options.memoKey, listOf(payloadReceiver))
    SendMessageOperation(api.core.gson.toJson(payload), credentials.account)
  }

  fun createMessageOperation(
      credentials: Credentials,
      messages: List<Pair<ChainObject, String>>
  ) = Single.zip(
      api.accountApi.get(credentials.account),
      Single.merge(messages.map { api.accountApi.get(it.first) }).toList(),
      BiFunction { sender: Account, recipients: List<Account> -> sender to recipients }
  ).map { (sender, recipients) ->
    val payloadReceivers = recipients.mapIndexed { idx, recipient ->
      val msg = Memo(messages[idx].second, credentials, recipient)
      MessagePayloadReceiver(recipient.id, recipient.options.memoKey, msg.nonce, msg.message)
    }
    val data = MessagePayload(sender.id, sender.options.memoKey, payloadReceivers)
    SendMessageOperation(api.core.gson.toJson(data), credentials.account)
  }
}
