package ch.decent.sdk.model

import ch.decent.sdk.model.types.Int64
import com.google.gson.annotations.SerializedName

data class AccountBalance(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("owner") val owner: ChainObject,
    @SerializedName("asset_type") val assetType: ChainObject,
    @SerializedName("balance") @Int64 val balance: Long
)
