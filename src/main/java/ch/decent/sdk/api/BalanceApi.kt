package ch.decent.sdk.api

import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.ChainObject
import io.reactivex.Single
import java.math.BigDecimal
import java.math.BigInteger

interface BalanceApi {

  /**
   * get account balance by id
   *
   * @param account object id of the account, 1.2.*
   * @param assets object ids of the assets, 1.3.*
   * @return list of amounts for different assets
   */
  fun getBalance(account: ChainObject, assets: Set<ChainObject> = emptySet()): Single<List<AssetAmount>>

  /**
   * get account balance by name
   *
   * @param accountReference name, id or public key of the account
   * @param assets object ids of the assets, 1.3.*
   * @return list of amounts for different assets
   */
  fun getBalance(accountReference: String, assets: Set<ChainObject> = emptySet()): Single<List<AssetAmount>>

  /**
   * get account balance by name
   *
   * @param account id of the account
   * @param assetSymbols asset symbols, eg. DCT
   * @return list of assets with amounts
   */
  fun getBalanceWithAsset(account: ChainObject, assetSymbols: Set<String> = emptySet()): Single<Map<Asset, BigInteger>>

  /**
   * get account balance by name
   *
   * @param accountReference name, id or public key of the account
   * @param assetSymbols asset symbols, eg. DCT
   * @return list of assets with amounts
   */
  fun getBalanceWithAsset(accountReference: String, assetSymbols: Set<String> = emptySet()): Single<Map<Asset, BigInteger>>

}