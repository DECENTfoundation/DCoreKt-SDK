package ch.decent.sdk.model

import ch.decent.sdk.DCoreConstants.COMMENT_MAX_CHARS
import ch.decent.sdk.DCoreConstants.RATING_ALLOWED
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.types.UInt32
import ch.decent.sdk.net.serialization.Serializer
import ch.decent.sdk.utils.TRX_ID_SIZE
import ch.decent.sdk.utils.hex
import ch.decent.sdk.utils.publicElGamal
import ch.decent.sdk.utils.unhex
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.net.URL
import java.util.*
import java.util.regex.Pattern

// fixme, abstract class, separate package
sealed class BaseOperation(
    @Transient var type: OperationType,
    @SerializedName("fee") open var fee: AssetAmount = FEE_UNSET
) {

  companion object {
    @JvmField
    val FEE_UNSET = AssetAmount(0)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as BaseOperation

    if (!Serializer.serialize(this).contentEquals(Serializer.serialize(other))) return false
    return true
  }

  override fun hashCode(): Int = Arrays.hashCode(Serializer.serialize(this))
}

class EmptyOperation(type: OperationType) : BaseOperation(type) {
  override fun toString(): String = type.toString()
}

/**
 * Custom operation
 *
 * @param type custom operation subtype
 * @param payer account which pays for the fee
 * @param requiredAuths accounts required to authorize this operation with signatures
 * @param data data payload encoded in hex string
 */
open class CustomOperation constructor(
    type: CustomOperationType,
    @SerializedName("payer") val payer: ChainObject,
    @SerializedName("required_auths") val requiredAuths: List<ChainObject>,
    @SerializedName("data") val data: String,
    fee: AssetAmount = FEE_UNSET
) : BaseOperation(OperationType.CUSTOM_OPERATION, fee) {

  @SerializedName("id") val id: Int = type.ordinal
}

/**
 * Transfer operation constructor
 *
 * @param from account object id of the sender
 * @param to account object id or content object id of the receiver
 * @param amount an [AssetAmount] to transfer
 * @param memo optional string note
 * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
 */
class TransferOperation @JvmOverloads constructor(
    @SerializedName("from") val from: ChainObject,
    @SerializedName("to") val to: ChainObject,
    @SerializedName("amount") val amount: AssetAmount,
    @SerializedName("memo") val memo: Memo? = null,
    fee: AssetAmount = FEE_UNSET
) : BaseOperation(OperationType.TRANSFER2_OPERATION, fee) {

  init {
    require(from.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
    require(to.objectType == ObjectType.ACCOUNT_OBJECT || to.objectType == ObjectType.CONTENT_OBJECT) { "not an account or content object id" }
  }

  override fun toString(): String {
    return "TransferOperation(from=$from, to=$to, amount=$amount, memo=$memo, fee=$fee, fee=$fee)"
  }
}

/**
 * Request to purchase content operation constructor
 *
 * @param uri uri of the content
 * @param consumer chain object id of the buyer's account
 * @param price price of content, can be equal to or higher then specified in content
 * @param publicElGamal public el gamal key
 * @param regionCode region code of the consumer
 * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
 */
class PurchaseContentOperation @JvmOverloads constructor(
    @SerializedName("URI") val uri: String,
    @SerializedName("consumer") val consumer: ChainObject,
    @SerializedName("price") val price: AssetAmount,
    @SerializedName("pubKey") val publicElGamal: PubKey,
    @SerializedName("region_code_from") @UInt32 val regionCode: Long = Regions.ALL.id,
    fee: AssetAmount = FEE_UNSET
) : BaseOperation(OperationType.REQUEST_TO_BUY_OPERATION, fee) {

  constructor(credentials: Credentials, content: Content) :
      this(content.uri, credentials.account, content.price(), if (URL(content.uri).protocol != "ipfs") PubKey() else credentials.keyPair.publicElGamal())

  init {
    require(consumer.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
    require(Pattern.compile("^(https?|ipfs|magnet):.*").matcher(uri).matches()) { "unsupported uri scheme" }
    require(price.amount >= 0) { "amount must be >= 0" }
  }

  override fun toString(): String {
    return "PurchaseContentOperation(uri='$uri', consumer=$consumer, price=$price, publicElGamal=$publicElGamal, regionCode=$regionCode, fee=$fee)"
  }
}

/**
 * Request to account update operation constructor
 *
 * @param accountId account
 * @param owner owner authority
 * @param active active authority
 * @param options account options
 * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
 *
 */
class AccountUpdateOperation @JvmOverloads constructor(
    @SerializedName("account") val accountId: ChainObject,
    @SerializedName("owner") val owner: Authority? = null,
    @SerializedName("active") val active: Authority? = null,
    @SerializedName("new_options") val options: AccountOptions? = null,
    fee: AssetAmount = FEE_UNSET
) : BaseOperation(OperationType.ACCOUNT_UPDATE_OPERATION, fee) {

  constructor(account: Account, votes: Set<VoteId>) : this(account.id, options = account.options.copy(votes = votes))

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
  }

  override fun toString(): String {
    return "AccountUpdateOperation(accountId=$accountId, owner=$owner, active=$active, options=$options, fee=$fee)"
  }
}

/**
 * Request to create account operation constructor
 *
 * @param registrar registrar
 * @param name account name
 * @param owner owner authority
 * @param active active authority
 * @param options account options
 * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
 *
 */
class AccountCreateOperation @JvmOverloads constructor(
    @SerializedName("registrar") val registrar: ChainObject,
    @SerializedName("name") val name: String,
    @SerializedName("owner") val owner: Authority,
    @SerializedName("active") val active: Authority,
    @SerializedName("options") val options: AccountOptions,
    fee: AssetAmount = FEE_UNSET
) : BaseOperation(OperationType.ACCOUNT_CREATE_OPERATION, fee) {

  init {
    require(registrar.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
    require(Account.isValidName(name)) { "not a valid name" }
  }

  @JvmOverloads
  constructor(registrar: ChainObject, name: String, public: Address, fee: AssetAmount = FEE_UNSET) :
      this(registrar, name, Authority(public), Authority(public), AccountOptions(public), fee)

  override fun toString(): String {
    return "AccountCreateOperation(registrar=$registrar, name='$name', owner=$owner, active=$active, options=$options, fee=$fee)"
  }

}

/**
 * Request to submit content operation constructor
 *
 * @param size size of content, including samples, in megabytes
 * @param author author of the content. If co-authors is not filled, this account will receive full payout
 * @param coauthors optional parameter. If map is not empty, payout will be splitted - the parameter maps co-authors
 * to basis points split, e.g. author1:9000 (bp), auhtor2:1000 (bp)
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
 * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
 *
 */
class AddOrUpdateContentOperation constructor(
    @SerializedName("size") val size: Long,
    @SerializedName("author") val author: ChainObject,
    @SerializedName("co_authors") val coauthors: List<List<Any>>? = null,
    @SerializedName("URI") val uri: String,
    @SerializedName("quorum") val quorum: Int,
    @SerializedName("price") val price: List<RegionalPrice>,
    @SerializedName("hash") val hash: String,
    @SerializedName("seeders") val seeders: List<ChainObject>,
    @SerializedName("key_parts") val keyParts: List<KeyPart>,
    @SerializedName("expiration") val expiration: LocalDateTime,
    @SerializedName("publishing_fee") val publishingFee: AssetAmount,
    @SerializedName("synopsis") val synopsis: String,
    @SerializedName("cd") val custodyData: CustodyData? = null,
    fee: AssetAmount = FEE_UNSET
) : BaseOperation(OperationType.CONTENT_SUBMIT_OPERATION, fee) {

  init {
    require(size > 0) { "invalid file size" }
    require(author.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
    require(Pattern.compile("^(https?|ipfs|magnet):.*").matcher(uri).matches()) { "unsupported uri scheme" }
    require(quorum >= 0) { "invalid seeders count" }
    require(expiration.toEpochSecond(ZoneOffset.UTC) > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) { "invalid expiration time" }
    require(hash.unhex().size == TRX_ID_SIZE) { "invalid file hash size, should be 40 chars long, hex encoded" }
  }

  override fun toString(): String {
    return "AddOrUpdateContentOperation(" +
        "size=$size," +
        " author=$author," +
        " coauthors=$coauthors," +
        " uri='$uri'," +
        " quorum=$quorum," +
        " price=$price," +
        " hash='$hash'," +
        " seeders=$seeders," +
        " keyParts=$keyParts," +
        " expiration=$expiration," +
        " publishingFee=$publishingFee," +
        " synopsis='$synopsis'," +
        " custodyData=$custodyData," +
        " fee=$fee)"
  }
}

/**
 * Leave comment and rating operation constructor
 *
 * @param uri uri of the content
 * @param consumer chain object id of the buyer's account
 * @param rating 1-5 stars
 * @param comment max 100 chars
 * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
 */
class LeaveRatingAndCommentOperation constructor(
    @SerializedName("URI") val uri: String,
    @SerializedName("consumer") val consumer: ChainObject,
    @SerializedName("rating") val rating: Int,
    @SerializedName("comment") val comment: String,
    fee: AssetAmount = FEE_UNSET
) : BaseOperation(OperationType.LEAVE_RATING_AND_COMMENT_OPERATION, fee) {

  init {
    require(rating in RATING_ALLOWED) { "rating must be between 1-5" }
    require(comment.length <= COMMENT_MAX_CHARS) { "comment max length is $COMMENT_MAX_CHARS chars" }
  }
}

class SendMessageOperation constructor(
    messagePayloadJson: String,
    payer: ChainObject,
    requiredAuths: List<ChainObject> = listOf(payer)
) : CustomOperation(CustomOperationType.MESSAGING, payer, requiredAuths, messagePayloadJson.toByteArray().hex())
