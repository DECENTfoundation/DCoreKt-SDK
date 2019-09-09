package ch.decent.sdk.model

import ch.decent.sdk.model.operation.BaseOperation
import com.google.gson.annotations.SerializedName

data class OpWrapper(
    @SerializedName("op") val op: BaseOperation
)

