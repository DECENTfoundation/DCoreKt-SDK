package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class FeeSchedule(
    @SerializedName("parameters") val parameters: Map<OperationType, FeeParameter>,
    @SerializedName("scale") val scale: Long
)