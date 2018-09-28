package ch.decent.sdk.api

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Miner
import ch.decent.sdk.model.MinerId
import io.reactivex.Single

interface MiningApi {

  /**
   * Returns list of miners by their Ids
   *
   * @param minerIds miner ids
   *
   * @return a list of miners
   */
  fun getMiners(minerIds: Set<ChainObject>): Single<List<Miner>>

  /**
   * lookup names and IDs for registered miners
   *
   * @param term lower bound of the first name
   * @param limit max 1000
   */
  fun lookupMiners(term: String = "", limit: Int = 1000): Single<List<MinerId>>

}