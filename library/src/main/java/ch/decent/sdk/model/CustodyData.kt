package ch.decent.sdk.model

import ch.decent.sdk.net.serialization.ByteSerializable
import ch.decent.sdk.net.serialization.bytes
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName

data class CustodyData(
    @SerializedName("n") val n: Int,
    @SerializedName("u_seed") val uSeed: String,
    @SerializedName("pubKey") val pubKey: String
) : ByteSerializable {
  override val bytes: ByteArray
    get() = Bytes.concat(
        n.bytes(),
        ByteArray(16, { 0 }),
        ByteArray(33, { 0 })
    )
}