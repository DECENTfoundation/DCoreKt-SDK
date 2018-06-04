package ch.decent.sdk.net.model.response

internal data class Error(
    val code: Int,
    val message: String
)