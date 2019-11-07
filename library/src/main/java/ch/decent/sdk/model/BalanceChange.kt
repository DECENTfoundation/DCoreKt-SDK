package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class BalanceChange(
    @SerializedName("hist_object") val operation: OperationHistory,
    @SerializedName("balance") val balance: Balance,
    @SerializedName("fee") val fee: AssetAmount,
    @SerializedName("timestamp") val timestamp: LocalDateTime,
    @SerializedName("transaction_id") val transactionId: String
)

// ??
data class Balance(
    @SerializedName("asset0") val asset0: AssetAmount,
    @SerializedName("asset1") val asset1: AssetAmount
)
