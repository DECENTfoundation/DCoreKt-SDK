package ch.decent.sdk.crypto

import ch.decent.sdk.utils.Hex
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class Sha256Hash(val bytes: ByteArray) {

  val hex
    get() = Hex.encode(bytes)

  fun toBigInteger(): BigInteger = BigInteger(1, bytes)

  companion object {
    private fun newDigest(): MessageDigest {
      try {
        return MessageDigest.getInstance("SHA-256")
      } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException(e)  // Can't happen.
      }
    }

    @JvmStatic
    fun of(input: ByteArray): Sha256Hash = hash(input).wrap()

    @JvmOverloads @JvmStatic
    fun hash(input: ByteArray, offset: Int = 0, length: Int = input.size): ByteArray {
      val digest = newDigest()
      digest.update(input, offset, length)
      return digest.digest()
    }

    @JvmOverloads @JvmStatic
    fun hashTwice(input: ByteArray, offset: Int = 0, length: Int = input.size): ByteArray {
      val digest = newDigest()
      digest.update(input, offset, length)
      return digest.digest(digest.digest())
    }
  }

}

fun ByteArray.wrap(): Sha256Hash = Sha256Hash(this)
