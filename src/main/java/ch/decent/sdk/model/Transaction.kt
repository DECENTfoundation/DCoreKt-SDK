package ch.decent.sdk.model

import ch.decent.sdk.Globals
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.Sha256Hash
import ch.decent.sdk.net.model.ByteSerializable
import ch.decent.sdk.utils.Hex
import ch.decent.sdk.utils.hex
import ch.decent.sdk.utils.unhex
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

data class Transaction @JvmOverloads constructor(
    @Transient private val blockData: BlockData,
    @SerializedName("operations") private val operations: List<BaseOperation>,
    @Transient private val chainId: String = Globals.DCT_CHAIN_ID,
    @SerializedName("signatures") val signatures: List<String>? = null
) : ByteSerializable {
  @SerializedName("expiration") val expiration: LocalDateTime = LocalDateTime.ofEpochSecond(blockData.expiration, 0, ZoneOffset.UTC)
  @SerializedName("ref_block_num") val refBlockNum: Int = blockData.refBlockNum
  @SerializedName("ref_block_prefix") val refBlockPrefix: Long = blockData.refBlockPrefix
  @SerializedName("extensions") val extensions: List<Any> = emptyList()

  private fun increment() = this.copy(blockData = blockData.increment())

  override val bytes: ByteArray
    get() = Bytes.concat(
        blockData.bytes,
        ByteArray(1, { operations.size.toByte() }),
        *operations.map { it.bytes }.toTypedArray(),
        ByteArray(1, { 0 }) //extensions
    )

  fun withSignature(keyPair: ECKeyPair): Transaction {
    var signature: String
    var transaction = this
    do {
      transaction = transaction.increment()
      val hash = Sha256Hash.of(chainId.unhex() + transaction.bytes)
      signature = keyPair.signature(hash)
    } while (signature.isBlank())
    return transaction.copy(signatures = listOf(signature))
  }

  val id: String
    get() = Sha256Hash.hash(bytes).take(20).toByteArray().hex()

}
