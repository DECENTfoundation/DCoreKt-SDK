package ch.decent.sdk.model

import ch.decent.sdk.net.model.ByteSerializable
import ch.decent.sdk.net.model.OperationType
import ch.decent.sdk.utils.Varint
import ch.decent.sdk.utils.bytes
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

/*
data class Operation(
    val type: OperationType,
    val operation: BaseOperation
) : ByteSerializable {
  override val bytes: ByteArray
    get() = byteArrayOf(type.ordinal.toByte()) + operation.bytes
}
*/

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
        memo?.bytes ?: byteArrayOf(0),
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
        owner?.let { byteArrayOf(1.toByte()) + bytes } ?: byteArrayOf(0.toByte()),
        active?.let { byteArrayOf(1.toByte()) + bytes } ?: byteArrayOf(0.toByte()),
        options?.let { byteArrayOf(1.toByte()) + it.bytes } ?: byteArrayOf(0.toByte()),
        byteArrayOf(0)
    )
}
