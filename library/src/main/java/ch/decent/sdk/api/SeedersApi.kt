package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Regions
import ch.decent.sdk.model.Seeder
import ch.decent.sdk.net.model.request.*
import io.reactivex.Single

class SeedersApi internal constructor(api: DCoreApi) : BaseApi(api) {

  fun getSeeder(accountId: ChainObject): Single<Seeder> = GetSeeder(accountId).toRequest()

  fun listSeedersByPrice(count: Int = 100): Single<List<Seeder>> = ListSeedersByPrice(count).toRequest()

  fun listSeedersByUpload(count: Int = 100): Single<List<Seeder>> = ListSeedersByUpload(count).toRequest()

  fun listSeedersByRegion(region: String = Regions.NONE.code): Single<List<Seeder>> = ListSeedersByRegion(region).toRequest()

  fun listSeedersByRating(count: Int = 100): Single<List<Seeder>> = ListSeedersByRating(count).toRequest()

}