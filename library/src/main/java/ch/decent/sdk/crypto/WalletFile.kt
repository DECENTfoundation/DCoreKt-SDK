package ch.decent.sdk.crypto

import ch.decent.sdk.model.ChainObject
import java.util.*

data class WalletFile(
  val version: Int,
  val accountId: ChainObject,
  val cipherText: String,
  val salt: String,
  val iv: String,
  val mac: String,
  val id: UUID = UUID.randomUUID()
)
