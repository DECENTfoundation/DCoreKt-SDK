package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Config
import ch.decent.sdk.net.model.ApiGroup

internal object GetConfig : BaseRequest<Config>(
    ApiGroup.DATABASE,
    "get_config",
    Config::class.java
)
