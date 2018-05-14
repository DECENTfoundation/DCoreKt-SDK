package ch.decent.sdk.crypto

import ch.decent.sdk.model.ChainObject

data class Credentials(
    val account: ChainObject,
    val keyPair: ECKeyPair
)