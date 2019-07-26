@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Regions
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import kotlin.Int
import kotlin.String
import kotlin.Suppress

class SeederApi internal constructor(
  private val api: ch.decent.sdk.api.SeederApi
) {
  fun get(accountId: AccountObjectId) = api.get(accountId).blockingGet()
  fun listByPrice(count: Int = REQ_LIMIT_MAX) = api.listByPrice(count).blockingGet()
  fun listByUpload(count: Int = REQ_LIMIT_MAX) = api.listByUpload(count).blockingGet()
  fun listByRegion(region: String = Regions.ALL.code) = api.listByRegion(region).blockingGet()
  fun listByRating(count: Int = REQ_LIMIT_MAX) = api.listByRating(count).blockingGet()}
