@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import kotlin.Suppress
import org.threeten.bp.LocalDateTime

class GeneralApi internal constructor(
  private val api: ch.decent.sdk.api.GeneralApi
) {
  fun getChainProperties() = api.getChainProperties().blockingGet()
  fun getGlobalProperties() = api.getGlobalProperties().blockingGet()
  fun getConfig() = api.getConfig().blockingGet()
  fun getChainId() = api.getChainId().blockingGet()
  fun getDynamicGlobalProperties() = api.getDynamicGlobalProperties().blockingGet()
  fun getTimeToMaintenance(time: LocalDateTime) = api.getTimeToMaintenance(time).blockingGet()}
