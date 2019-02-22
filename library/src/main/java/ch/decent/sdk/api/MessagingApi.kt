package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.GetMessageObjects
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class MessagingApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get all messages
   *
   * @param sender filter by sender account id
   * @param receiver filter by receiver account id
   * @param maxCount max items to return
   *
   * @return list of messages
   */
  fun getAll(sender: ChainObject? = null, receiver: ChainObject? = null, maxCount: Int = 1000) =
      GetMessageObjects(sender, receiver, maxCount).toRequest()


  /**
   * Create message operation, send a message to one receiver
   *
   * @param credentials sender account credentials
   * @param to receiver address
   * @param message a message to send
   *
   * @return send message operation
   */
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

  /**
   * Create message operation, send messages to multiple receivers
   *
   * @param credentials sender account credentials
   * @param messages a list of pairs of receiver account id and message
   *
   * @return send message operation
   */
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
