package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import com.google.gson.annotations.SerializedName

data class Authority(
    @SerializedName("weight_threshold") val weightThreshold: Long,
    @SerializedName("account_auths") val accountAuths: List<AccountAuth>,
    @SerializedName("key_auths") val keyAuths: List<KeyAuth>
) {

  constructor(public: Address) : this(1, emptyList(), listOf(KeyAuth(public, 1)))
}

data class KeyAuth(
    val value: Address,
    val weight: Short
)

data class AccountAuth(
    val value: AccountObjectId,
    val weight: Short
)
