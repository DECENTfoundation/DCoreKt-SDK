package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.types.UInt64
import ch.decent.sdk.utils.SIZE_32
import ch.decent.sdk.utils.decryptAesWithChecksum
import ch.decent.sdk.utils.secret
import ch.decent.sdk.utils.unhex
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import java.math.BigInteger
import java.nio.charset.Charset

data class Message(
    val operationId: MessagingObjectId,
    val timestamp: LocalDateTime,
    val sender: AccountObjectId,
    val senderAddress: Address?,
    val receiver: AccountObjectId,
    val receiverAddress: Address?,
    val message: String,
    val nonce: BigInteger = BigInteger.ZERO,
    val encrypted: Boolean = senderAddress != null && receiverAddress != null
) {

  @Suppress("TooGenericExceptionCaught")
  private fun decryptOrNull(keyPair: ECKeyPair, address: Address) = try {
    decryptAesWithChecksum(keyPair.secret(address, nonce), message.unhex())
  } catch (ex: Exception) {
    null
  }

  /**
   * Decrypt the message with given credentials
   *
   * @param credentials account credentials
   * @return decrypted message or unchanged message if unable to decrypt with credentials, check the [encrypted] field
   */
  fun decrypt(credentials: Credentials): Message {
    if (encrypted.not()) return this
    checkNotNull(senderAddress)
    checkNotNull(receiverAddress)
    val decrypted = decryptOrNull(credentials.keyPair, if (credentials.account == sender) receiverAddress else senderAddress)
    return if (decrypted == null) this
    else copy(message = decrypted, encrypted = false)
  }

  companion object {
    @JvmStatic
    fun create(response: MessageResponse) =
        response.receiversData.map {
          Message(response.id, response.created, response.sender, response.senderAddress, it.receiver, it.receiverAddress, it.data, it.nonce).run {
            if (encrypted.not()) copy(message = message.drop(SIZE_32 * 2).unhex().toString(Charset.forName("UTF-8")))
            else this
          }
        }
  }
}

data class MessageResponse(
    @SerializedName("id") val id: MessagingObjectId,
    @SerializedName("created") val created: LocalDateTime,
    @SerializedName("sender") val sender: AccountObjectId,
    @SerializedName("sender_pubkey") val senderAddress: Address?,
    @SerializedName("receivers_data") val receiversData: List<MessageReceiver>,
    @SerializedName("text") val text: String
)

data class MessageReceiver(
    @SerializedName("receiver") val receiver: AccountObjectId,
    @SerializedName("receiver_pubkey") val receiverAddress: Address?,
    @SerializedName("nonce") @UInt64 val nonce: BigInteger,
    @SerializedName("data") val data: String
)

data class MessagePayload(
    @SerializedName("from") val from: AccountObjectId,
    @SerializedName("receivers_data") val receiversData: List<MessagePayloadReceiver>,
    @SerializedName("pub_from") val fromAddress: Address? = null
) {

  /**
   * unencrypted message payload constructor
   */
  constructor(
      from: AccountObjectId,
      messages: List<MessageRequest>
  ) : this(from, messages.map { MessagePayloadReceiver(it.recipient, Memo(it.message).message) })
}

data class MessagePayloadReceiver(
    @SerializedName("to") val to: AccountObjectId,
    @SerializedName("data") val data: String,
    @SerializedName("pub_to") val toAddress: Address? = null,
    @SerializedName("nonce") @UInt64 val nonce: BigInteger? = null
)

data class MessageRequest(
    val recipient: AccountObjectId,
    val message: String
)
