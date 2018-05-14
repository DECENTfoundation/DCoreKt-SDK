package ch.decent.sdk.net.model.response

data class RpcListResponse<out T>(
    val id: Int,
    val result: List<T>?,
    val error: Error?
) {
  fun result() = result ?: emptyList()
}