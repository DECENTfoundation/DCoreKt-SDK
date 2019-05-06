package ch.decent.sdk.exception

import ch.decent.sdk.net.model.response.Error

class DCoreException internal constructor(error: Error) : Exception(error.message)
