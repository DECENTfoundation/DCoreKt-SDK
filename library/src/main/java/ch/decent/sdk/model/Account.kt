package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt8
import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("id") val id: ChainObject,
    @SerializedName("registrar") val registrar: ChainObject,
    @SerializedName("name") val name: String,
    @SerializedName("owner") val owner: Authority,
    @SerializedName("active") val active: Authority,
    @SerializedName("options") val options: AccountOptions,
    @SerializedName("rights_to_publish") val rightsToPublish: Publishing,
    @SerializedName("statistics") val statistics: ChainObject,
    @SerializedName("top_n_control_flags") @UInt8 val topControlFlags: Short
) {

  val primaryAddress
    get() = active.keyAuths.first().value

  companion object {
    private val pattern = Regex("^(?=.{5,63}$)([a-z][a-z0-9-]+[a-z0-9])(\\.[a-z][a-z0-9-]+[a-z0-9])*$")

    fun isValidName(name: String) = pattern.matches(name)
  }
}
