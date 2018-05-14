package ch.decent.sdk.utils

import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.PubKey
import java.math.BigInteger
import java.security.MessageDigest

object ElGamal {

  private val modulus = BigInteger("11760620558671662461946567396662025495126946227619472274601251081547302009186313201119191293557856181195016058359990840577430081932807832465057884143546419")
  private val generator = 3.toBigInteger()

  fun privateElGamal(keyPair: ECKeyPair) =
      MessageDigest.getInstance("SHA-512").let {
        BigInteger(1, it.digest(keyPair.private!!.toByteArray()))
      }

  fun publicElGamal(private: BigInteger) = generator.modPow(private, modulus)

  fun publicElGamal(keyPair: ECKeyPair) = publicElGamal(privateElGamal(keyPair))

}

fun BigInteger.publicKey(): PubKey = PubKey(this)
fun ECKeyPair.publicElGamal(): PubKey = ElGamal.publicElGamal(this).publicKey()