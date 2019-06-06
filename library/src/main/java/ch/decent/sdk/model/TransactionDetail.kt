package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class TransactionDetail(
    @SerializedName("id") val id: TransactionDetailObjectId,
    @SerializedName("m_from_account") val from: AccountObjectId,
    @SerializedName("m_to_account") val to: AccountObjectId,
    @SerializedName("m_operation_type") val type: Int,
    @SerializedName("m_transaction_amount") val amount: AssetAmount,
    @SerializedName("m_transaction_encrypted_memo") val memo: Memo?,
    @SerializedName("m_transaction_fee") val fee: AssetAmount,
    @SerializedName("m_str_description") val description: String,
    @SerializedName("m_timestamp") val timestamp: LocalDateTime
)
