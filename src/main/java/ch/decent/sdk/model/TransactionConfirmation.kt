package ch.decent.sdk.model

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class TransactionConfirmation(
    @SerializedName("id") val id: String,
    @SerializedName("block_num") val blockNum: Long,
    @SerializedName("trx_num") val trxNum: Long,
    @SerializedName("trx") val transaction: ProcessedTransaction
) {

  data class ProcessedTransaction(
      @SerializedName("signatures") val signatures: List<String>,
      @SerializedName("extensions") val extensions: List<Any>,
      @SerializedName("operations") val op: List<JsonArray>,
      @SerializedName("expiration") val expiration: LocalDateTime,
      @SerializedName("ref_block_num") val refBlockNum: Int,
      @SerializedName("ref_block_prefix") val refBlockPrefix: Long,
      @SerializedName("operation_results") val opResults: List<JsonArray>
      ) {

    @Transient val operations: List<BaseOperation>

    init {
//      todo parse op list
//      todo parse opResult list
      operations = emptyList()
    }

  }
}