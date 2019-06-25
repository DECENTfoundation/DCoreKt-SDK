package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.types.UInt64
import ch.decent.sdk.utils.SIZE_32
import ch.decent.sdk.utils.decryptAesWithChecksum
import ch.decent.sdk.utils.encryptAes
import ch.decent.sdk.utils.generateNonce
import ch.decent.sdk.utils.hash256
import ch.decent.sdk.utils.hex
import ch.decent.sdk.utils.secret
import ch.decent.sdk.utils.unhex
import com.google.gson.annotations.SerializedName
import java.math.BigInteger
import java.nio.charset.Charset

class Memo {
  @SerializedName("from") val from: Address?
  @SerializedName("to") val to: Address?
  @SerializedName("message") val message: String
  @SerializedName("nonce") @UInt64 val nonce: BigInteger

  /**
   * Create Memo object with unencrypted message
   *
   * @param message a message to send
   */
  constructor(message: String) {
    this.message = (ByteArray(SIZE_32) { 0 } + message.toByteArray()).hex()
    this.nonce = BigInteger.ZERO
    this.from = null
    this.to = null
  }

  /**
   * Create Memo object with encrypted message
   *
   * @param message a message to send
   * @param credentials sender credentials
   * @param recipient receiver account
   */
  constructor(message: String, credentials: Credentials, recipient: Account) :
      this(message, credentials.keyPair, recipient.options.memoKey)

  /**
   * Create Memo object with encrypted message
   *
   * @param message a message to send
   * @param keyPair sender keys, use [ch.decent.sdk.crypto.Credentials.keyPair]
   * @param recipient receiver public key, use address from [Account.active] keys
   * @param nonce unique positive number
   */
  constructor(message: String, keyPair: ECKeyPair, recipient: Address, nonce: BigInteger = generateNonce()) {
    require(nonce.signum() > 0) { "nonce must be a positive number" }
    this.nonce = nonce
    this.from = Address(keyPair.public)
    this.to = recipient
    val checksummed = message.toByteArray().hash256().copyOfRange(0, SIZE_32) + message.toByteArray()
    val secret = keyPair.secret(recipient, this.nonce)
    this.message = encryptAes(secret, checksummed).hex()
  }

  @Suppress("TooGenericExceptionCaught")
  private fun decryptOrEmpty(secret: ByteArray) = try {
    decryptAesWithChecksum(secret, message.unhex())
  } catch (ex: Exception) {
    ""
  }

  fun decrypt(keyPair: ECKeyPair): String {
    return if (from == null || to == null) {
      message.drop(SIZE_32 * 2).unhex().toString(Charset.forName("UTF-8"))
    } else if (from.publicKey == keyPair.public) {
      decryptOrEmpty(keyPair.secret(to, nonce))
    } else if (to.publicKey == keyPair.public) {
      decryptOrEmpty(keyPair.secret(from, nonce))
    } else {
      ""
    }
  }

  override fun toString(): String {
    return "Memo(from=$from, to=$to, message='$message', nonce=$nonce)"
  }
}
