package ch.decent.sdk.model

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class ProcessedTransaction(
    @SerializedName("signatures") val signatures: List<String>,
    @SerializedName("extensions") val extensions: List<Any>,
    @SerializedName("operations") val operations: List<BaseOperation>,
    @SerializedName("expiration") val expiration: LocalDateTime,
    @SerializedName("ref_block_num") val refBlockNum: Int,
    @SerializedName("ref_block_prefix") val refBlockPrefix: Long,
    @SerializedName("operation_results") val opResults: List<JsonArray>
)