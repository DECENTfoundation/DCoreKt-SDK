@file:JvmName("Utils")
@file:Suppress("TooManyFunctions", "MagicNumber")

package ch.decent.sdk.utils

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.ECKeyPair
import okio.Buffer
import okio.ByteString.Companion.decodeHex
import okio.ByteString.Companion.toByteString
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

fun ByteArray.hex(): String = this.toByteString().hex()
fun String.unhex(): ByteArray = this.decodeHex().toByteArray()

fun ECKeyPair.secret(address: Address, nonce: BigInteger): ByteArray = address.publicKey.multiply(this.private).normalize().xCoord.encoded.let {
  val sha512 = MessageDigest.getInstance("SHA-512")
  sha512.digest((nonce.toString() + sha512.digest(it).hex()).toByteArray())
}

fun generateEntropy(power: Int = 250): ByteArray {
  val input = Date().toString().toByteArray()
  var entropy = input.hash256() + input.joinToString().toByteArray() + input

  val start = System.currentTimeMillis()
  while ((System.currentTimeMillis() - start) < power) {
    entropy = entropy.hash256()
  }

  return entropy + ByteArray(32).apply { Random().nextBytes(this) }.hash256()
}

fun ByteArray.hash256(): ByteArray = MessageDigest.getInstance("SHA-256").digest(this)

fun ByteArray.hash512(): ByteArray = MessageDigest.getInstance("SHA-512").digest(this)

fun generateNonce(): BigInteger {
  val sha224 = MessageDigest.getInstance("SHA-224").digest(ECKeyPair(SecureRandom()).privateBytes)
  return BigInteger(1, sha224.copyOf(1) + Buffer().writeLongLe(System.nanoTime()).readByteArray().copyOf(7))
}

fun decryptAesWithChecksum(key: ByteArray, encrypted: ByteArray): String {
  val bytes = decryptAes(key, encrypted)
  val message = bytes.copyOfRange(SIZE_32, bytes.size)
  val checksum = message.hash256().copyOfRange(0, SIZE_32)
  if (bytes.copyOfRange(0, SIZE_32).contentEquals(checksum)) {
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
