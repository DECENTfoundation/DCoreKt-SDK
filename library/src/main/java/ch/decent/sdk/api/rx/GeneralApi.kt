@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api.rx

import ch.decent.sdk.model.ChainProperties
import ch.decent.sdk.model.Config
import ch.decent.sdk.model.DynamicGlobalProps
import ch.decent.sdk.model.GlobalProperties
import ch.decent.sdk.model.MinerRewardInput
import ch.decent.sdk.net.model.request.GetChainId
import ch.decent.sdk.net.model.request.GetChainProperties
import ch.decent.sdk.net.model.request.GetConfig
import ch.decent.sdk.net.model.request.GetDynamicGlobalProps
import ch.decent.sdk.net.model.request.GetGlobalProperties
import ch.decent.sdk.net.model.request.GetTimeToMaintenance
import io.reactivex.Single
import org.threeten.bp.LocalDateTime

class GeneralApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Retrieve properties associated with the chain.
   *
   * @return chain id and immutable chain parameters
   */
  fun getChainProperties(): Single<ChainProperties> = GetChainProperties.toRequest()

  /**
   * Retrieve global properties. This object contains all of the properties of the blockchain that are fixed
   * or that change only once per maintenance interval such as the current list of miners, block interval, etc.
   *
   * @return global property object
   */
  fun getGlobalProperties(): Single<GlobalProperties> = GetGlobalProperties.toRequest()

  /**
   * Retrieve compile-time constants.
   *
   * @return configured constants
   */
  fun getConfig(): Single<Config> = GetConfig.toRequest()

  /**
   * Get the chain ID.
   *
   * @return the chain ID identifying blockchain network
   */
  fun getChainId(): Single<String> = GetChainId.toRequest()

  /**
   * Retrieve the dynamic properties. The returned object contains information that changes every block interval,
   * such as the head block number, the next miner, etc.
   *
   * @return dynamic property object
   */
  fun getDynamicGlobalProperties(): Single<DynamicGlobalProps> = GetDynamicGlobalProps.toRequest()

  /**
   * Get remaining time to next maintenance interval from given time.
   *
   * @param time reference time
   *
   * @return remaining time to next maintenance interval along with some additional data
   */
  fun getTimeToMaintenance(time: LocalDateTime): Single<MinerRewardInput> = GetTimeToMaintenance(time).toRequest()

}
