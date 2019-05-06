package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class AccountBalance(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("owner") val owner: ChainObject,
    @SerializedName("asset_type") val assetType: ChainObject,
    @SerializedName("balance") val balance: BigInteger
)
