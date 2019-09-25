@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api.rx

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AmountWithAsset
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.AssetObjectId
import ch.decent.sdk.model.CddVestingPolicy
import ch.decent.sdk.model.CddVestingPolicyCreate
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.LinearVestingPolicy
import ch.decent.sdk.model.LinearVestingPolicyCreate
import ch.decent.sdk.model.StaticVariant2
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.VestingBalance
import ch.decent.sdk.model.VestingBalanceObjectId
import ch.decent.sdk.model.operation.VestingBalanceCreateOperation
import ch.decent.sdk.model.operation.VestingBalanceWithdrawOperation
import ch.decent.sdk.net.model.request.GetAccountBalances
import ch.decent.sdk.net.model.request.GetNamedAccountBalances
import ch.decent.sdk.net.model.request.GetVestingBalanceObjects
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
  fun get(accountId: AccountObjectId, asset: AssetObjectId): Single<AssetAmount> = getAll(accountId, listOf(asset)).map { it.single() }

  /**
   * Get account balance by id.
   *
   * @param accountId object id of the account, 1.2.*
   * @param assets object ids of the assets, 1.3.*
   *
   * @return list of amounts for different assets
   */
  @JvmOverloads
  fun getAll(accountId: AccountObjectId, assets: List<AssetObjectId> = emptyList()): Single<List<AssetAmount>> = GetAccountBalances(accountId, assets).toRequest()

  /**
   * get account balance by name.
   *
   * @param name account name
   * @param asset object id of the assets, 1.3.*
   *
   * @return amount for asset
   */
  fun get(name: String, asset: AssetObjectId): Single<AssetAmount> = getAll(name, listOf(asset)).map { it.single() }

  /**
   * Get account balance by name.
   *
   * @param name account name
   * @param assets object ids of the assets, 1.3.*
   *
   * @return list of amounts for different assets
   */
  @JvmOverloads
  fun getAll(name: String, assets: List<AssetObjectId> = emptyList()): Single<List<AssetAmount>> = GetNamedAccountBalances(name, assets).toRequest()

  /**
   * Get account balance by name.
   *
   * @param accountId id of the account
   * @param assetSymbol asset symbol, eg. DCT
   *
   * @return a pair of asset to amount
   */
  @JvmOverloads
  fun getWithAsset(accountId: AccountObjectId, assetSymbol: String = DCoreConstants.DCT_SYMBOL): Single<AmountWithAsset> =
      getAllWithAsset(accountId, listOf(assetSymbol)).map { it.single() }

  /**
   * Get account balance by id with asset.
   *
   * @param accountId id of the account
   * @param assetSymbols asset symbols, eg. DCT
   *
   * @return a list of pairs of assets to amounts
   */
  fun getAllWithAsset(accountId: AccountObjectId, assetSymbols: List<String>): Single<List<AmountWithAsset>> =
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
  @JvmOverloads
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
  fun getAllVestingByAccount(accountId: AccountObjectId): Single<List<VestingBalance>> = GetVestingBalances(accountId).toRequest()

  fun getAllVesting(ids: List<VestingBalanceObjectId>): Single<List<VestingBalance>> = GetVestingBalanceObjects(ids).toRequest()

  fun getVesting(id: VestingBalanceObjectId): Single<VestingBalance> = getAllVesting(listOf(id)).map { it.single() }

  fun createVestingBalanceCreateOperation(
      creator: AccountObjectId,
      owner: AccountObjectId,
      amount: AssetAmount,
      policy: LinearVestingPolicyCreate,
      fee: Fee = Fee()
  ): Single<VestingBalanceCreateOperation> = Single.just(VestingBalanceCreateOperation(creator, owner, amount, StaticVariant2(policy), fee))

  fun createVestingBalanceCreateOperation(
      creator: AccountObjectId,
      owner: AccountObjectId,
      amount: AssetAmount,
      policy: CddVestingPolicyCreate,
      fee: Fee = Fee()
  ): Single<VestingBalanceCreateOperation> = Single.just(VestingBalanceCreateOperation(creator, owner, amount, StaticVariant2(obj2 = policy), fee))

  fun createVestingBalance(
      credentials: Credentials,
      owner: AccountObjectId,
      amount: AssetAmount,
      policy: LinearVestingPolicyCreate,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createVestingBalanceCreateOperation(credentials.account, owner, amount, policy, fee)
      .broadcast(credentials)

  fun createVestingBalance(
      credentials: Credentials,
      owner: AccountObjectId,
      amount: AssetAmount,
      policy: CddVestingPolicyCreate,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createVestingBalanceCreateOperation(credentials.account, owner, amount, policy, fee)
      .broadcast(credentials)

  fun createVestingBalanceWithdrawOperation(
      id: VestingBalanceObjectId,
      amount: AssetAmount,
      fee: Fee = Fee()
  ): Single<VestingBalanceWithdrawOperation> = getVesting(id).map { VestingBalanceWithdrawOperation(id, it.owner, amount) }

  fun withdrawVestingBalance(
      credentials: Credentials,
      id: VestingBalanceObjectId,
      amount: AssetAmount,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = VestingBalanceWithdrawOperation(id, credentials.account, amount, fee)
      .broadcast(credentials)

}
