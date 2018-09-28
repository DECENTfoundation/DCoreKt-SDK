package ch.decent.sdk.model

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
) {

  companion object {
    private val pattern = Pattern.compile("^[a-z][a-z0-9-]+[a-z0-9](?:\\.[a-z][a-z0-9-]+[a-z0-9])*\$")

    fun isValidName(name: String) = pattern.matcher(name).matches() && name.length in 5..63
  }
}