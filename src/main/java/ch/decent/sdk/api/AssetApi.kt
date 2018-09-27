package ch.decent.sdk.api

import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.BaseOperation
import ch.decent.sdk.model.ChainObject
import io.reactivex.Single

interface AssetApi {

  /**
   * get assets by id
   *
   * @param assetIds asset id eg. DCT id is 1.3.0
   *
   * @return list of assets or empty
   */
  fun getAssets(assetIds: List<ChainObject>): Single<List<Asset>>

  /**
   * lookup assets by symbol
   *
   * @param assetSymbols asset symbols eg. DCT
   *
   * @return list of assets or empty
   */
  fun lookupAssets(assetSymbols: List<String>): Single<List<Asset>>

  /**
   * Returns fees for operation
   *
   * @param op list of operations
   *
   * @return a list of fee asset amounts
   */
  fun getFees(op: List<BaseOperation>): Single<List<AssetAmount>>

  /**
   * Returns fee for operation
   *
   * @param op operation
   *
   * @return a fee asset amount
   */
  fun getFee(op: BaseOperation): Single<AssetAmount> = getFees(listOf(op)).map { it.first() }

}