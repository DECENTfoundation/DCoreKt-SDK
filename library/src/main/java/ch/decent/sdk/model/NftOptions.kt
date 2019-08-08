package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName

data class NftOptions(
    @SerializedName("issuer") val issuer: AccountObjectId,
    @SerializedName("max_supply") @UInt32 val maxSupply: Long,
    @SerializedName("fixed_max_supply") val fixedMaxSupply: Boolean,
    @SerializedName("description") val description: String
) {

  fun update(maxSupply: Long? = null, description: String? = null, fixedMaxSupply: Boolean? = null): NftOptions =
      copy(maxSupply = maxSupply ?: this.maxSupply, fixedMaxSupply = fixedMaxSupply ?: this.fixedMaxSupply, description = description ?: this.description).also {
        require(it.maxSupply >= this.maxSupply) { "Max supply must be at least ${this.maxSupply}" }
        require(it.fixedMaxSupply == this.fixedMaxSupply || !this.fixedMaxSupply) { "Max supply must remain fixed" }
        require(it.maxSupply == this.maxSupply || !this.fixedMaxSupply) { "Can not change max supply (it's fixed)" }
        require(this != it) { "no new values to update" }
      }
}
