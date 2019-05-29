package ch.decent.sdk.model

import ch.decent.sdk.model.types.Int64
import com.google.gson.annotations.SerializedName

data class AccountBalance(
    @SerializedName("id") val id: AccountBalanceObjectId,
    @SerializedName("owner") val owner: AccountObjectId,
    @SerializedName("asset_type") val assetType: AssetObjectId,
    @SerializedName("balance") @Int64 val balance: Long
)
