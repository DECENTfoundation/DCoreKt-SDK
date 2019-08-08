package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Seeder
import ch.decent.sdk.net.model.ApiGroup

internal class GetSeeder(
    accountId: AccountObjectId
) : BaseRequest<Seeder>(
    ApiGroup.DATABASE,
    "get_seeder",
    Seeder::class.java,
    listOf(accountId)
)
