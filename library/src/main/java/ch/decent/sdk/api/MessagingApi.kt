@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.Account
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.Message
import ch.decent.sdk.model.MessagePayload
import ch.decent.sdk.model.MessagePayloadReceiver
import ch.decent.sdk.model.MessageResponse
import ch.decent.sdk.model.SendMessageOperation
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.net.model.request.GetMessageObjects
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class MessagingApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get all message operations
   *
   * @param sender filter by sender account id
   * @param receiver filter by receiver account id
   * @param maxCount max items to return
   *
   * @return list of message operation responses
   */
  fun getAllOperations(sender: ChainObject? = null, receiver: ChainObject? = null, maxCount: Int = 1000): Single<List<MessageResponse>> =
      GetMessageObjects(sender, receiver, maxCount).toRequest()

  /**
   * Get all messages
   *
   * @param sender filter by sender account id
   * @param receiver filter by receiver account id
   * @param maxCount max items to return
   *
   * @return list of messages
   */
  fun getAll(sender: ChainObject? = null, receiver: ChainObject? = null, maxCount: Int = 1000): Single<List<Message>> =
      GetMessageObjects(sender, receiver, maxCount).toRequest()
          .map { it.map { Message.create(it) }.flatten() }

  /**
   * Get all messages and decrypt
   *
   * @param credentials account credentials used for decryption, must be either sender's or receiver's
   * @param sender filter by sender account id
   * @param receiver filter by receiver account id
   * @param maxCount max items to return
   *
   * @return list of messages
   */
  fun getAllDecrypted(
      credentials: Credentials,
      sender: ChainObject? = null,
      receiver: ChainObject? = null,
      maxCount: Int = 1000
  ): Single<List<Message>> =
      require(sender == credentials.account || receiver == credentials.account)
      { "credentials account id must match either sender id or receiver id " }.let {
        GetMessageObjects(sender, receiver, maxCount).toRequest()
            .map { it.map { Message.create(it) }.flatten() }
            .map { it.map { it.decrypt(credentials) } }
      }

  /**
   * Get all messages for sender and decrypt
   *
   * @param credentials sender account credentials with decryption keys
   * @param maxCount max items to return
   *
   * @return list of messages
   */
  fun getAllDecryptedForSender(credentials: Credentials, maxCount: Int = 1000): Single<List<Message>> =
      getAllDecrypted(credentials, sender = credentials.account, maxCount = maxCount)

  /**
   * Get all messages for receiver and decrypt
   *
   * @param credentials receiver account credentials with decryption keys
   * @param maxCount max items to return
   *
   * @return list of messages
   */
  fun getAllDecryptedForReceiver(credentials: Credentials, maxCount: Int = 1000): Single<List<Message>> =
      getAllDecrypted(credentials, receiver = credentials.account, maxCount = maxCount)

  /**
   * Create message operation, send messages to multiple receivers
   *
   * @param credentials sender account credentials
   * @param messages pairs of receiver account id and message
   *
   * @return send message operation
   */
  fun createMessageOperation(
      credentials: Credentials,
      messages: List<Pair<ChainObject, String>>
  ): Single<SendMessageOperation> = Single.zip(
      api.accountApi.get(credentials.account),
      Single.merge(messages.map { api.accountApi.get(it.first) }).toList(),
      BiFunction { sender: Account, recipients: List<Account> -> sender to recipients }
  ).map { (sender, recipients) ->
    val payloadReceivers = recipients.mapIndexed { idx, recipient ->
      val msg = Memo(messages[idx].second, credentials, recipient)
      MessagePayloadReceiver(recipient.id, msg.message, recipient.options.memoKey, msg.nonce)
    }
    val data = MessagePayload(sender.id, payloadReceivers, sender.options.memoKey)
    SendMessageOperation(api.core.gson.toJson(data), credentials.account)
  }

  /**
   * Create message operation, send messages to multiple receivers, unencrypted
   *
   * @param credentials sender account credentials
   * @param messages a list of pairs of receiver account id and message
   *
   * @return send message operation
   */
  fun createMessageOperationUnencrypted(
      credentials: Credentials,
      messages: List<Pair<ChainObject, String>>
  ): Single<SendMessageOperation> = Single.just(SendMessageOperation(api.core.gson.toJson(MessagePayload(credentials.account, messages)), credentials.account))

  /**
   * Send messages to receivers
   *
   * @param credentials sender account credentials
   * @param messages pairs of receiver account id and message
   *
   * @return a transaction confirmation
   */
  fun send(
      credentials: Credentials,
      messages: List<Pair<ChainObject, String>>
  ): Single<TransactionConfirmation> = createMessageOperation(credentials, messages).flatMap {
    api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
  }

  /**
   * Send unencrypted messages to receivers
   *
   * @param credentials sender account credentials
   * @param messages a list of pairs of receiver account id and message
   *
   * @return a transaction confirmation
   */
  fun sendUnencrypted(
      credentials: Credentials,
      messages: List<Pair<ChainObject, String>>
  ): Single<TransactionConfirmation> = createMessageOperationUnencrypted(credentials, messages).flatMap {
    api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
  }
}
