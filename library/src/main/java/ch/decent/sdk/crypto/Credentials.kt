package ch.decent.sdk.crypto

import ch.decent.sdk.model.AccountObjectId

data class Credentials(
    val account: AccountObjectId,
    val keyPair: ECKeyPair
) {
  constructor(account: AccountObjectId, encodedPrivate: String) : this(account, ECKeyPair.fromBase58(encodedPrivate))
}
