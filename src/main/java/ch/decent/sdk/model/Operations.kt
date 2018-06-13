package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.net.serialization.ByteSerializable
import ch.decent.sdk.net.serialization.Varint
import ch.decent.sdk.net.serialization.bytes
import ch.decent.sdk.net.serialization.optionalBytes
import ch.decent.sdk.utils.unhex
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.math.BigInteger
import java.util.regex.Pattern

sealed class BaseOperation(@Transient val type: OperationType) : ByteSerializable {
  //  @SerializedName("extensions") val extensions = emptyList<Any>()
  @SerializedName("fee") open var fee: AssetAmount = AssetAmount(BigInteger.ZERO)
}

class EmptyOperation(type: OperationType) : BaseOperation(type) {
  override val bytes: ByteArray
    get() = byteArrayOf(0)

  override fun toString(): String = type.toString()
}

/**
 * Transfer operation constructor
 *
 * @param from account object id of the sender
 * @param to account object id or content object id of the receiver
 * @param amount an amount and asset type to transfer
 * @param memo optional string note
 */
data class TransferOperation @JvmOverloads constructor(
    @SerializedName("from") val from: ChainObject,
    @SerializedName("to") val to: ChainObject,
    @SerializedName("amount") val amount: AssetAmount,
    @SerializedName("memo") val memo: Memo? = null
) : BaseOperation(OperationType.TRANSFER2_OPERATION) {

  init {
    require(from.objectType == ObjectType.ACCOUNT_OBJECT, { "not an account object id" })
    require(to.objectType == ObjectType.ACCOUNT_OBJECT || to.objectType == ObjectType.CONTENT_OBJECT, { "not an account or content object id" })
  }

  override val bytes: ByteArray
    get() = Bytes.concat(
        byteArrayOf(type.ordinal.toByte()),
        fee.bytes,
        from.bytes,
        to.objectTypeIdBytes,
        amount.bytes,
        memo.optionalBytes(),
        byteArrayOf(0)
    )
}

/**
 * Request to buy content operation constructor
 *
 * @param uri uri of the content
 * @param consumer chain object id of the buyer's account
 * @param price price of content, can be equal to or higher then specified in content
 * @param publicElGamal public el gamal key
 * @param regionCode region code of the consumer
 *
 */
data class BuyContentOperation @JvmOverloads constructor(
    @SerializedName("URI") val uri: String,
    @SerializedName("consumer") val consumer: ChainObject,
    @SerializedName("price") val price: AssetAmount,
    @SerializedName("pubKey") val publicElGamal: PubKey,
    @SerializedName("region_code_from") val regionCode: Int = 1
) : BaseOperation(OperationType.REQUEST_TO_BUY_OPERATION) {

  init {
    require(consumer.objectType == ObjectType.ACCOUNT_OBJECT, { "not an account object id" })
    require(Pattern.compile("^(https?|ipfs|magnet):.*").matcher(uri).matches(), { "unsupported uri scheme" })
    require(price.amount > BigInteger.ZERO, { "amount must be > 0" })
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
}

/**
 * Request to account update operation constructor
 *
 * @param accountId account
 * @param owner owner authority
 * @param active active authority
 * @param options account options
 *
 */
data class AccountUpdateOperation @JvmOverloads constructor(
    @SerializedName("account") val accountId: ChainObject,
    @SerializedName("owner") val owner: Authority? = null,
    @SerializedName("active") val active: Authority? = null,
    @SerializedName("new_options") val options: Options? = null
) : BaseOperation(OperationType.ACCOUNT_UPDATE_OPERATION) {

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT, { "not an account object id" })
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
}

/**
 * Request to create account operation constructor
 *
 * @param registrar registrar
 * @param owner owner authority
 * @param active active authority
 * @param options account options
 *
 */
data class AccountCreateOperation constructor(
    @SerializedName("registrar") val registrar: ChainObject,
    @SerializedName("name") val name: String,
    @SerializedName("owner") val owner: Authority,
    @SerializedName("active") val active: Authority,
    @SerializedName("options") val options: Options
) : BaseOperation(OperationType.ACCOUNT_CREATE_OPERATION) {

  init {
    require(registrar.objectType == ObjectType.ACCOUNT_OBJECT, { "not an account object id" })
    require(Pattern.compile("^[a-z][a-z0-9-]+[a-z0-9](?:\\.[a-z][a-z0-9-]+[a-z0-9])*\$").matcher(name).matches() &&
        name.length in 5..63, { "not a valid name" }) // 5 and 63 -> Graphene min and max account name length
  }

  constructor(registrar: ChainObject, name: String, public: Address) : this(registrar, name, Authority(public), Authority(public), Options(public))

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
 *
 */
data class ContentSubmitOperation constructor(
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
    @SerializedName("cd") val custodyData: CustodyData? = null
) : BaseOperation(OperationType.CONTENT_SUBMIT_OPERATION) {

  init {
    require(size > 0, { "invalid file size" })
    require(author.objectType == ObjectType.ACCOUNT_OBJECT, { "not an account object id" })
    require(Pattern.compile("^(https?|ipfs|magnet):.*").matcher(uri).matches(), { "unsupported uri scheme" })
    require(quorum >= 0, { "invalid seeders count" })
    require(expiration.toEpochSecond(ZoneOffset.UTC) > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), { "invalid expiration time" })
    require(hash.length == 20, { "invalid file hash size, should be 40 chars long, hex encoded" })
  }

  override val bytes: ByteArray
    get() = Bytes.concat(
        byteArrayOf(type.ordinal.toByte()),
        fee.bytes,
        size.bytes(),
        author.bytes,
        ByteArray(1, { 0 }),
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
}
