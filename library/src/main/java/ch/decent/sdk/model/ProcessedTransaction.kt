package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt16
import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.net.serialization.Serializer
import ch.decent.sdk.utils.TRX_ID_SIZE
import ch.decent.sdk.utils.hash256
import ch.decent.sdk.utils.hex
import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class ProcessedTransaction(
    @SerializedName("signatures") val signatures: List<String>,
    @SerializedName("extensions") val extensions: List<Any>,
    @SerializedName("operations") val operations: List<BaseOperation>,
    @SerializedName("expiration") val expiration: LocalDateTime,
    @SerializedName("ref_block_num") @UInt16 val refBlockNum: Int,
    @SerializedName("ref_block_prefix") @UInt32 val refBlockPrefix: Long,
    @SerializedName("operation_results") val opResults: List<JsonArray>
) {

  val transaction
    get() = Transaction(operations, expiration, refBlockNum, refBlockPrefix, null, signatures, extensions)

  val id: String
    get() = Serializer.serialize(transaction).hash256().take(TRX_ID_SIZE).toByteArray().hex()
}
