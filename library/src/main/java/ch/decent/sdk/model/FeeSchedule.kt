package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class FeeSchedule(
    /**
     * operation types int keys
     */
    @SerializedName("parameters") val parameters: Map<Int, FeeParameter>,
    @SerializedName("scale") val scale: Long
)
