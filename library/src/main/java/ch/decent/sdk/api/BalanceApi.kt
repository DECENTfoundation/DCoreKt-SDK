package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.GetAccountBalances
import ch.decent.sdk.net.model.request.GetNamedAccountBalances
import ch.decent.sdk.net.model.request.GetVestingBalances
import io.reactivex.Single

class BalanceApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get account balance by id.
   *
   * @param accountId object id of the account, 1.2.*
   * @param assets object ids of the assets, 1.3.*
   *
   * @return list of amounts for different assets
   */
  @JvmOverloads
  fun getBalance(accountId: ChainObject, assets: List<ChainObject> = emptyList()): Single<List<AssetAmount>> = GetAccountBalances(accountId, assets).toRequest()

  /**
   * Get account balance by id.
   *
   * @param accountId object id of the account, 1.2.*
   * @param asset object id of the assets, 1.3.*
   *
   * @return amount for asset
   */
  fun getBalance(accountId: ChainObject, asset: ChainObject): Single<AssetAmount> = getBalance(accountId, listOf(asset)).map { it.single() }

  /**
   * Get account balance by name.
   *
   * @param accountReference name, id or public key of the account
   * @param assets object ids of the assets, 1.3.*
   *
   * @return list of amounts for different assets
   */
  @JvmOverloads
  fun getBalance(accountReference: String, assets: List<ChainObject> = emptyList()): Single<List<AssetAmount>> = when {
    Account.isValidName(accountReference) -> GetNamedAccountBalances(accountReference, assets).toRequest()
    else -> api.accountApi.getAccount(accountReference).flatMap { getBalance(it.id, assets) }
  }

  /**
   * get account balance by name.
   *
   * @param accountReference name, id or public key of the account
   * @param asset object id of the assets, 1.3.*
   *
   * @return amount for asset
   */
  fun getBalance(accountReference: String, asset: ChainObject): Single<AssetAmount> =
      getBalance(accountReference, listOf(asset)).map { it.single() }

  /**
   * Get account balance by id with asset.
   *
   * @param accountId id of the account
   * @param assetSymbols asset symbols, eg. DCT
   *
   * @return a list of pairs of assets to amounts
   */
  fun getBalanceWithAsset(accountId: ChainObject, assetSymbols: List<String>): Single<List<Pair<Asset, AssetAmount>>> =
      api.assetApi.lookupAssets(assetSymbols.toList()).flatMap { assets ->
        getBalance(accountId, assets.map { it.id }).map {
          it.map { balance -> assets.single { it.id == balance.assetId } to balance }
        }
      }

  /**
   * Get account balance by name.
   *
   * @param accountId id of the account
   * @param assetSymbol asset symbol, eg. DCT
   *
   * @return a pair of asset to amount
   */
  fun getBalanceWithAsset(accountId: ChainObject, assetSymbol: String = DCoreConstants.DCT_SYMBOL): Single<Pair<Asset, AssetAmount>> =
      getBalanceWithAsset(accountId, listOf(assetSymbol)).map { it.single() }

  /**
   * Get account balance by name.
   *
   * @param accountReference name, id or public key of the account
   * @param assetSymbols asset symbols, eg. DCT
   *
   * @return list of assets with amounts
   */
  fun getBalanceWithAsset(accountReference: String, assetSymbols: List<String>): Single<List<Pair<Asset, AssetAmount>>> =
      api.accountApi.getAccount(accountReference).flatMap { getBalanceWithAsset(it.id, assetSymbols) }

  /**
   * Get account balance by name.
   *
   * @param accountReference name, id or public key of the account
   * @param assetSymbol asset symbol, eg. DCT
   *
   * @return a pair of asset to amount
   */
  fun getBalanceWithAsset(accountReference: String, assetSymbol: String = DCoreConstants.DCT_SYMBOL): Single<Pair<Asset, AssetAmount>> =
      api.accountApi.getAccount(accountReference).flatMap { getBalanceWithAsset(it.id, assetSymbol) }

  /**
   * Get information about a vesting balance object.
   *
   * @param accountId id of the account
   *
   * @return a list of vesting balances with additional information
   */
  fun getVestingBalances(accountId: ChainObject): Single<List<VestingBalance>> = GetVestingBalances(accountId).toRequest()

}