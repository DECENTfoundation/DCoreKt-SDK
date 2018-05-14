package ch.decent.sdk.net.model.request

import ch.decent.sdk.net.model.ApiGroup

internal class Login : BaseRequest<Boolean>(
    ApiGroup.LOGIN,
    "login",
    Boolean::class.java,
    listOf("", "")
)