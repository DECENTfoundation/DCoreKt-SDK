package ch.decent.sdk.model

import java.math.BigInteger

data class PubKey(
    val key: BigInteger = BigInteger.ZERO
) {
  val keyString
    get() = "$key."

  constructor(key: String) : this(BigInteger(key))
}
