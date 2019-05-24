package ch.decent.sdk.model

import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.types.UInt16
import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.net.serialization.Serializer
import ch.decent.sdk.utils.TRX_ID_SIZE
import ch.decent.sdk.utils.hash256
import ch.decent.sdk.utils.hex
import ch.decent.sdk.utils.unhex
import com.google.gson.annotations.SerializedName
import okio.buffer
import okio.source
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

data class Transaction @JvmOverloads constructor(
    @SerializedName("operations") val operations: List<BaseOperation>,
    @SerializedName("expiration") var expiration: LocalDateTime,
    @SerializedName("ref_block_num") @UInt16 val refBlockNum: Int,
    @SerializedName("ref_block_prefix") @UInt32 val refBlockPrefix: Long,
    @Transient val chainId: String?,
    @SerializedName("signatures") val signatures: List<String>? = null,
    @SerializedName("extensions") val extensions: List<Any> = emptyList()
) {

  /**
   * Generate signature on transaction data. May return empty if the signature is not valid for DCore.
   *
   * @param key private key
   * @param chainId id of the DCore chain, different for live/testnet/custom net...
   */
  @JvmOverloads
  fun signature(key: ECKeyPair, chainId: String = this.chainId ?: ""): String {
    require(chainId.isNotBlank()) { "chain id must be set on signing" }

    val data = Serializer.serialize(this)
    return key.signature(chainId.unhex() + data)
  }


  /**
   * Set a single signature to transaction and return it. May change expiration time to meet valid signature checks for DCore.
   *
   * @param key private key
   * @param chainId id of the DCore chain, different for live/testnet/custom net...
   */
  @JvmOverloads
  fun withSignature(key: ECKeyPair, chainId: String = this.chainId ?: ""): Transaction {
    require(chainId.isNotBlank()) { "chain id must be set on signing" }

    var signature = signature(key, chainId)
    while (signature.isBlank()) {
      expiration = expiration.plusSeconds(1L)
      signature = signature(key, chainId)
    }

    return this.copy(signatures = listOf(signature))
  }

  val id: String
    get() = Serializer.serialize(this).hash256().take(TRX_ID_SIZE).toByteArray().hex()

  companion object {
    @JvmStatic
    fun create(ops: List<BaseOperation>, chainId: String, props: DynamicGlobalProps, expiration: Duration): Transaction {
      val bytes = props.headBlockId.unhex().inputStream().source().buffer()
      bytes.skip(2)
      return Transaction(ops, props.time.plus(expiration), bytes.readShort().toInt() and 0xFFFF, bytes.readIntLe().toLong() and 0xFFFFFFFF, chainId)
    }
  }

}
