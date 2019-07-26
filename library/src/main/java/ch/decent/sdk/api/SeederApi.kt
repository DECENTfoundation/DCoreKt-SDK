@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Regions
import ch.decent.sdk.model.Seeder
import ch.decent.sdk.net.model.request.GetSeeder
import ch.decent.sdk.net.model.request.ListSeedersByPrice
import ch.decent.sdk.net.model.request.ListSeedersByRating
import ch.decent.sdk.net.model.request.ListSeedersByRegion
import ch.decent.sdk.net.model.request.ListSeedersByUpload
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import io.reactivex.Single

class SeederApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get a seeder by ID.
   * @param accountId seeder account object id
   *
   * @return a seeder object or [ObjectNotFoundException] if not found
   */
  fun get(accountId: ChainObject): Single<Seeder> = GetSeeder(accountId).toRequest()

  /**
   * Get a list of seeders by price, in increasing order.
   *
   * @param count number of items to retrieve, max 100
   *
   * @return a list of seeders
   */
  @JvmOverloads
  fun listByPrice(count: Int = REQ_LIMIT_MAX): Single<List<Seeder>> = ListSeedersByPrice(count).toRequest()

  /**
   * Get a list of seeders ordered by total upload, in decreasing order.
   *
   * @param count number of items to retrieve, max 100
   *
   * @return a list of seeders
   */
  @JvmOverloads
  fun listByUpload(count: Int = REQ_LIMIT_MAX): Single<List<Seeder>> = ListSeedersByUpload(count).toRequest()

  /**
   * Get a list of seeders ordered by price.
   *
   * @param region region code, defined in [Regions]
   *
   * @return a list of seeders
   */
  @JvmOverloads
  fun listByRegion(region: String = Regions.ALL.code): Single<List<Seeder>> = ListSeedersByRegion(region).toRequest()

  /**
   * Get a list of seeders by price, in decreasing order.
   *
   * @param count number of items to retrieve, max 100
   *
   * @return a list of seeders
   */
  @JvmOverloads
  fun listByRating(count: Int = REQ_LIMIT_MAX): Single<List<Seeder>> = ListSeedersByRating(count).toRequest()

}
