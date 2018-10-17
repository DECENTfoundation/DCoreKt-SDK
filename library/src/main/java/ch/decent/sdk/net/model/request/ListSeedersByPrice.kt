package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Seeder
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class ListSeedersByPrice(
    count: Int = 100
) : BaseRequest<List<Seeder>>(
    ApiGroup.DATABASE,
    "list_seeders_by_price",
    TypeToken.getParameterized(List::class.java, Seeder::class.java).type,
    listOf(count)
)