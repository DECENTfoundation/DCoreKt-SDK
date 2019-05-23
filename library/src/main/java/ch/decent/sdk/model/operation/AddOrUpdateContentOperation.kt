package ch.decent.sdk.model.operation

import ch.decent.sdk.DCoreConstants.BASIS_POINTS_TOTAL
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.CoAuthors
import ch.decent.sdk.model.CustodyData
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.KeyPart
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.RegionalPrice
import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.model.types.UInt64
import ch.decent.sdk.utils.TRX_ID_SIZE
import ch.decent.sdk.utils.hex
import ch.decent.sdk.utils.ripemd160
import ch.decent.sdk.utils.unhex
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.math.BigInteger
import java.util.regex.Pattern

/**
 * Request to submit content operation constructor
 *
 * @param size size of content, including samples, in megabytes
 * @param author author of the content. If co-authors is not filled, this account will receive full payout
 * @param coAuthors if map is not empty, payout will be split - the parameter maps co-authors
 * to basis points split, e.g. author1:9000 (bp), author2:1000 (bp),
 * if author is omitted from this map, it is assigned 10000 (bp_total) minus sum of splits
 * @param uri URI where the content can be found
 * @param quorum how many seeders needs to cooperate to recover the key
 * @param price list of regional prices
 * @param hash hash of the content. Should be 40 chars long, hex encoded
 * @param seeders list of selected seeders
 * @param keyParts key particles, each assigned to one of the seeders, encrypted with his key
 * @param expiration content expiration time
 * @param publishingFee fee must be greater than the sum of seeders' publishing prices * number of days. Is paid by author
 * @param synopsis JSON formatted structure containing content information
 * @param custodyData if cd.n == 0 then no custody is submitted, and simplified verification is done.
 * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
 * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
 */
class AddOrUpdateContentOperation @JvmOverloads constructor(
    @SerializedName("size") @UInt64 val size: BigInteger = BigInteger.ONE,
    @SerializedName("author") val author: ChainObject,
    @SerializedName("co_authors") val coAuthors: CoAuthors = CoAuthors(emptyMap()),
    @SerializedName("URI") val uri: String,
    @SerializedName("quorum") @UInt32 val quorum: Int = 0, // seeders count won't overflow Int.max
    @SerializedName("price") val price: List<RegionalPrice>,
    @SerializedName("hash") val hash: String = uri.toByteArray().ripemd160().hex(),
    @SerializedName("seeders") val seeders: List<ChainObject> = emptyList(),
    @SerializedName("key_parts") val keyParts: List<KeyPart> = emptyList(),
    @SerializedName("expiration") val expiration: LocalDateTime,
    @SerializedName("publishing_fee") val publishingFee: AssetAmount = AssetAmount(0),
    @SerializedName("synopsis") val synopsis: String,
    @SerializedName("cd") val custodyData: CustodyData? = null,
    fee: Fee = Fee()
) : BaseOperation(OperationType.CONTENT_SUBMIT_OPERATION, fee) {

  init {
    require(size > BigInteger.ZERO) { "invalid file size" }
    require(author.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
    require(Pattern.compile("^(https?|ipfs|magnet):.*").matcher(uri).matches()) { "unsupported uri scheme" }
    require(quorum >= 0) { "invalid seeders count" }
    require(expiration.toEpochSecond(ZoneOffset.UTC) > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) { "invalid expiration time" }
    require(hash.unhex().size == TRX_ID_SIZE) { "invalid file hash size, should be 40 chars long, hex encoded" }
  }

  override fun toString(): String {
    return "AddOrUpdateContentOperation(size=$size, author=$author, coauthors=$coAuthors, uri='$uri', " +
        "quorum=$quorum, price=$price, hash='$hash', seeders=$seeders, keyParts=$keyParts, expiration=$expiration," +
        " publishingFee=$publishingFee, synopsis='$synopsis', custodyData=$custodyData)"
  }

}
