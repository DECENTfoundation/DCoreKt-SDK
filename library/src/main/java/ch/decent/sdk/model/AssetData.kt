package ch.decent.sdk.model

import ch.decent.sdk.model.types.Int64
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class AssetData(
    @SerializedName("id") val id: ChainObject = ObjectType.ASSET_OBJECT.genericId,
    @SerializedName("current_supply") @Int64 val currentSupply: Long,
    @SerializedName("asset_pool") @Int64 val assetPool: Long,
    @SerializedName("core_pool") @Int64 val corePool: Long
)
