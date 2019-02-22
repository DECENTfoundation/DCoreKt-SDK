package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.utils.decryptAesWithChecksum
import ch.decent.sdk.utils.secret
import ch.decent.sdk.utils.unhex
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import java.math.BigInteger

data class Message(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("created") val created: LocalDateTime,
    @SerializedName("sender") val sender: ChainObject,
    @SerializedName("sender_pubkey") val senderAddress: Address,
    @SerializedName("receivers_data") val receiversData: List<MessageReceiver>,
    @SerializedName("text") val text: String
) {

  private fun decryptOrEmpty(keyPair: ECKeyPair, payload: MessageReceiver, address: Address = payload.receiverAddress) = try {
    decryptAesWithChecksum(keyPair.secret(address, payload.nonce), payload.data.unhex())
  } catch (ex: Exception) {
    ""
  }

  /**
   * Decrypt messages, sender can decrypt all sent messages, recipient only message addressed to him
   *
   * @param credentials account credentials
   *
   * @return list of pairs of receiver id to decrypted message string
   */
  fun decrypt(credentials: Credentials): List<Pair<ChainObject, String>> =
      when {
        credentials.account == sender -> receiversData.map { it.receiver to decryptOrEmpty(credentials.keyPair, it) }
        credentials.account != sender -> receiversData
            .filter { it.receiver == credentials.account }
            .map { it.receiver to decryptOrEmpty(credentials.keyPair, it, senderAddress) }
        else -> emptyList()
      }
}

data class MessageReceiver(
    @SerializedName("receiver") val receiver: ChainObject,
    @SerializedName("receiver_pubkey") val receiverAddress: Address,
    @SerializedName("nonce") val nonce: BigInteger,
    @SerializedName("data") val data: String
)

data class MessagePayload(
    @SerializedName("from") val from: ChainObject,
    @SerializedName("pub_from") val fromAddress: Address,
    @SerializedName("receivers_data") val receiversData: List<MessagePayloadReceiver>
)

data class MessagePayloadReceiver(
    @SerializedName("to") val to: ChainObject,
    @SerializedName("pub_to") val toAddress: Address,
    @SerializedName("nonce") val nonce: BigInteger,
    @SerializedName("data") val data: String
)
