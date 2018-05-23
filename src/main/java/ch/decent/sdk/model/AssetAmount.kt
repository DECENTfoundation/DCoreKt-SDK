package ch.decent.sdk.model

import ch.decent.sdk.Globals
import ch.decent.sdk.net.serialization.ByteSerializable
import ch.decent.sdk.net.serialization.bytes
import com.google.common.primitives.Bytes
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class AssetAmount @JvmOverloads constructor(
    @SerializedName("amount") val amount: BigInteger,
    @SerializedName("asset_id") val assetId: ChainObject = Globals.DCT_ASSET_ID
) : ByteSerializable {

  constructor(amount: Long) : this(amount.toBigInteger())

  init {
    require(amount >= BigInteger.ZERO, { "amount must be greater or equal to 0" })
    require(assetId.objectType == ObjectType.ASSET_OBJECT, { "object id is not an asset" })
  }

  override val bytes: ByteArray
    get() = Bytes.concat(amount.toLong().bytes(), assetId.bytes)

}