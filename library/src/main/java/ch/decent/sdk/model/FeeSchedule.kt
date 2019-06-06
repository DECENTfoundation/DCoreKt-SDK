package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt32
import com.google.gson.annotations.SerializedName

data class FeeSchedule(
    /**
     * operation types int keys
     */
    @SerializedName("parameters") val parameters: Map<Int, FeeParameter>,
    @SerializedName("scale") @UInt32 val scale: Long
)
