package ch.decent.sdk.net.model.response

import ch.decent.sdk.exception.ObjectNotFoundException

data class RpcResponse<out T>(
    val id: Int,
    val result: T?,
    val error: Error?
) {
  fun result(): T = result ?: throw ObjectNotFoundException()
}