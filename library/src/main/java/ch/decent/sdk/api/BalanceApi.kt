package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.AmountWithAsset
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.VestingBalance
import ch.decent.sdk.net.model.request.GetAccountBalances
import ch.decent.sdk.net.model.request.GetNamedAccountBalances
import ch.decent.sdk.net.model.request.GetVestingBalances
import io.reactivex.Single

class BalanceApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get account balance by id.
   *
   * @param accountId object id of the account, 1.2.*
   * @param asset object id of the assets, 1.3.*
   *
   * @return amount for asset
   */
  fun get(accountId: ChainObject, asset: ChainObject): Single<AssetAmount> = getAll(accountId, listOf(asset)).map { it.single() }

  /**
   * Get account balance by id.
   *
   * @param accountId object id of the account, 1.2.*
   * @param assets object ids of the assets, 1.3.*
   *
   * @return list of amounts for different assets
   */
  @JvmOverloads
  fun getAll(accountId: ChainObject, assets: List<ChainObject> = emptyList()): Single<List<AssetAmount>> = GetAccountBalances(accountId, assets).toRequest()

  /**
   * get account balance by name.
   *
   * @param name account name
   * @param asset object id of the assets, 1.3.*
   *
   * @return amount for asset
   */
  fun get(name: String, asset: ChainObject): Single<AssetAmount> =
      getAll(name, listOf(asset)).map { it.single() }

  /**
   * Get account balance by name.
   *
   * @param name account name
   * @param assets object ids of the assets, 1.3.*
   *
   * @return list of amounts for different assets
   */
  @JvmOverloads
  fun getAll(name: String, assets: List<ChainObject> = emptyList()): Single<List<AssetAmount>> = GetNamedAccountBalances(name, assets).toRequest()

  /**
   * Get account balance by name.
   *
   * @param accountId id of the account
   * @param assetSymbol asset symbol, eg. DCT
   *
   * @return a pair of asset to amount
   */
  fun getWithAsset(accountId: ChainObject, assetSymbol: String = DCoreConstants.DCT_SYMBOL): Single<AmountWithAsset> =
      getAllWithAsset(accountId, listOf(assetSymbol)).map { it.single() }

  /**
   * Get account balance by id with asset.
   *
   * @param accountId id of the account
   * @param assetSymbols asset symbols, eg. DCT
   *
   * @return a list of pairs of assets to amounts
   */
  fun getAllWithAsset(accountId: ChainObject, assetSymbols: List<String>): Single<List<AmountWithAsset>> =
      api.assetApi.getAllByName(assetSymbols).flatMap { assets ->
        getAll(accountId, assets.map { it.id }).map {
          it.map { balance -> AmountWithAsset(assets.single { it.id == balance.assetId }, balance) }
        }
      }

  /**
   * Get account balance by name.
   *
   * @param name account name
   * @param assetSymbol asset symbol, eg. DCT
   *
   * @return a pair of asset to amount
   */
  fun getWithAsset(name: String, assetSymbol: String = DCoreConstants.DCT_SYMBOL): Single<AmountWithAsset> =
      getAllWithAsset(name, listOf(assetSymbol)).map { it.single() }

  /**
   * Get account balance by name.
   *
   * @param name account name
   * @param assetSymbols asset symbols, eg. DCT
   *
   * @return list of assets with amounts
   */
  fun getAllWithAsset(name: String, assetSymbols: List<String>): Single<List<AmountWithAsset>> =
      api.assetApi.getAllByName(assetSymbols).flatMap { assets ->
        getAll(name, assets.map { it.id }).map {
          it.map { balance -> AmountWithAsset(assets.single { it.id == balance.assetId }, balance) }
        }
      }

  /**
   * Get information about a vesting balance object.
   *
   * @param accountId id of the account
   *
   * @return a list of vesting balances with additional information
   */
  fun getAllVesting(accountId: ChainObject): Single<List<VestingBalance>> = GetVestingBalances(accountId).toRequest()

}