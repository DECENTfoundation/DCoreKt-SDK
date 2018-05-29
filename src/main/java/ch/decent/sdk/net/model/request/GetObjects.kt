package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.net.model.ApiGroup
import java.lang.reflect.Type

open class GetObjects<T>(
    objects: List<ChainObject>,
    returnClass: Type
) : BaseRequest<T>(
    ApiGroup.DATABASE,
    "get_objects",
    returnClass,
    listOf(objects)
)