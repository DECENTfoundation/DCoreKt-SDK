package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.bytes
import ch.decent.sdk.net.serialization.ByteSerializable
import ch.decent.sdk.net.serialization.bytes
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName

data class Authority(
    @SerializedName("weight_threshold") val weightThreshold: Int,
    @SerializedName("account_auths") val accountAuths: List<Any>,
    @SerializedName("key_auths") val keyAuths: List<AuthMap>
) : ByteSerializable {

  constructor(public: Address): this(1, emptyList(), listOf(AuthMap(public, 1)))

  override val bytes: ByteArray
    get() = Bytes.concat(
        weightThreshold.bytes(),
//        accountAuths.bytes(),
        byteArrayOf(0),
        keyAuths.bytes()
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
