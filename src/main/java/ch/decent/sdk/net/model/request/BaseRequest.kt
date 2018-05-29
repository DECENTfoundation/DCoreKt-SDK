package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup
import java.lang.reflect.Type

abstract class BaseRequest<T>(
    @Transient val apiGroup: ApiGroup,
    val method: String,
    @Transient val returnClass: Type,
    val params: List<Any> = emptyList(),
    val jsonrpc: String = "2.0",
    val id: Int = 1
)