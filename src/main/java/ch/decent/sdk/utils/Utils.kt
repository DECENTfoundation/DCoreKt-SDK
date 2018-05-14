package ch.decent.sdk.utils

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.Sha256Hash
import com.google.common.io.BaseEncoding
import org.bouncycastle.crypto.engines.AESEngine
import org.bouncycastle.crypto.modes.CBCBlockCipher
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher
import org.bouncycastle.crypto.params.KeyParameter
import org.bouncycastle.crypto.params.ParametersWithIV
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.SecureRandom

/**
 * Hex encoding used throughout the DCore framework.
 */
val Hex: BaseEncoding = BaseEncoding.base16().lowerCase()

fun ByteArray.hex() = Hex.encode(this)
fun String.unhex() = Hex.decode(this)

// Serialization utils methods

/**
 *
 *
 * The regular [java.math.BigInteger.toByteArray] includes the sign bit of the number and
 * might result in an extra byte addition. This method removes this extra byte.
 *
 *
 *
 * Assuming only positive numbers, it's possible to discriminate if an extra byte
 * is added by checking if the first element of the array is 0 (0000_0000).
 * Due to the minimal representation provided by BigInteger, it means that the bit sign
 * is the least significant bit 0000_000**0** .
 * Otherwise the representation is not minimal.
 * For example, if the sign bit is 0000_00**0**0, then the representation is not minimal due to the rightmost zero.
 *
 * @param b the integer to format into a byte array
 * @param numBytes the desired size of the resulting byte array
 * @return numBytes byte long array.
 */
fun BigInteger.bytes(numBytes: Int): ByteArray {
  require(signum() >= 0, { "b must be positive or zero" })
  require(numBytes > 0, { "numBytes must be positive" })
  val src = toByteArray()
  val dest = ByteArray(numBytes)
  val isFirstByteOnlyForSign = src[0].toInt() == 0
  val length = if (isFirstByteOnlyForSign) src.size - 1 else src.size
  check(length <= numBytes, { "The given number does not fit in $numBytes" })
  val srcPos = if (isFirstByteOnlyForSign) 1 else 0
  val destPos = numBytes - length
  System.arraycopy(src, srcPos, dest, destPos, length)
  return dest
}

/**
 * Returns an array of bytes with the underlying data used to represent an integer in the reverse form.
 * This is useful for endianess switches, meaning that if you give this function a big-endian integer
 * it will return it's little-endian bytes.
 * @return The array of bytes that represent this value in the reverse format.
 */
fun Int.bytes(): ByteArray {
  return ByteBuffer.allocate(Integer.SIZE / 8).putInt(Integer.reverseBytes(this)).array()
}

/**
 * Same operation as in the Int.reverse function, but in this case for a short (2 bytes) value.
 * @return The array of bytes that represent this value in the reverse format.
 */
fun Short.bytes(): ByteArray {
  return ByteBuffer.allocate(java.lang.Short.SIZE / 8).putShort(java.lang.Short.reverseBytes(this)).array()
}

/**
 * Same operation as in the Int.reverse function, but in this case for a long (8 bytes) value.
 * @return The array of bytes that represent this value in the reverse format.
 */
fun Long.bytes(): ByteArray {
  return ByteBuffer.allocate(java.lang.Long.SIZE / 8).putLong(java.lang.Long.reverseBytes(this)).array()
}

fun String.bytes() = Varint.writeUnsignedVarInt(toByteArray().size) + toByteArray()

fun Boolean.bytes() = byteArrayOf(if (this) 1.toByte() else 0.toByte())

fun String.parseVoteId(): ByteArray = Regex("""(\d+):(\d+)""").matchEntire(this)?.let {
  it.groupValues[2].toInt().shl(8).or(it.groupValues[1].toInt()).bytes()
} ?: throw IllegalArgumentException()

// end of serialization utils methods

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