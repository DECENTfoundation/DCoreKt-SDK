@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api.rx

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.Account
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.Message
import ch.decent.sdk.model.MessagePayload
import ch.decent.sdk.model.MessagingObjectId
import ch.decent.sdk.model.MessagePayloadReceiver
import ch.decent.sdk.model.MessageRequest
import ch.decent.sdk.model.MessageResponse
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.operation.SendMessageOperation
import ch.decent.sdk.net.model.request.GetMessageObjects
import ch.decent.sdk.net.model.request.GetMessages
import ch.decent.sdk.utils.REQ_LIMIT_MAX_1K
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class MessagingApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Find all message operations
   *
   * @param sender filter by sender account id
   * @param receiver filter by receiver account id
   * @param maxCount max items to return
   *
   * @return list of message operation responses
   */
  @JvmOverloads
  fun findAllOperations(sender: AccountObjectId? = null, receiver: AccountObjectId? = null, maxCount: Int = REQ_LIMIT_MAX_1K): Single<List<MessageResponse>> =
      GetMessageObjects(sender, receiver, maxCount).toRequest()

  /**
   * Find all messages
   *
   * @param sender filter by sender account id
   * @param receiver filter by receiver account id
   * @param maxCount max items to return
   *
   * @return list of messages
   */
  @JvmOverloads
  fun findAll(sender: AccountObjectId? = null, receiver: AccountObjectId? = null, maxCount: Int = REQ_LIMIT_MAX_1K): Single<List<Message>> =
      GetMessageObjects(sender, receiver, maxCount).toRequest()
          .map { it.map { Message.create(it) }.flatten() }

  /**
   * Find all messages and decrypt
   *
   * @param credentials account credentials used for decryption, must be either sender's or receiver's
   * @param sender filter by sender account id
   * @param receiver filter by receiver account id
   * @param maxCount max items to return
   *
   * @return list of messages
   */
  @JvmOverloads
  fun findAllDecrypted(
      credentials: Credentials,
      sender: AccountObjectId? = null,
      receiver: AccountObjectId? = null,
      maxCount: Int = REQ_LIMIT_MAX_1K
  ): Single<List<Message>> =
      require(sender == credentials.account || receiver == credentials.account)
      { "credentials account id must match either sender id or receiver id " }.let {
        GetMessageObjects(sender, receiver, maxCount).toRequest()
            .map { it.map { Message.create(it) }.flatten() }
            .map { it.map { it.decrypt(credentials) } }
      }

  /**
   * Find all messages for sender and decrypt
   *
   * @param credentials sender account credentials with decryption keys
   * @param maxCount max items to return
   *
   * @return list of messages
   */
  @JvmOverloads
  fun findAllDecryptedForSender(credentials: Credentials, maxCount: Int = REQ_LIMIT_MAX_1K): Single<List<Message>> =
      findAllDecrypted(credentials, sender = credentials.account, maxCount = maxCount)

  /**
   * Find all messages for receiver and decrypt
   *
   * @param credentials receiver account credentials with decryption keys
   * @param maxCount max items to return
   *
   * @return list of messages
   */
  @JvmOverloads
  fun findAllDecryptedForReceiver(credentials: Credentials, maxCount: Int = REQ_LIMIT_MAX_1K): Single<List<Message>> =
      findAllDecrypted(credentials, receiver = credentials.account, maxCount = maxCount)

  /**
   * Create message operation, send messages to multiple receivers
   *
   * @param credentials sender account credentials
   * @param messages pairs of receiver account id and message
   *
   * @return send message operation
   */
  @JvmOverloads
  fun createMessageOperation(
      credentials: Credentials,
      messages: List<MessageRequest>,
      fee: Fee = Fee()
  ): Single<SendMessageOperation> = Single.zip(
      api.accountApi.get(credentials.account),
      Single.merge(messages.map { api.accountApi.get(it.recipient) }).toList(),
      BiFunction { sender: Account, recipients: List<Account> -> sender to recipients }
  ).map { (sender, recipients) ->
    val payloadReceivers = recipients.mapIndexed { idx, recipient ->
      val msg = Memo(messages[idx].message, credentials, recipient)
      MessagePayloadReceiver(recipient.id, msg.message, recipient.options.memoKey, msg.nonce)
    }
    val data = MessagePayload(sender.id, payloadReceivers, sender.options.memoKey)
    SendMessageOperation(api.gson, data, credentials.account, fee)
  }

  /**
   * Create message operation, send messages to multiple receivers, unencrypted
   *
   * @param credentials sender account credentials
   * @param messages a list of pairs of receiver account id and message
   *
   * @return send message operation
   */
  @JvmOverloads
  fun createMessageOperationUnencrypted(
      credentials: Credentials,
      messages: List<MessageRequest>,
      fee: Fee = Fee()
  ): Single<SendMessageOperation> = Single.just(
      SendMessageOperation(api.gson, MessagePayload(credentials.account, messages), credentials.account, fee)
  )

  /**
   * Send messages to receivers
   *
   * @param credentials sender account credentials
   * @param messages pairs of receiver account id and message
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun send(
      credentials: Credentials,
      messages: List<MessageRequest>,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createMessageOperation(credentials, messages, fee).flatMap {
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
  @JvmOverloads
  fun sendUnencrypted(
      credentials: Credentials,
      messages: List<MessageRequest>,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createMessageOperationUnencrypted(credentials, messages, fee).flatMap {
    api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
  }

  /**
   * Get messages by ids
   *
   * @param ids a list of messages ids
   *
   * @return list of messages
   */
  fun getAll(ids: List<MessagingObjectId>): Single<List<Message>> =
      GetMessages(ids).toRequest()
          .map { it.map { Message.create(it) }.flatten() }

  /**
   * Get message by id
   *
   * @param id message id
   *
   * @return message
   */
  fun get(id: MessagingObjectId): Single<Message> = getAll(listOf(id)).map { it.single() }
}
