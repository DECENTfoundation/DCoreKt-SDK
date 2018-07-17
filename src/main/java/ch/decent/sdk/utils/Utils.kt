package ch.decent.sdk.utils

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.Sha256Hash
import ch.decent.sdk.net.serialization.bytes
import com.google.common.io.BaseEncoding
import org.bouncycastle.crypto.engines.AESEngine
import org.bouncycastle.crypto.modes.CBCBlockCipher
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher
import org.bouncycastle.crypto.params.KeyParameter
import org.bouncycastle.crypto.params.ParametersWithIV
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Hex encoding used throughout the DCore framework.
 */
val Hex: BaseEncoding = BaseEncoding.base16().lowerCase()

fun ByteArray.hex() = Hex.encode(this)
fun String.unhex() = Hex.decode(this)

fun String.parseVoteId(): ByteArray = Regex("""(\d+):(\d+)""").matchEntire(this)?.let {
  it.groupValues[2].toInt().shl(8).or(it.groupValues[1].toInt()).bytes()
} ?: throw IllegalArgumentException()

fun ECKeyPair.secret(address: Address, nonce: BigInteger): ByteArray = address.publicKey.multiply(this.private).normalize().xCoord.encoded.let {
  val sha512 = MessageDigest.getInstance("SHA-512")
  sha512.digest((nonce.toString() + sha512.digest(it).hex()).toByteArray())
}

fun generateNonce(): BigInteger {
  val sha224 = MessageDigest.getInstance("SHA-224").digest(ECKeyPair(SecureRandom()).private!!.toByteArray())
  return BigInteger(1, sha224.copyOf(1) + System.nanoTime().bytes().copyOf(7))
}

fun decryptAesWithChecksum(key: ByteArray, encrypted: ByteArray): String {
  val bytes = decryptAes(key, encrypted)
  val message = bytes.copyOfRange(4, bytes.size)
  val checksum = Sha256Hash.hash(message).copyOfRange(0, 4)
  if (bytes.copyOfRange(0, 4).contentEquals(checksum)) {
    return message.toString(Charset.forName("UTF-8"))
  } else {
    throw IllegalStateException("checksum for the message does not match")
  }
}

fun decryptAes(key: ByteArray, encrypted: ByteArray): ByteArray {
  val cipher = PaddedBufferedBlockCipher(CBCBlockCipher(AESEngine()))
  val ivBytes = key.copyOfRange(32, 32 + 16)
  val sks = key.copyOfRange(0, 32)

  cipher.init(false, ParametersWithIV(KeyParameter(sks), ivBytes))
  val clear = ByteArray(cipher.getOutputSize(encrypted.size))
  var len = cipher.processBytes(encrypted, 0, encrypted.size, clear, 0)
  len += cipher.doFinal(clear, len)

  return clear.copyOf(len)
}

fun encryptAes(key: ByteArray, clear: ByteArray): ByteArray {
  val cipher = PaddedBufferedBlockCipher(CBCBlockCipher(AESEngine()))
  val ivBytes = key.copyOfRange(32, 32 + 16)
  val sks = key.copyOfRange(0, 32)

  cipher.init(true, ParametersWithIV(KeyParameter(sks), ivBytes))
  val outBuf = ByteArray(cipher.getOutputSize(clear.size))

  var processed = cipher.processBytes(clear, 0, clear.size, outBuf, 0)
  processed += cipher.doFinal(outBuf, processed)

  return outBuf
}

fun decryptAes128(initialVector: ByteArray, encryptedText: ByteArray, key: ByteArray): String =
  Cipher.getInstance("AES/CBC/PKCS5Padding").let {
      it.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"), IvParameterSpec(initialVector))
      String(it.doFinal(encryptedText))
  }