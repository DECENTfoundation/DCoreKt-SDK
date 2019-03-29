@file:JvmName("Utils")

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
import java.util.*

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

fun generateEntropy(power: Int = 250): ByteArray {
  val input = Date().toString()
  var entropy = hash256(input.bytes()) + input.bytes().joinToString().bytes() + input.bytes()

  val start = System.currentTimeMillis()
  while ((System.currentTimeMillis() - start) < power) {
    entropy = hash256(entropy)
  }

  return hash256(entropy + ByteArray(32).apply { Random().nextBytes(this) })
}

fun hash256(data: ByteArray): ByteArray = MessageDigest.getInstance("SHA-256").digest(data)

fun hash512(data: ByteArray): ByteArray = MessageDigest.getInstance("SHA-512").digest(data)

fun generateNonce(): BigInteger {
  val sha224 = MessageDigest.getInstance("SHA-224").digest(ECKeyPair(SecureRandom()).privateBytes)
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

fun decryptAes(keyWithIv: ByteArray, encrypted: ByteArray): ByteArray {
  val ivBytes = keyWithIv.copyOfRange(32, 32 + 16)
  val sks = keyWithIv.copyOfRange(0, 32)
  return decryptAes(ivBytes, sks, encrypted)
}

fun decryptAes(iv: ByteArray, key: ByteArray, encrypted: ByteArray): ByteArray {
  val cipher = createCipher(false, iv, key)
  val clear = ByteArray(cipher.getOutputSize(encrypted.size))
  var len = cipher.processBytes(encrypted, 0, encrypted.size, clear, 0)
  len += cipher.doFinal(clear, len)

  return clear.copyOf(len)
}

fun encryptAes(keyWithIv: ByteArray, clear: ByteArray): ByteArray {
  val ivBytes = keyWithIv.copyOfRange(32, 32 + 16)
  val sks = keyWithIv.copyOfRange(0, 32)
  return encryptAes(ivBytes, sks, clear)
}

fun encryptAes(iv: ByteArray, key: ByteArray, clear: ByteArray): ByteArray {
  val cipher = createCipher(true, iv, key)
  val outBuf = ByteArray(cipher.getOutputSize(clear.size))

  var processed = cipher.processBytes(clear, 0, clear.size, outBuf, 0)
  processed += cipher.doFinal(outBuf, processed)

  return outBuf
}

/**
 * create AES cipher with a fixed block size of 128 bits and available key sizes 128/192/256 bits
 *
 * @param forEncryption if true the cipher is initialised for encryption, if false for decryption
 * @param iv initial vector, 128 bits
 * @param key encryption key, 128/192/256 bits
 */
fun createCipher(forEncryption: Boolean, iv: ByteArray, key: ByteArray) =
    PaddedBufferedBlockCipher(CBCBlockCipher(AESEngine())).apply {
      init(forEncryption, ParametersWithIV(KeyParameter(key), iv))
    }
