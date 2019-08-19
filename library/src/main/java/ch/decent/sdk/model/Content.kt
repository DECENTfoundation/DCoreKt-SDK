package ch.decent.sdk.model

import ch.decent.sdk.model.types.Int64
import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.model.types.UInt64
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import java.math.BigInteger

data class Content(
    @SerializedName("id") val id: ContentObjectId,
    @SerializedName("author") val author: AccountObjectId,
    @SerializedName("co_authors") val coAuthors: CoAuthors,
    @SerializedName("expiration") val expiration: LocalDateTime,
    @SerializedName("created") val created: LocalDateTime,
    @SerializedName("price") val price: PricePerRegion,
    @SerializedName("synopsis") val synopsis: String,
    @SerializedName("size") @UInt64 val size: BigInteger,
    @SerializedName("quorum") @UInt32 val quorum: Int,
    @SerializedName("URI") val uri: String,
    @SerializedName("key_parts") val keyParts: Map<AccountObjectId, KeyPart>,
    @SerializedName("last_proof") val lastProof: Map<AccountObjectId, LocalDateTime>,
    @SerializedName("seeder_price") @Int64 val seederPrice: Map<AccountObjectId, Long>,
    @SerializedName("is_blocked") val isBlocked: String,
    @SerializedName("_hash") val hash: String,
    @SerializedName("AVG_rating") @UInt64 val rating: BigInteger,
    @SerializedName("num_of_ratings") @UInt32 val ratingCount: Long,
    @SerializedName("times_bought") @UInt32 val timesBought: Long,
    @SerializedName("publishing_fee_escrow") val publishingFeeEscrow: AssetAmount,
    @SerializedName("cd") val custodyData: CustodyData
) {

  // todo?
  fun price() = price.prices[Regions.ALL.id]!!
}
