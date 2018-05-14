package ch.decent.sdk.crypto

import ch.decent.sdk.utils.Base58
import org.bouncycastle.crypto.digests.RIPEMD160Digest
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

    fun decode(address: String): Address {
      val prefix = address.substring(0, 3)
      val decoded = Base58.decode(address.substring(3, address.length))
      val pubKey = decoded.copyOfRange(0, decoded.size - 4)
      val checksum = decoded.copyOfRange(decoded.size - 4, decoded.size)
      val key = ECKeyPair.fromPublic(pubKey)
      val calculatedChecksum = calculateChecksum(pubKey)
      if (calculatedChecksum.indices.find { checksum[it] != calculatedChecksum[it] } != null)
        throw IllegalArgumentException("Checksum error")
      return Address(key.public, prefix)
    }

    fun decodeCheckNull(address: String): Address? {
      val decoded = Base58.decode(address.substring(3, address.length))
      val pubKey = decoded.copyOfRange(0, decoded.size - 4)
      return if (pubKey.all { it == 0x00.toByte() }) null else decode(address)
    }

    private fun calculateChecksum(data: ByteArray): ByteArray =
        RIPEMD160Digest().let {
          val checksum = ByteArray(160 / 8)
          it.update(data, 0, data.size)
          it.doFinal(checksum, 0)
          checksum.copyOfRange(0, 4)
        }
  }
}

fun String.address() = Address.decode(this)
fun ECPoint.address() = Address(this)

fun Address.bytes(): ByteArray = publicKey.getEncoded(true)
