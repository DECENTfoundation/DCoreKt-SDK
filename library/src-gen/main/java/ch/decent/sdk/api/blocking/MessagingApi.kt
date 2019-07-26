@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.MessageRequest
import ch.decent.sdk.utils.REQ_LIMIT_MAX_1K
import kotlin.Int
import kotlin.Suppress
import kotlin.collections.List

class MessagingApi internal constructor(
  private val api: ch.decent.sdk.api.MessagingApi
) {
  fun getAllOperations(
    sender: AccountObjectId? = null,
    receiver: AccountObjectId? = null,
    maxCount: Int = REQ_LIMIT_MAX_1K
  ) = api.getAllOperations(sender, receiver, maxCount).blockingGet()
  fun getAll(
    sender: AccountObjectId? = null,
    receiver: AccountObjectId? = null,
    maxCount: Int = REQ_LIMIT_MAX_1K
  ) = api.getAll(sender, receiver, maxCount).blockingGet()
  fun getAllDecrypted(
    credentials: Credentials,
    sender: AccountObjectId? = null,
    receiver: AccountObjectId? = null,
    maxCount: Int = REQ_LIMIT_MAX_1K
  ) = api.getAllDecrypted(credentials, sender, receiver, maxCount).blockingGet()
  fun getAllDecryptedForSender(credentials: Credentials, maxCount: Int = REQ_LIMIT_MAX_1K) =
      api.getAllDecryptedForSender(credentials, maxCount).blockingGet()
  fun getAllDecryptedForReceiver(credentials: Credentials, maxCount: Int = REQ_LIMIT_MAX_1K) =
      api.getAllDecryptedForReceiver(credentials, maxCount).blockingGet()
  fun createMessageOperation(
    credentials: Credentials,
    messages: List<MessageRequest>,
    fee: Fee = Fee()
  ) = api.createMessageOperation(credentials, messages, fee).blockingGet()
  fun createMessageOperationUnencrypted(
    credentials: Credentials,
    messages: List<MessageRequest>,
    fee: Fee = Fee()
  ) = api.createMessageOperationUnencrypted(credentials, messages, fee).blockingGet()
  fun send(
    credentials: Credentials,
    messages: List<MessageRequest>,
    fee: Fee = Fee()
  ) = api.send(credentials, messages, fee).blockingGet()
  fun sendUnencrypted(
    credentials: Credentials,
    messages: List<MessageRequest>,
    fee: Fee = Fee()
  ) = api.sendUnencrypted(credentials, messages, fee).blockingGet()}
