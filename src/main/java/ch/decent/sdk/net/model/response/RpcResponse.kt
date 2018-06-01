package ch.decent.sdk.net.model.response

import ch.decent.sdk.exception.ObjectNotFoundException

data class RpcResponse<out T>(
    val id: Int,
    val result: T?,
    val error: Error?
) {
  fun result(message: String): T = result ?: throw ObjectNotFoundException(message)
}