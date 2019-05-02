package ch.decent.sdk.crypto

import ch.decent.sdk.net.serialization.bytes
import ch.decent.sdk.utils.Base58

/**
 * Parses and generates private keys in the form used by the Bitcoin "dumpprivkey" command. This is the private key
 * bytes with a header byte and 4 checksum bytes at the end. If there are 33 private keyPair bytes instead of 32, then
 * the last byte is a discriminator value for the compressed pubkey.
 */
class DumpedPrivateKey private constructor(
    private val version: Int,
    val bytes: ByteArray,
    val compressed: Boolean
) {

  constructor(private: ECKeyPair): this(0x80, private.privateBytes, private.compressed!!)

  /**
   * Returns the base-58 encoded String representation of this
   * object, including version and checksum bytes.
   */
  private fun toBase58(): String {
    return Base58.encodeChecked(version, bytes, compressed)
  }

  override fun toString(): String {
    return toBase58()
  }

  companion object {
    @JvmStatic
    fun fromBase58(encoded: String): DumpedPrivateKey {
      val versionAndData = Base58.decodeChecked(encoded)
      val version = versionAndData[0].toInt() and 0xFF
      require(version == 0x80) { "$version is not a valid private key version byte" }
      return versionAndData.copyOfRange(1, versionAndData.size).let {
        if (it.size == 33 && it[32].toInt() == 1) {
          DumpedPrivateKey(version, it.copyOfRange(0, it.size - 1), true)
        } else {
          DumpedPrivateKey(version, it, false)
        }
      }
    }

    fun toBase58(private: ECKeyPair): String = DumpedPrivateKey(private).toBase58()
  }

}

fun String.dpk() = DumpedPrivateKey.fromBase58(this)
