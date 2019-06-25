package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.Seeder
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class ListSeedersByRegion(
    region: String
) : BaseRequest<List<Seeder>>(
    ApiGroup.DATABASE,
    "list_seeders_by_region",
    TypeToken.getParameterized(List::class.java, Seeder::class.java).type,
    listOf(region)
)
