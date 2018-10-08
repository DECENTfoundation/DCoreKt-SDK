package ch.decent.sdk.net.model.response

import org.threeten.bp.LocalDateTime

internal data class Error(
    val code: Int,
    val message: String,
    val data: Data
)

internal data class Data(
    val code: Int,
    val name: String,
    val message: String,
    val stack: List<Stack>
)

internal data class Stack(
    val context: Context,
    val format: String,
    val data: Any
)

internal data class Context(
    val level: String,
    val file: String,
    val line: Int,
    val method: String,
    val hostname: String,
    val thread_name: String,
    val timestamp: LocalDateTime
)