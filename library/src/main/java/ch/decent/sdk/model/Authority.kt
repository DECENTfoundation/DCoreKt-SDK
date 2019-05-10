package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import com.google.gson.annotations.SerializedName

data class Authority(
    @SerializedName("weight_threshold") val weightThreshold: Long,
    @SerializedName("account_auths") val accountAuths: List<Any>,
    @SerializedName("key_auths") val keyAuths: List<AuthMap>
) {

  constructor(public: Address) : this(1, emptyList(), listOf(AuthMap(public, 1)))
}

data class AuthMap(
    val value: Address,
    val weight: Short
)
