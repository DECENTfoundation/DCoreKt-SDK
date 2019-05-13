package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Seeder
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import com.google.gson.reflect.TypeToken

internal class ListSeedersByRating(
    count: Int = REQ_LIMIT_MAX
) : BaseRequest<List<Seeder>>(
    ApiGroup.DATABASE,
    "list_seeders_by_rating",
    TypeToken.getParameterized(List::class.java, Seeder::class.java).type,
    listOf(count)
)
