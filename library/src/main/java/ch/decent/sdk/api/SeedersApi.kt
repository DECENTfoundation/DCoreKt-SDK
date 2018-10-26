package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Regions
import ch.decent.sdk.model.Seeder
import ch.decent.sdk.net.model.request.*
import io.reactivex.Single

class SeedersApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get a seeder by ID.
   * @param accountId seeder account object id
   *
   * @return a seeder object or [ObjectNotFoundException] if not found
   */
  fun getSeeder(accountId: ChainObject): Single<Seeder> = GetSeeder(accountId).toRequest()

  /**
   * Get a list of seeders by price, in increasing order.
   *
   * @param count number of items to retrieve, max 100
   *
   * @return a list of seeders
   */
  fun listSeedersByPrice(count: Int = 100): Single<List<Seeder>> = ListSeedersByPrice(count).toRequest()

  /**
   * Get a list of seeders ordered by total upload, in decreasing order.
   *
   * @param count number of items to retrieve, max 100
   *
   * @return a list of seeders
   */
  fun listSeedersByUpload(count: Int = 100): Single<List<Seeder>> = ListSeedersByUpload(count).toRequest()

  /**
   * Get a list of seeders ordered by price.
   *
   * @param region region code, defined in [Regions]
   *
   * @return a list of seeders
   */
  fun listSeedersByRegion(region: String = Regions.ALL.code): Single<List<Seeder>> = ListSeedersByRegion(region).toRequest()

  /**
   * Get a list of seeders by price, in decreasing order.
   *
   * @param count number of items to retrieve, max 100
   *
   * @return a list of seeders
   */
  fun listSeedersByRating(count: Int = 100): Single<List<Seeder>> = ListSeedersByRating(count).toRequest()

}