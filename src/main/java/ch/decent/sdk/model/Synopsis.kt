package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

data class Synopsis(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("content_type_id") val type: ChainObject,
    @SerializedName("file_name") val fileName: String?,
    @SerializedName("sampleURL") val sampleUrl: String?,
    @SerializedName("fileFormat") val fileFormat: String?
)