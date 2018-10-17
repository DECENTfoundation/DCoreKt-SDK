package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup

internal object Info: BaseRequest<String>(
    ApiGroup.DATABASE,
    "info",
    String::class.java
)