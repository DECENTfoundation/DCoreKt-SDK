package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

@Deprecated("")
data class TransactionDetail(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("m_from_account") val from: ChainObject,
    @SerializedName("m_to_account") val to: ChainObject,
    @SerializedName("m_operation_type") val type: Int,
    @SerializedName("m_transaction_amount") val amount: AssetAmount,
    @SerializedName("m_transaction_encrypted_memo") val memo: Memo?,
    @SerializedName("m_transaction_fee") val fee: AssetAmount,
    @SerializedName("m_nft_data_id") val nftDataId: ChainObject?,
    @SerializedName("m_str_description") val description: String,
    @SerializedName("m_timestamp") val timestamp: LocalDateTime
)
