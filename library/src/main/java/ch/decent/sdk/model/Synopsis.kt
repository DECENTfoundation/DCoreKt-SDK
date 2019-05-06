package ch.decent.sdk.model

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

data class Synopsis(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("content_type_id") val type: ChainObject
) {

  val json: String = GsonBuilder()
      .registerTypeAdapter(ChainObject::class.java, ChainObjectAdapter)
      .create()
      .toJson(this)

}
