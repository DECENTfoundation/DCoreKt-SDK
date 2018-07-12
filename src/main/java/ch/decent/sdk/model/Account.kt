package ch.decent.sdk.model

import ch.decent.sdk.net.serialization.ByteSerializable
import ch.decent.sdk.net.serialization.bytes
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName
import java.util.regex.Pattern

data class Account(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("registrar") val registrar: ChainObject,
    @SerializedName("name") val name: String,
    @SerializedName("owner") val owner: Authority,
    @SerializedName("active") val active: Authority,
    @SerializedName("options") val options: Options,
    @SerializedName("rights_to_publish") val rightsToPublish: Publishing,
    @SerializedName("statistics") val statistics: ChainObject,
    @SerializedName("top_n_control_flags") val topControlFlags: Int
) : ByteSerializable {

  override val bytes: ByteArray
    get() = Bytes.concat(
        id.bytes,
        registrar.bytes,
        name.bytes(),
        owner.bytes,
        active.bytes,
        options.bytes,
        rightsToPublish.bytes,
        statistics.bytes,
        topControlFlags.bytes()
    )

  companion object {
    private val pattern = Pattern.compile("^[a-z][a-z0-9-]+[a-z0-9](?:\\.[a-z][a-z0-9-]+[a-z0-9])*\$")

    fun isValidName(name: String) = pattern.matcher(name).matches() && name.length in 5..63
  }
}