package ch.decent.sdk.crypto

import ch.decent.sdk.model.Account
import ch.decent.sdk.model.ChainObject

data class Credentials(
    val account: ChainObject,
    val keyPair: ECKeyPair
) {
  constructor(account: Account, encodedPrivate: String): this(account.id, ECKeyPair.fromBase58(encodedPrivate))
}