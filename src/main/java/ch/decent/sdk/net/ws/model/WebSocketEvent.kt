package ch.decent.sdk.net.ws.model

import okhttp3.WebSocket
import okio.ByteString

sealed class WebSocketEvent
data class OnOpen(val webSocket: WebSocket): WebSocketEvent()
object OnClosing: WebSocketEvent()
data class OnMessageText(val text: String): WebSocketEvent()
data class OnMessageBytes(val bytes: ByteString): WebSocketEvent()