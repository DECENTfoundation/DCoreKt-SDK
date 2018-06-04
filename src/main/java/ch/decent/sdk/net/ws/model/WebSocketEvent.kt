package ch.decent.sdk.net.ws.model

import okhttp3.WebSocket
import okio.ByteString

internal sealed class WebSocketEvent
internal data class OnOpen(val webSocket: WebSocket) : WebSocketEvent()
internal object OnClosing : WebSocketEvent()
internal data class OnMessageText(val text: String) : WebSocketEvent()
internal data class OnMessageBytes(val bytes: ByteString) : WebSocketEvent()