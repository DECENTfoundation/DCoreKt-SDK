package ch.decent.sdk.exception

import ch.decent.sdk.net.model.request.BaseRequest

class ObjectNotFoundException(message: String) : Exception("object does not exist: $message")
