package ch.decent.sdk.model.operation

import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.Fee
import ch.decent.sdk.net.serialization.Serializer
import com.google.gson.annotations.SerializedName
import java.util.*

abstract class BaseOperation(
    @Transient var type: OperationType,
    fee: Fee
) {

  /**
   * Fee for the operation, must be set to valid value when broadcasting to network
   */
  @SerializedName("fee") var fee: AssetAmount = AssetAmount(fee.amount ?: 0, fee.assetId)
    set(value) {
      field = value
      fetchFee = false
    }

  /**
   * Defines whether to fetch required fee for the operation when using the API methods to create transaction or broadcast operation
   */
  @Transient var fetchFee: Boolean = fee.amount == null

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as BaseOperation

    if (!Serializer.serialize(this).contentEquals(Serializer.serialize(other))) return false
    return true
  }

  override fun hashCode(): Int = Arrays.hashCode(Serializer.serialize(this))
}
