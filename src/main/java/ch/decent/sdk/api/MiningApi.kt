package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Miner
import ch.decent.sdk.model.MinerId
import ch.decent.sdk.net.model.request.GetMiners
import ch.decent.sdk.net.model.request.LookupMinerAccounts
import io.reactivex.Single

class MiningApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Returns list of miners by their Ids
   *
   * @param minerIds miner ids
   *
   * @return a list of miners
   */
  fun getMiners(minerIds: List<ChainObject>): Single<List<Miner>> = GetMiners(minerIds).toRequest()

  /**
   * lookup names and IDs for registered miners
   *
   * @param term lower bound of the first name
   * @param limit max 1000
   *
   * @return list of found miner ids
   */
  @JvmOverloads
  fun lookupMiners(term: String = "", limit: Int = 1000): Single<List<MinerId>> = LookupMinerAccounts(term, limit).toRequest()


  /**
   * Returns map of the first 1000 miners by their name to miner account
   *
   * @return a map of miner name to miner account
   */
  fun getMiners(): Single<Map<String, Miner>> =
      lookupMiners().flatMap { ids -> getMiners(ids.map { it.id }).map { ids.map { it.name }.zip(it).toMap() } }

}