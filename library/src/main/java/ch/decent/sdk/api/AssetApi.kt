@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.AssetData
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.RealSupply
import ch.decent.sdk.net.model.request.GetAssets
import ch.decent.sdk.net.model.request.GetAssetsData
import ch.decent.sdk.net.model.request.GetRealSupply
import ch.decent.sdk.net.model.request.ListAssets
import ch.decent.sdk.net.model.request.LookupAssets
import ch.decent.sdk.net.model.request.PriceToDct
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import io.reactivex.Single

class AssetApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get asset by id.
   *
   * @param assetId asset id eg. DCT id is 1.3.0
   *
   * @return asset or [ObjectNotFoundException]
   */
  fun get(assetId: ChainObject): Single<Asset> = getAll(listOf(assetId)).map { it.single() }

  /**
   * Get assets by id.
   *
   * @param assetIds asset id eg. DCT id is 1.3.0
   *
   * @return list of assets or [ObjectNotFoundException]
   */
  fun getAll(assetIds: List<ChainObject>): Single<List<Asset>> = GetAssets(assetIds).toRequest()

  /**
   * Return current core asset supply.
   *
   * @return current supply
   */
  fun getRealSupply(): Single<RealSupply> = GetRealSupply.toRequest()

  /**
   * Get asset dynamic data by id.
   *
   * @param assetId asset data id eg. DCT id is 2.3.0
   *
   * @return asset dynamic data or [ObjectNotFoundException]
   */
  fun getAssetsData(assetId: List<ChainObject>): Single<List<AssetData>> = GetAssetsData(assetId).toRequest()

  /**
   * Get asset dynamic data by id.
   *
   * @param assetId asset data id eg. DCT id is 2.3.0
   *
   * @return asset dynamic data or [ObjectNotFoundException]
   */
  fun getAssetData(assetId: ChainObject): Single<AssetData> = getAssetsData(listOf(assetId)).map { it.single() }

  /**
   * Get assets alphabetically by symbol name.
   *
   * @param lowerBound lower bound of symbol names to retrieve
   * @param limit maximum number of assets to fetch (must not exceed 100)
   *
   * @return the assets found
   */
  @JvmOverloads
  fun listAllRelative(lowerBound: String, limit: Int = 100): Single<List<Asset>> = ListAssets(lowerBound, limit).toRequest()

  @JvmOverloads
  fun listAll(includeMonitored: Boolean = false): Single<List<Asset>> =
      if (includeMonitored) pageAll("")
      else pageAll("").map { it.filter { it.monitoredAssetOpts == null } }

  /**
   * Lookup asset by symbol.
   *
   * @param assetSymbol asset symbol eg. DCT
   *
   * @return asset or [ObjectNotFoundException]
   */
  fun getByName(assetSymbol: String): Single<Asset> = getAllByName(listOf(assetSymbol)).map { it.single() }

  /**
   * Lookup assets by symbol.
   *
   * @param assetSymbols asset symbols eg. DCT
   *
   * @return list of assets or [ObjectNotFoundException]
   */
  fun getAllByName(assetSymbols: List<String>): Single<List<Asset>> = LookupAssets(assetSymbols).toRequest()

  /**
   * Converts asset into DCT, using actual price feed.
   *
   * @param amount some amount
   *
   * @return price in DCT
   */
  fun convertToDct(amount: AssetAmount): Single<AssetAmount> = PriceToDct(amount).toRequest()

  private fun pageAll(lowerBound: String): Single<List<Asset>> {
    return listAllRelative(lowerBound, REQ_LIMIT_MAX)
        .flatMap { prev ->
          if (prev.size < REQ_LIMIT_MAX) {
            Single.just(prev)
          } else {
            pageAll(prev.last().symbol).map { next -> next.drop(1) + prev }
          }
        }
  }

}
