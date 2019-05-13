package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName

data class FeeSchedule(
    @SerializedName("parameters") val parameters: Map<OperationType, FeeParameter>,
    @SerializedName("scale") @UInt32 val scale: Long
)
