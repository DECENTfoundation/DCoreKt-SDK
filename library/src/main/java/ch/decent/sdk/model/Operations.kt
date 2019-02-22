package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.net.serialization.*
import ch.decent.sdk.utils.hex
import ch.decent.sdk.utils.publicElGamal
import ch.decent.sdk.utils.unhex
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.math.BigInteger
import java.net.URL
import java.util.*
import java.util.regex.Pattern

sealed class BaseOperation(
    @Transient var type: OperationType,
    @SerializedName("fee") open var fee: AssetAmount = FEE_UNSET
) : ByteSerializable {

  companion object {
    @JvmField
    val FEE_UNSET = AssetAmount(BigInteger.ZERO)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as BaseOperation

    if (!bytes.contentEquals(other.bytes)) return false
    return true
  }

  override fun hashCode(): Int = Arrays.hashCode(bytes)
}

class EmptyOperation(type: OperationType) : BaseOperation(type) {
  override val bytes: ByteArray
    get() = byteArrayOf(0)

  override fun toString(): String = type.toString()
}

open class CustomOperation constructor(
    @SerializedName("id") val id: Int,
    @SerializedName("payer") val payer: ChainObject,
    @SerializedName("required_auths") val requiredAuths: List<ChainObject>,
    @SerializedName("data") val data: String,
    fee: AssetAmount = BaseOperation.FEE_UNSET
) : BaseOperation(OperationType.CUSTOM_OPERATION, fee) {

  override val bytes: ByteArray
    get() = Bytes.concat(
        byteArrayOf(type.ordinal.toByte()),
        fee.bytes,
        payer.bytes,
        requiredAuths.bytes(),
        id.toShort().bytes(),
        data.unhex().bytes()
    )
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
    fee: AssetAmount = BaseOperation.FEE_UNSET
) : BaseOperation(OperationType.TRANSFER2_OPERATION, fee) {

  init {
    require(from.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
    require(to.objectType == ObjectType.ACCOUNT_OBJECT || to.objectType == ObjectType.CONTENT_OBJECT) { "not an account or content object id" }
  }

  override val bytes: ByteArray
    get() = Bytes.concat(
        byteArrayOf(type.ordinal.toByte()),
        fee.bytes,
        from.bytes,
        if (type == OperationType.TRANSFER2_OPERATION) to.objectTypeIdBytes else to.bytes,
        amount.bytes,
        memo.optionalBytes(),
        byteArrayOf(0)
    )

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
    @SerializedName("region_code_from") val regionCode: Int = Regions.NONE.id,
    fee: AssetAmount = BaseOperation.FEE_UNSET
) : BaseOperation(OperationType.REQUEST_TO_BUY_OPERATION, fee) {

  constructor(credentials: Credentials, content: Content) :
      this(content.uri, credentials.account, content.price(), if (URL(content.uri).protocol != "ipfs") PubKey() else credentials.keyPair.publicElGamal())

  init {
    require(consumer.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
    require(Pattern.compile("^(https?|ipfs|magnet):.*").matcher(uri).matches()) { "unsupported uri scheme" }
    require(price.amount >= BigInteger.ZERO) { "amount must be >= 0" }
  }

  override val bytes: ByteArray
    get() = Bytes.concat(
        byteArrayOf(type.ordinal.toByte()),
        fee.bytes,
        Varint.writeUnsignedVarInt(uri.toByteArray().size),
        uri.toByteArray(),
        consumer.bytes,
        price.bytes,
        regionCode.bytes(),
        publicElGamal.bytes
    )

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
    @SerializedName("new_options") val options: Options? = null,
    fee: AssetAmount = BaseOperation.FEE_UNSET
) : BaseOperation(OperationType.ACCOUNT_UPDATE_OPERATION, fee) {

  constructor(account: Account, votes: Set<VoteId>) : this(account.id, options = account.options.copy(votes = votes))

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
  }

  override val bytes: ByteArray
    get() = Bytes.concat(
        byteArrayOf(type.ordinal.toByte()),
        fee.bytes,
        accountId.bytes,
        owner.optionalBytes(),
        active.optionalBytes(),
        options.optionalBytes(),
        byteArrayOf(0)
    )

  override fun toString(): String {
    return "AccountUpdateOperation(accountId=$accountId, owner=$owner, active=$active, options=$options, fee=$fee)"
  }
}

/**
 * Request to create account operation constructor
 *
 * @param registrar registrar
 * @param owner owner authority
 * @param active active authority
 * @param options account options
 * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
 *
 */
class AccountCreateOperation constructor(
    @SerializedName("registrar") val registrar: ChainObject,
    @SerializedName("name") val name: String,
    @SerializedName("owner") val owner: Authority,
    @SerializedName("active") val active: Authority,
    @SerializedName("options") val options: Options,
    fee: AssetAmount = BaseOperation.FEE_UNSET
) : BaseOperation(OperationType.ACCOUNT_CREATE_OPERATION, fee) {

  init {
    require(registrar.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
    require(Account.isValidName(name)) { "not a valid name" }
  }

  constructor(registrar: ChainObject, name: String, public: Address) :
      this(registrar, name, Authority(public), Authority(public), Options(public))

  override val bytes: ByteArray
    get() = Bytes.concat(
        byteArrayOf(type.ordinal.toByte()),
        fee.bytes,
        registrar.bytes,
        name.bytes(),
        owner.bytes,
        active.bytes,
        options.bytes,
        byteArrayOf(0)
    )

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
class ContentSubmitOperation constructor(
    @SerializedName("size") val size: Long,
    @SerializedName("author") val author: ChainObject,
    @SerializedName("co_authors") val coauthors: List<List<Any>>? = null,
    @SerializedName("URI") val uri: String,
    @SerializedName("quorum") val quorum: Int,
    @SerializedName("price") val price: List<RegionalPrice>,
    @SerializedName("hash") val hash: String,
    @SerializedName("seeders") val seeders: List<ChainObject>,
    @SerializedName("key_parts") val keyParts: List<KeyParts>,
    @SerializedName("expiration") val expiration: LocalDateTime,
    @SerializedName("publishing_fee") val publishingFee: AssetAmount,
    @SerializedName("synopsis") val synopsis: String,
    @SerializedName("cd") val custodyData: CustodyData? = null,
    fee: AssetAmount = BaseOperation.FEE_UNSET
) : BaseOperation(OperationType.CONTENT_SUBMIT_OPERATION, fee) {

  init {
    require(size > 0) { "invalid file size" }
    require(author.objectType == ObjectType.ACCOUNT_OBJECT) { "not an account object id" }
    require(Pattern.compile("^(https?|ipfs|magnet):.*").matcher(uri).matches()) { "unsupported uri scheme" }
    require(quorum >= 0) { "invalid seeders count" }
    require(expiration.toEpochSecond(ZoneOffset.UTC) > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) { "invalid expiration time" }
    require(hash.unhex().size == 20) { "invalid file hash size, should be 40 chars long, hex encoded" }
  }

  override val bytes: ByteArray
    get() = Bytes.concat(
        byteArrayOf(type.ordinal.toByte()),
        fee.bytes,
        size.bytes(),
        author.bytes,
        byteArrayOf(0),
        uri.bytes(),
        quorum.bytes(),
        price.bytes(),
        hash.unhex(),
        seeders.bytes(),
        keyParts.bytes(),
        expiration.toEpochSecond(ZoneOffset.UTC).toInt().bytes(),
        publishingFee.bytes,
        synopsis.bytes(),
        custodyData.optionalBytes()
    )

  override fun toString(): String {
    return "ContentSubmitOperation(size=$size, author=$author, coauthors=$coauthors, uri='$uri', quorum=$quorum, price=$price, hash='$hash', seeders=$seeders, keyParts=$keyParts, expiration=$expiration, publishingFee=$publishingFee, synopsis='$synopsis', custodyData=$custodyData, fee=$fee)"
  }
}

class SendMessageOperation constructor(
    messagePayloadJson: String,
    payer: ChainObject,
    requiredAuths: List<ChainObject> = listOf(payer)
) : CustomOperation(1, payer, requiredAuths, messagePayloadJson.toByteArray().hex())