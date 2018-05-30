package ch.decent.sdk.crypto

import ch.decent.sdk.model.Account
import ch.decent.sdk.model.ChainObject

data class Credentials(
    val account: ChainObject,
    val keyPair: ECKeyPair
) {
  /**
   * Creates credentials
   *
   * @param account the blockchain account to use
   * @param encodedPrivate encoded private key as base58 string eg. 5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn
   */
  constructor(account: Account, encodedPrivate: String): this(account.id, ECKeyPair.fromBase58(encodedPrivate))
}