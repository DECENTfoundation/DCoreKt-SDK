@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetDataObjectId
import ch.decent.sdk.model.AssetObjectId
import ch.decent.sdk.model.AssetOptions
import ch.decent.sdk.model.ExchangeRateValues
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.MonitoredAssetOptions
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import java.math.RoundingMode
import kotlin.Boolean
import kotlin.Byte
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List

class AssetApi internal constructor(
  private val api: ch.decent.sdk.api.AssetApi
) {
  fun get(assetId: AssetObjectId) = api.get(assetId).blockingGet()
  fun getAll(assetIds: List<AssetObjectId>) = api.getAll(assetIds).blockingGet()
  fun getRealSupply() = api.getRealSupply().blockingGet()
  fun getAssetsData(assetId: List<AssetDataObjectId>) = api.getAssetsData(assetId).blockingGet()
  fun getAssetData(assetId: AssetDataObjectId) = api.getAssetData(assetId).blockingGet()
  fun listAllRelative(lowerBound: String, limit: Int = REQ_LIMIT_MAX) =
      api.listAllRelative(lowerBound, limit).blockingGet()
  fun listAll(includeMonitored: Boolean = false) = api.listAll(includeMonitored).blockingGet()
  fun getByName(assetSymbol: String) = api.getByName(assetSymbol).blockingGet()
  fun getAllByName(assetSymbols: List<String>) = api.getAllByName(assetSymbols).blockingGet()
  fun convertFromDCT(
    assetId: AssetObjectId,
    amount: Long,
    roundingMode: RoundingMode = RoundingMode.CEILING
  ) = api.convertFromDCT(assetId, amount, roundingMode).blockingGet()
  fun convertToDCT(
    assetId: AssetObjectId,
    amount: Long,
    roundingMode: RoundingMode = RoundingMode.CEILING
  ) = api.convertToDCT(assetId, amount, roundingMode).blockingGet()
  fun createAssetCreateOperation(
    issuer: AccountObjectId,
    symbol: String,
    precision: Byte,
    description: String,
    options: AssetOptions,
    monitoredOptions: MonitoredAssetOptions? = null,
    fee: Fee = Fee()
  ) = api.createAssetCreateOperation(issuer, symbol, precision, description, options,
      monitoredOptions, fee).blockingGet()
  fun create(
    credentials: Credentials,
    symbol: String,
    precision: Byte,
    description: String,
    options: AssetOptions = AssetOptions(),
    fee: Fee = Fee()
  ) = api.create(credentials, symbol, precision, description, options, fee).blockingGet()
  fun createMonitoredAsset(
    credentials: Credentials,
    symbol: String,
    precision: Byte,
    description: String,
    options: MonitoredAssetOptions = MonitoredAssetOptions(),
    fee: Fee = Fee()
  ) = api.createMonitoredAsset(credentials, symbol, precision, description, options,
      fee).blockingGet()
  fun createAssetUpdateOperation(
    assetIdOrSymbol: String,
    newIssuer: AccountObjectId? = null,
    fee: Fee = Fee()
  ) = api.createAssetUpdateOperation(assetIdOrSymbol, newIssuer, fee).blockingGet()
  fun update(
    credentials: Credentials,
    assetIdOrSymbol: String,
    exchangeRate: ExchangeRateValues? = null,
    description: String? = null,
    exchangeable: Boolean? = null,
    maxSupply: Long? = null,
    newIssuer: AccountObjectId? = null,
    fee: Fee = Fee()
  ) = api.update(credentials, assetIdOrSymbol, exchangeRate, description, exchangeable, maxSupply,
      newIssuer, fee).blockingGet()
  fun createAssetUpdateAdvancedOperation(assetIdOrSymbol: String, fee: Fee = Fee()) =
      api.createAssetUpdateAdvancedOperation(assetIdOrSymbol, fee).blockingGet()
  fun updateAdvanced(
    credentials: Credentials,
    assetIdOrSymbol: String,
    precision: Byte? = null,
    fixedMaxSupply: Boolean? = null,
    fee: Fee = Fee()
  ) = api.updateAdvanced(credentials, assetIdOrSymbol, precision, fixedMaxSupply, fee).blockingGet()
  fun createAssetIssueOperation(
    assetIdOrSymbol: String,
    amount: Long,
    to: AccountObjectId? = null,
    memo: Memo? = null,
    fee: Fee = Fee()
  ) = api.createAssetIssueOperation(assetIdOrSymbol, amount, to, memo, fee).blockingGet()
  fun issue(
    credentials: Credentials,
    assetIdOrSymbol: String,
    amount: Long,
    to: AccountObjectId? = null,
    memo: Memo? = null,
    fee: Fee = Fee()
  ) = api.issue(credentials, assetIdOrSymbol, amount, to, memo, fee).blockingGet()
  fun createFundPoolsOperation(
    assetIdOrSymbol: String,
    uia: Long,
    dct: Long,
    fee: Fee = Fee()
  ) = api.createFundPoolsOperation(assetIdOrSymbol, uia, dct, fee).blockingGet()
  fun fund(
    credentials: Credentials,
    assetIdOrSymbol: String,
    uia: Long,
    dct: Long,
    fee: Fee = Fee()
  ) = api.fund(credentials, assetIdOrSymbol, uia, dct, fee).blockingGet()
  fun createClaimFeesOperation(
    assetIdOrSymbol: String,
    uia: Long,
    dct: Long,
    fee: Fee = Fee()
  ) = api.createClaimFeesOperation(assetIdOrSymbol, uia, dct, fee).blockingGet()
  fun claim(
    credentials: Credentials,
    assetIdOrSymbol: String,
    uia: Long,
    dct: Long,
    fee: Fee = Fee()
  ) = api.claim(credentials, assetIdOrSymbol, uia, dct, fee).blockingGet()
  fun createReserveOperation(
    assetIdOrSymbol: String,
    amount: Long,
    fee: Fee = Fee()
  ) = api.createReserveOperation(assetIdOrSymbol, amount, fee).blockingGet()
  fun reserve(
    credentials: Credentials,
    assetIdOrSymbol: String,
    amount: Long,
    fee: Fee = Fee()
  ) = api.reserve(credentials, assetIdOrSymbol, amount, fee).blockingGet()}
