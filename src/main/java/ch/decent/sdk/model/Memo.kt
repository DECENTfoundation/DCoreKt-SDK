package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.Sha256Hash
import ch.decent.sdk.net.model.ByteSerializable
import ch.decent.sdk.utils.*
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName
import java.math.BigInteger
import java.nio.charset.Charset

class Memo : ByteSerializable {
  @SerializedName("from") val from: Address?
  @SerializedName("to") val to: Address?
  @SerializedName("message") val message: String
  @SerializedName("nonce") val nonce: BigInteger

  constructor(message: String) {
    this.message = (ByteArray(4, { 0 }) + message.toByteArray()).hex()
    this.nonce = BigInteger.ZERO
    this.from = null
    this.to = null
  }

  constructor(message: String, keyPair: ECKeyPair, recipient: Address, nonce: BigInteger = generateNonce()) {
    require(nonce.signum() > 0, { "nonce must be a positive number" })
    this.nonce = nonce
    this.from = Address(keyPair.public)
    this.to = recipient
    val checksummed = Sha256Hash.hash(message.toByteArray()).copyOfRange(0, 4) + message.toByteArray()
    val secret = keyPair.secret(recipient, this.nonce)
    this.message = encryptAes(secret, checksummed).hex()
  }

  private fun decryptOrEmpty(secret: ByteArray) = try {
    decryptAesWithChecksum(secret, message.unhex())
  } catch (ex: Exception) {
    ""
  }

  override val bytes: ByteArray
    get() = Bytes.concat(
        byteArrayOf(1),
        from?.publicKey?.getEncoded(true) ?: ByteArray(33, { 0 }),
        to?.publicKey?.getEncoded(true) ?: ByteArray(33, { 0 }),
        nonce.toLong().bytes(),
        byteArrayOf(message.unhex().size.toByte()),
        message.unhex()
    )

  fun decrypt(keyPair: ECKeyPair): String {
    return if (from == null || to == null) {
      message.drop(2).unhex().toString(Charset.forName("UTF-8"))
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