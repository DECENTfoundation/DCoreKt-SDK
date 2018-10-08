package ch.decent.sdk.exception

class ObjectNotFoundException(message: String) : Exception("object does not exist: $message")
