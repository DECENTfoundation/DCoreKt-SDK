package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.model.types.UInt64
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import java.math.BigInteger

// todo untested
data class Seeder(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("free_space") @UInt64 val freeSpace: BigInteger,
    @SerializedName("price") val price: AssetAmount,
    @SerializedName("expiration") val expiration: LocalDateTime,
    @SerializedName("pubKey") val pubKey: PubKey,
    @SerializedName("ipfs_ID") val ipfsId: String,
    @SerializedName("stats") val stats: ChainObject,
    @SerializedName("rating") @UInt32 val rating: Long,
    @SerializedName("region_code") val regionCode: String
)
