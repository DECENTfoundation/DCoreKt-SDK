package ch.decent.sdk.net.model.request

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.Seeder
import ch.decent.sdk.net.model.ApiGroup
import com.google.gson.reflect.TypeToken

internal class GetSeeder(
    accountId: ChainObject
) : BaseRequest<Seeder>(
    ApiGroup.DATABASE,
    "get_seeder",
    Seeder::class.java,
    listOf(accountId)
) {

  init {
    require(accountId.objectType == ObjectType.ACCOUNT_OBJECT) { "not a valid account object id" }
  }
}