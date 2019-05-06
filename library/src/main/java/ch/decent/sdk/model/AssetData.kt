package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class AssetData(
    @SerializedName("id") val id: ChainObject = ObjectType.ASSET_OBJECT.genericId,
    @SerializedName("current_supply") val currentSupply: BigInteger,
    @SerializedName("asset_pool") val assetPool: BigInteger,
    @SerializedName("core_pool") val corePool: BigInteger
)
