package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Config
import ch.decent.sdk.net.model.ApiGroup

internal object GetConfiguration : BaseRequest<Config>(
    ApiGroup.DATABASE,
    "get_configuration",
    Config::class.java
)
