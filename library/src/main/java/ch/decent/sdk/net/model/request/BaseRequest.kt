package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

internal abstract class BaseRequest<T>(
    val apiGroup: ApiGroup,
    val method: String,
    val returnClass: Type,
    val params: List<Any?> = emptyList()
) {
  fun description() = "method: $method, params: ${params.joinToString()}"
  fun json(gson: Gson, callId: Long = 1, callbackId: Long? = null) = gson.toJson(RequestJson(this, callId, callbackId))
}

internal class RequestJson(
    request: BaseRequest<*>,
    @SerializedName("id") val callId: Long = 1,
    callbackId: Long? = null
) {
  @SerializedName("method") val method: String = "call"
  @SerializedName("params") val params: List<*> =
      listOf(request.apiGroup.id, request.method, callbackId?.let { listOf(it) + request.params } ?: request.params)
}