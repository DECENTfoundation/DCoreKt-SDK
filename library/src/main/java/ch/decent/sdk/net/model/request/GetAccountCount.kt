package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup

internal object GetAccountCount: BaseRequest<Long>(
    ApiGroup.DATABASE,
    "get_account_count",
    Long::class.java
)