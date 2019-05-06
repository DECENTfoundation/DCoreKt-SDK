package ch.decent.sdk.crypto

import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.model.Account
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.CipherKeyPairAdapter
import ch.decent.sdk.model.ExtraKeysAdapter
import ch.decent.sdk.model.PubKey
import ch.decent.sdk.utils.SIZE_128
import ch.decent.sdk.utils.SIZE_256
import ch.decent.sdk.utils.decryptAes
import ch.decent.sdk.utils.encryptAes
import ch.decent.sdk.utils.hex
import ch.decent.sdk.utils.privateElGamal
import ch.decent.sdk.utils.publicElGamal
import ch.decent.sdk.utils.unhex
import com.google.gson.annotations.SerializedName
import org.bouncycastle.crypto.digests.KeccakDigest
import org.bouncycastle.crypto.generators.SCrypt
import org.threeten.bp.LocalDateTime
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.SecureRandom

object Wallet {
  private const val SALT_LEN = 16
  private const val ITER = 76
  private const val MEM = 16384
  private const val THREADS = 2
  private const val N = 1 shl 8
  private const val P = 2
  private const val R = 4
  private const val DK_LEN = 32

  private fun randomBytes(len: Int): ByteArray = SecureRandom().let {
    val bytes = ByteArray(len)
    it.nextBytes(bytes)
    bytes
  }

/*
  private fun kdf(password: String, salt: ByteArray) =
      Argon2Factory.createAdvanced().rawHash(ITER, MEM, THREADS, password, salt).let {
        it.copyOfRange(0, 16) to it.copyOfRange(16, 32)
      }
*/

  private fun kdf(password: String, salt: ByteArray) =
      SCrypt.generate(password.toByteArray(), salt, N, R, P, DK_LEN).let {
        it.copyOfRange(0, SIZE_128) to it.copyOfRange(SIZE_128, SIZE_128 * 2)
      }

  private fun ByteArray.mac() =
      KeccakDigest(SIZE_256 * 8).let {
        val digest = ByteArray(SIZE_256)
        it.update(this, 0, size)
        it.doFinal(digest, 0)
        digest
      }

  fun create(credentials: Credentials, password: String = ""): WalletFile {
    val secret = credentials.keyPair.privateBytes
    val salt = randomBytes(SALT_LEN)
    val derived = kdf(password, salt)
    val iv = randomBytes(SIZE_128)
    val cipher = encryptAes(iv, derived.first, secret)
    val mac = (derived.second + cipher).mac()
    return WalletFile(1, credentials.account, cipher.hex(), salt.hex(), iv.hex(), mac.hex())
  }

  fun decrypt(walletFile: WalletFile, password: String = ""): Credentials {
    val derived = kdf(password, walletFile.salt.unhex())
    val mac = (derived.second + walletFile.cipherText.unhex()).mac()
    if (!mac.contentEquals(walletFile.mac.unhex())) throw CipherException("Invalid password provided!")
    val clear = decryptAes(walletFile.iv.unhex(), derived.first, walletFile.cipherText.unhex())
    return Credentials(walletFile.accountId, ECKeyPair.fromPrivate(clear))
  }

  fun exportDCoreWallet(credentials: Credentials, account: Account, password: String, chainId: String): DCoreWallet {
    require(credentials.account == account.id)
    val pass = MessageDigest.getInstance("SHA-512").digest(password.toByteArray())
    val gson = DCoreSdk.gsonBuilder
        .registerTypeAdapter(Wallet.ExtraKeys::class.java, ExtraKeysAdapter)
        .registerTypeAdapter(Wallet.CipherKeyPair::class.java, CipherKeyPairAdapter)
        .create()

    val cipher = gson.toJson(Wallet.CipherKeys(credentials, pass.hex()))
    return Wallet.DCoreWallet(credentials, account, pass, cipher, chainId)
  }

  fun importDCoreWallet(walletJson: String, password: String): Credentials {
    val pass = MessageDigest.getInstance("SHA-512").digest(password.toByteArray())
    val gson = DCoreSdk.gsonBuilder
        .registerTypeAdapter(Wallet.ExtraKeys::class.java, ExtraKeysAdapter)
        .registerTypeAdapter(Wallet.CipherKeyPair::class.java, CipherKeyPairAdapter)
        .create()
    val import = gson.fromJson(walletJson, Wallet.DCoreWallet::class.java)
    check(import.version >= 1)
    val cipher = decryptAes(pass, import.cipher.unhex()).toString(Charset.defaultCharset())
    val keys = gson.fromJson(cipher, Wallet.CipherKeys::class.java)
    val account = import.accounts.first()
    return Credentials(account.id, keys.ecKeys.first { it.public == account.active.keyAuths.first().value }.private.ecKey())
  }

  data class DCoreWallet(
      @SerializedName("version") val version: Int = 1,
      @SerializedName("update_time") val updateTime: LocalDateTime = LocalDateTime.now(),
      @SerializedName("chain_id") val chainId: String,
      @SerializedName("my_accounts") val accounts: List<Account>,
      @SerializedName("cipher_keys") val cipher: String,
      @SerializedName("extra_keys") val extraKeys: List<ExtraKeys>,
      @SerializedName("pending_account_registrations") val pendingAccountRegistrations: List<Any> = emptyList(),
      @SerializedName("pending_miner_registrations") val pendingMinerRegistrations: List<Any> = emptyList(),
      @SerializedName("ws_server") val wsServer: String = "ws://localhost:8090",
      @SerializedName("ws_user") val wsUser: String = "",
      @SerializedName("ws_password") val wsPassword: String = ""
  ) {
    constructor(credentials: Credentials, account: Account, pass: ByteArray, cipherJson: String, chainId: String) : this(
        accounts = listOf(account),
        cipher = encryptAes(pass, cipherJson.toByteArray()).hex(),
        extraKeys = listOf(ExtraKeys(account.id, listOf(credentials.keyPair.address()))),
        chainId = chainId
    )
  }

  data class ExtraKeys(
      val account: ChainObject,
      val keys: List<Address>
  )

  data class CipherKeys(
      @SerializedName("ec_keys") val ecKeys: List<CipherKeyPair>,
      @SerializedName("checksum") val checksum: String,
      @SerializedName("el_gamal_keys") val elGamalKeys: List<PubKeyPair>
  ) {
    constructor(credentials: Credentials, pass: String) : this(
        listOf(CipherKeyPair(credentials.keyPair.address(), credentials.keyPair.dpk())),
        pass,
        listOf(PubKeyPair(credentials.keyPair))
    )
  }

  data class CipherKeyPair(
      val public: Address,
      val private: DumpedPrivateKey
  )

  data class PubKeyPair(
      @SerializedName("private_key") val private: PubKey,
      @SerializedName("public_key") val public: PubKey
  ) {
    constructor(keyPair: ECKeyPair) : this(keyPair.privateElGamal(), keyPair.publicElGamal())
  }
}

fun Credentials.walletFile(password: String = "") = Wallet.create(this, password)
fun WalletFile.credentials(password: String = "") = Wallet.decrypt(this, password)
