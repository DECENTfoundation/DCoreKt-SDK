package ch.decent.sdk.model

import ch.decent.sdk.net.serialization.ByteSerializable
import ch.decent.sdk.net.serialization.bytes
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName

data class Publishing(
    @SerializedName("is_publishing_manager") val isPublishingManager: Boolean,
    @SerializedName("publishing_rights_received") val publishRightsReceived: List<Any>,
    @SerializedName("publishing_rights_forwarded") val publishRightsForwarded: List<Any>
) : ByteSerializable {

  override val bytes: ByteArray
    get() = Bytes.concat(
        isPublishingManager.bytes(),
        byteArrayOf(0),
        byteArrayOf(0)
    )
}
