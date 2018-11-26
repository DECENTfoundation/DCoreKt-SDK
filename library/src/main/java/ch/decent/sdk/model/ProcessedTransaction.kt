package ch.decent.sdk.model

import ch.decent.sdk.crypto.Sha256Hash
import ch.decent.sdk.net.serialization.ByteSerializable
import ch.decent.sdk.net.serialization.bytes
import ch.decent.sdk.utils.hex
import com.google.common.primitives.Bytes
import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

data class ProcessedTransaction(
    @SerializedName("signatures") val signatures: List<String>,
    @SerializedName("extensions") val extensions: List<Any>,
    @SerializedName("operations") val operations: List<BaseOperation>,
    @SerializedName("expiration") val expiration: LocalDateTime,
    @SerializedName("ref_block_num") val refBlockNum: Int,
    @SerializedName("ref_block_prefix") val refBlockPrefix: Long,
    @SerializedName("operation_results") val opResults: List<JsonArray>
) : ByteSerializable {
  override val bytes: ByteArray
    get() = Bytes.concat(
        BlockData(refBlockNum, refBlockPrefix, expiration.toEpochSecond(ZoneOffset.UTC)).bytes,
        operations.bytes(),
        byteArrayOf(0) //extensions
    )

  val id: String
    get() = Sha256Hash.hash(bytes).take(20).toByteArray().hex()
}