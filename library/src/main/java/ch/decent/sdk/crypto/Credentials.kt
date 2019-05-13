package ch.decent.sdk.crypto

import ch.decent.sdk.model.ChainObject

data class Credentials(
    val account: ChainObject,
    val keyPair: ECKeyPair
) {
  constructor(account: ChainObject, encodedPrivate: String) : this(account, ECKeyPair.fromBase58(encodedPrivate))
}
