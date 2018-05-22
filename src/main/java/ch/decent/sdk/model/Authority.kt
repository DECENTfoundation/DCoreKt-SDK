package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.bytes
import ch.decent.sdk.net.model.ByteSerializable
import ch.decent.sdk.utils.bytes
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName

data class Authority(
    @SerializedName("weight_threshold") val weightThreshold: Int,
    @SerializedName("account_auths") val accountAuths: List<Any>,
    @SerializedName("key_auths") val keyAuths: List<AuthMap>
) : ByteSerializable {

  override val bytes: ByteArray
    get() = Bytes.concat(
        weightThreshold.bytes(),
        ByteArray(1, { accountAuths.size.toByte() }),
        ByteArray(1, { keyAuths.size.toByte() }),
        *keyAuths.map { it.bytes }.toTypedArray()
    )
}

data class AuthMap(
    val value: Address,
    val weight: Short
) : ByteSerializable {

  override val bytes: ByteArray
    get() = Bytes.concat(
        value.bytes(),
        weight.bytes()
    )
}