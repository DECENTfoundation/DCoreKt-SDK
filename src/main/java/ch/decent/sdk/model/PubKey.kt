package ch.decent.sdk.model

import ch.decent.sdk.net.model.ByteSerializable
import ch.decent.sdk.utils.Varint
import java.math.BigInteger

data class PubKey(
    val key: BigInteger = BigInteger.ZERO
): ByteSerializable {
  val keyString
      get() = "$key."

  constructor(key: String): this(BigInteger(key))

  override val bytes: ByteArray
    get() = Varint.writeUnsignedVarInt(keyString.toByteArray().size) + keyString.toByteArray()
}
