@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetObjectId
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.emptyList

class BalanceApi internal constructor(
  private val api: ch.decent.sdk.api.BalanceApi
) {
  fun get(accountId: AccountObjectId, asset: AssetObjectId) = api.get(accountId,
      asset).blockingGet()
  fun getAll(accountId: AccountObjectId, assets: List<AssetObjectId> = emptyList()) =
      api.getAll(accountId, assets).blockingGet()
  fun get(name: String, asset: AssetObjectId) = api.get(name, asset).blockingGet()
  fun getAll(name: String, assets: List<AssetObjectId> = emptyList()) = api.getAll(name,
      assets).blockingGet()
  fun getWithAsset(accountId: AccountObjectId, assetSymbol: String = DCoreConstants.DCT_SYMBOL) =
      api.getWithAsset(accountId, assetSymbol).blockingGet()
  fun getAllWithAsset(accountId: AccountObjectId, assetSymbols: List<String>) =
      api.getAllWithAsset(accountId, assetSymbols).blockingGet()
  fun getWithAsset(name: String, assetSymbol: String = DCoreConstants.DCT_SYMBOL) =
      api.getWithAsset(name, assetSymbol).blockingGet()
  fun getAllWithAsset(name: String, assetSymbols: List<String>) = api.getAllWithAsset(name,
      assetSymbols).blockingGet()
  fun getAllVesting(accountId: AccountObjectId) = api.getAllVesting(accountId).blockingGet()}
