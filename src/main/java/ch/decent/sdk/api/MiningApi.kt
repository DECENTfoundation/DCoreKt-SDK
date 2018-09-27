package ch.decent.sdk.api

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Miner
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

}