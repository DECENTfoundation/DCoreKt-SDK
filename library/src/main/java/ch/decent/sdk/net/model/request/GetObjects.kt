package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ObjectId
import ch.decent.sdk.net.model.ApiGroup
import java.lang.reflect.Type

internal open class GetObjects<T>(
    objects: List<ObjectId>,
    returnClass: Type
) : BaseRequest<T>(
    ApiGroup.DATABASE,
    "get_objects",
    returnClass,
    listOf(objects)
)
