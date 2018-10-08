package ch.decent.sdk.model

import ch.decent.sdk.net.serialization.ByteSerializable
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName

data class KeyParts(
    @SerializedName("C1") val keyC1: PubKey,
    @SerializedName("D1") val keyD1: PubKey
) : ByteSerializable {
  override val bytes: ByteArray
    get() = Bytes.concat(
        keyC1.bytes,
        keyD1.bytes
    )

}