package ch.decent.sdk.utils

import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.PubKey
import java.math.BigInteger

object ElGamal {

  private val modulus = BigInteger("11760620558671662461946567396662025495126946227619472274601251081547302009186313201119191293557856181195016058359990840577430081932807832465057884143546419")
  private val generator = 3.toBigInteger()

  fun privateElGamal(keyPair: ECKeyPair) = BigInteger(1, hash512(keyPair.privateBytes))

  fun publicElGamal(private: BigInteger) = generator.modPow(private, modulus)

  fun publicElGamal(keyPair: ECKeyPair) = publicElGamal(privateElGamal(keyPair))

}

fun BigInteger.pubKey(): PubKey = PubKey(this)
fun ECKeyPair.publicElGamal(): PubKey = ElGamal.publicElGamal(this).pubKey()
fun ECKeyPair.privateElGamal(): PubKey = ElGamal.privateElGamal(this).pubKey()