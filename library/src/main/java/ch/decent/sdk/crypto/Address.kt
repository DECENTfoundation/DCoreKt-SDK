package ch.decent.sdk.crypto

import ch.decent.sdk.utils.Base58
import ch.decent.sdk.utils.SIZE_32
import ch.decent.sdk.utils.ripemd160
import org.bouncycastle.math.ec.ECPoint

/**
 * Class used to encapsulate address-related operations.
 */
data class Address(val publicKey: ECPoint, private val prefix: String = PREFIX) {

  override fun toString(): String = encode()

  /**
   * the public key is always in a compressed format in DCore
   */
  fun encode(): String {
    val pubKey = publicKey.getEncoded(true)
    val checksum = calculateChecksum(pubKey)
    val pubKeyChecksummed = pubKey + checksum
    return this.prefix + Base58.encode(pubKeyChecksummed)
  }

  companion object {
    const val PREFIX = "DCT"

    @Suppress("TooGenericExceptionCaught")
    @JvmStatic
    fun isValid(address: String) = try {
      decode(address)
      true
    } catch (e: Exception) {
      false
    }

    @JvmStatic
    fun decode(address: String): Address {
      val prefix = address.substring(0, PREFIX.length)
      val decoded = Base58.decode(address.substring(PREFIX.length, address.length))
      val pubKey = decoded.copyOfRange(0, decoded.size - SIZE_32)
      val checksum = decoded.copyOfRange(decoded.size - SIZE_32, decoded.size)
      val key = ECKeyPair.fromPublic(pubKey)
      val calculatedChecksum = calculateChecksum(pubKey)
      if (calculatedChecksum.indices.find { checksum[it] != calculatedChecksum[it] } != null)
        throw IllegalArgumentException("Checksum error")
      return Address(key.public, prefix)
    }

    @JvmStatic
    fun decodeCheckNull(address: String): Address? {
      val decoded = Base58.decode(address.substring(PREFIX.length, address.length))
      val pubKey = decoded.copyOfRange(0, decoded.size - SIZE_32)
      return if (pubKey.all { it == 0x00.toByte() }) null else decode(address)
    }

    private fun calculateChecksum(data: ByteArray): ByteArray = data.ripemd160().copyOfRange(0, SIZE_32)
//        RIPEMD160Digest().let {
//          val checksum = ByteArray(DIGEST_SIZE)
//          it.update(data, 0, data.size)
//          it.doFinal(checksum, 0)
//          checksum.copyOfRange(0, SIZE_32)
//        }
  }
}

fun String.address() = Address.decode(this)
fun ECPoint.address() = Address(this)

fun Address.bytes(): ByteArray = publicKey.getEncoded(true)
