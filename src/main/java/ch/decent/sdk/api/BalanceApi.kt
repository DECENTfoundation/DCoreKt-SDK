package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.Account
import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.net.model.ApiGroup
import ch.decent.sdk.net.model.request.GetAccountBalances
import ch.decent.sdk.net.model.request.GetNamedAccountBalances
import io.reactivex.Single
import java.math.BigInteger

class BalanceApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * get account balance by id
   *
   * @param account object id of the account, 1.2.*
   * @param assets object ids of the assets, 1.3.*
   *
   * @return list of amounts for different assets
   */
  @JvmOverloads
  fun getBalance(account: ChainObject, assets: List<ChainObject> = emptyList()): Single<List<AssetAmount>> = GetAccountBalances(account, assets).toRequest()

  /**
   * get account balance by name
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
   * get account balance by id with asset
   *
   * @param account id of the account
   * @param assetSymbols asset symbols, eg. DCT
   *
   * @return a list of pairs of assets to amounts
   */
  fun getBalanceWithAsset(account: ChainObject, assetSymbols: List<String>): Single<List<Pair<Asset, AssetAmount>>> =
      api.assetApi.lookupAssets(assetSymbols.toList()).flatMap { assets ->
        getBalance(account, assets.map { it.id }).map {
          it.map { balance -> assets.single { it.id == balance.assetId } to balance }
        }
      }

  /**
   * get account balance by name
   *
   * @param account id of the account
   * @param assetSymbol asset symbol, eg. DCT
   *
   * @return a pair of asset to amount
   */
  fun getBalanceWithAsset(account: ChainObject, assetSymbol: String = DCoreConstants.DCT_SYMBOL): Single<Pair<Asset, AssetAmount>> =
      getBalanceWithAsset(account, listOf(assetSymbol)).map { it.single() }

  /**
   * get account balance by name
   *
   * @param accountReference name, id or public key of the account
   * @param assetSymbols asset symbols, eg. DCT
   *
   * @return list of assets with amounts
   */
  fun getBalanceWithAsset(accountReference: String, assetSymbols: List<String>): Single<List<Pair<Asset, AssetAmount>>> =
      api.accountApi.getAccount(accountReference).flatMap { getBalanceWithAsset(it.id, assetSymbols) }

  /**
   * get account balance by name
   *
   * @param accountReference name, id or public key of the account
   * @param assetSymbol asset symbol, eg. DCT
   *
   * @return a pair of asset to amount
   */
  fun getBalanceWithAsset(accountReference: String, assetSymbol: String = DCoreConstants.DCT_SYMBOL): Single<Pair<Asset, AssetAmount>> =
      api.accountApi.getAccount(accountReference).flatMap { getBalanceWithAsset(it.id, assetSymbol) }

}