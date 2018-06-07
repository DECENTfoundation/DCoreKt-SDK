package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.net.serialization.ByteSerializable
import ch.decent.sdk.net.serialization.Varint
import ch.decent.sdk.net.serialization.bytes
import ch.decent.sdk.net.serialization.optionalBytes
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

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
 * @param to account object id of the receiver
 * @param amount an amount and asset type to transfer
 * @param memo optional string note
 */
data class TransferOperation @JvmOverloads constructor(
    @SerializedName("from") val from: ChainObject,
    @SerializedName("to") val to: ChainObject,
    @SerializedName("amount") val amount: AssetAmount,
    @SerializedName("memo") val memo: Memo? = null
) : BaseOperation(OperationType.TRANSFER_OPERATION) {

  override val bytes: ByteArray
    get() = Bytes.concat(
        byteArrayOf(type.ordinal.toByte()),
        fee.bytes,
        from.bytes,
        to.bytes,
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

  constructor(registrar: ChainObject, name: String, public: Address): this(registrar, name, Authority(public), Authority(public), Options(public))

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
