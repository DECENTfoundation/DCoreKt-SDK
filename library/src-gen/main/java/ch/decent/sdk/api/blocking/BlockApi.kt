@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import kotlin.Long
import kotlin.Suppress

class BlockApi internal constructor(
  private val api: ch.decent.sdk.api.BlockApi
) {
  fun get(blockNum: Long) = api.get(blockNum).blockingGet()
  fun getHeader(blockNum: Long) = api.getHeader(blockNum).blockingGet()
  fun getHeadTime() = api.getHeadTime().blockingGet()}
