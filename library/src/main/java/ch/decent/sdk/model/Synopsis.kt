package ch.decent.sdk.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Synopsis(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("content_type_id") val type: String = contentType(ApplicationType.DECENT_CORE, CategoryType.NONE)
) {

  val json: String = Gson().toJson(this)

  companion object {
    fun fromJson(json: String): Synopsis = Gson().fromJson(json, Synopsis::class.java)
  }

}
