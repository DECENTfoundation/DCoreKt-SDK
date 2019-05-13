package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup

internal class RequestApiAccess(api: ApiGroup) : BaseRequest<Int>(
    ApiGroup.LOGIN,
    api.apiName,
    Int::class.java,
    emptyList()
)
