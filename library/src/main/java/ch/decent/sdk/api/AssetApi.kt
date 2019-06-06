@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Asset
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.AssetData
import ch.decent.sdk.model.AssetDataObjectId
import ch.decent.sdk.model.AssetObjectId
import ch.decent.sdk.model.AssetOptions
import ch.decent.sdk.model.ExchangeRate
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.MonitoredAssetOptions
import ch.decent.sdk.model.RealSupply
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.isValidId
import ch.decent.sdk.model.operation.AssetClaimFeesOperation
import ch.decent.sdk.model.operation.AssetCreateOperation
import ch.decent.sdk.model.operation.AssetFundPoolsOperation
import ch.decent.sdk.model.operation.AssetIssueOperation
import ch.decent.sdk.model.operation.AssetReserveOperation
import ch.decent.sdk.model.operation.AssetUpdateAdvancedOperation
import ch.decent.sdk.model.operation.AssetUpdateOperation
import ch.decent.sdk.model.toObjectId
import ch.decent.sdk.net.model.request.GetAssets
import ch.decent.sdk.net.model.request.GetAssetsData
import ch.decent.sdk.net.model.request.GetRealSupply
import ch.decent.sdk.net.model.request.ListAssets
import ch.decent.sdk.net.model.request.LookupAssets
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import io.reactivex.Single
import java.math.RoundingMode

class AssetApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get asset by id.
   *
   * @param assetId asset id eg. DCT id is 1.3.0
   *
   * @return asset or [ObjectNotFoundException]
   */
  fun get(assetId: AssetObjectId): Single<Asset> = getAll(listOf(assetId)).map { it.single() }

  /**
   * Get assets by id.
   *
   * @param assetIds asset id eg. DCT id is 1.3.0
   *
   * @return list of assets or [ObjectNotFoundException]
   */
  fun getAll(assetIds: List<AssetObjectId>): Single<List<Asset>> = GetAssets(assetIds).toRequest()

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
  fun getAssetsData(assetId: List<AssetDataObjectId>): Single<List<AssetData>> = GetAssetsData(assetId).toRequest()

  /**
   * Get asset dynamic data by id.
   *
   * @param assetId asset data id eg. DCT id is 2.3.0
   *
   * @return asset dynamic data or [ObjectNotFoundException]
   */
  fun getAssetData(assetId: AssetDataObjectId): Single<AssetData> = getAssetsData(listOf(assetId)).map { it.single() }

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
   * Get asset by id and convert amount in DCT to this asset
   *
   * @param assetId asset id to get
   * @param amount amount to convert
   * @param roundingMode rounding mode to use when rounding to target asset precision
   */
  @JvmOverloads
  fun convertFromDCT(assetId: AssetObjectId, amount: Long, roundingMode: RoundingMode = RoundingMode.CEILING): Single<AssetAmount> =
      get(assetId).map { it.convertFromDCT(amount, roundingMode) }

  /**
   * Get asset by id and convert amount in this asset to DCT
   *
   * @param assetId asset id to get
   * @param amount amount to convert
   * @param roundingMode rounding mode to use when rounding to target asset precision
   */
  @JvmOverloads
  fun convertToDCT(assetId: AssetObjectId, amount: Long, roundingMode: RoundingMode = RoundingMode.CEILING): Single<AssetAmount> =
      get(assetId).map { it.convertToDCT(amount, roundingMode) }


  /**
   * Create asset create operation.
   *
   * @param issuer account id issuing the asset
   * @param symbol the string symbol, 3-16 uppercase chars
   * @param precision base unit precision, decimal places used in string representation
   * @param description optional description
   * @param options asset options
   * @param monitoredOptions options for monitored asset
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun createAssetCreateOperation(
      issuer: AccountObjectId,
      symbol: String,
      precision: Byte,
      description: String,
      options: AssetOptions,
      monitoredOptions: MonitoredAssetOptions? = null,
      fee: Fee = Fee()
  ): Single<AssetCreateOperation> =
      Single.just(AssetCreateOperation(issuer, symbol, precision, description, options, monitoredOptions, fee))

  /**
   * Create a new Asset.
   *
   * @param credentials account credentials issuing the asset
   * @param symbol the string symbol, 3-16 uppercase chars
   * @param precision base unit precision, decimal places used in string representation
   * @param description optional description
   * @param options asset options
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun create(
      credentials: Credentials,
      symbol: String,
      precision: Byte,
      description: String,
      options: AssetOptions = AssetOptions(ExchangeRate.forCreateOp(1, 1)),
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> =
      createAssetCreateOperation(credentials.account, symbol, precision, description, options, fee = fee)
          .broadcast(credentials)

  /**
   * cannot create
   * asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; }
   * therefore throws Missing Active Authority 1.2.0
   */
  @JvmOverloads
  fun createMonitoredAsset(
      credentials: Credentials,
      symbol: String,
      precision: Byte,
      description: String,
      options: MonitoredAssetOptions = MonitoredAssetOptions(),
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> =
      createAssetCreateOperation(credentials.account, symbol, precision, description, AssetOptions(ExchangeRate.EMPTY, 0), options, fee)
          .broadcast(credentials)

  /**
   * Create update asset operation. Fills model with actual asset values.
   *
   * @param assetIdOrSymbol asset to update
   * @param newIssuer a new issuer account id
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun createAssetUpdateOperation(
      assetIdOrSymbol: String,
      newIssuer: AccountObjectId? = null,
      fee: Fee = Fee()
  ): Single<AssetUpdateOperation> = get(assetIdOrSymbol).map {
    AssetUpdateOperation(it.issuer, it.id, it.description, newIssuer, it.options.maxSupply, it.options.exchangeRate, it.options.exchangeable, fee)
  }

  /**
   * Update asset.
   *
   * @param credentials account credentials issuing the asset
   * @param assetIdOrSymbol asset to update
   * @param exchangeRate new exchange rate, DCT base amount to UIA quote amount pair
   * @param description new description
   * @param exchangeable enable converting the asset to DCT, so it can be used to pay for fees
   * @param maxSupply new max supply
   * @param newIssuer a new issuer account id
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun update(
      credentials: Credentials,
      assetIdOrSymbol: String,
      exchangeRate: Pair<Int, Int>? = null,
      description: String? = null,
      exchangeable: Boolean? = null,
      maxSupply: Long? = null,
      newIssuer: AccountObjectId? = null,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createAssetUpdateOperation(assetIdOrSymbol, newIssuer, fee)
      .map {
        it.apply {
          if (exchangeRate != null) this.coreExchangeRate =
              ExchangeRate(AssetAmount(exchangeRate.first.toLong()), AssetAmount(exchangeRate.second.toLong(), it.assetToUpdate))
          if (description != null) this.newDescription = description
          if (exchangeable != null) this.exchangeable = exchangeable
          if (maxSupply != null) this.maxSupply = maxSupply
        }
      }
      .broadcast(credentials)

  /**
   * Create update advanced options operation for the asset.
   *
   * @param assetIdOrSymbol asset to update
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun createAssetUpdateAdvancedOperation(
      assetIdOrSymbol: String,
      fee: Fee = Fee()
  ): Single<AssetUpdateAdvancedOperation> = get(assetIdOrSymbol)
      .map { AssetUpdateAdvancedOperation(it.issuer, it.id, it.precision, it.options.extensions?.isFixedMaxSupply ?: false, fee) }

  /**
   * Update advanced options for the asset.
   *
   * @param credentials account credentials issuing the asset
   * @param assetIdOrSymbol asset to update
   * @param precision new precision
   * @param fixedMaxSupply whether it should be allowed to change max supply, cannot be reverted once set to true
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun updateAdvanced(
      credentials: Credentials,
      assetIdOrSymbol: String,
      precision: Byte? = null,
      fixedMaxSupply: Boolean? = null,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createAssetUpdateAdvancedOperation(assetIdOrSymbol, fee)
      .map {
        it.apply {
          if (precision != null) this.precision = precision
          if (fixedMaxSupply != null) this.fixedMaxSupply = fixedMaxSupply
        }
      }
      .broadcast(credentials)

  /**
   * Create issue asset operation. Only the issuer of the asset can issue some funds until maxSupply is reached.
   *
   * @param assetIdOrSymbol asset to issue
   * @param amount raw amount to issue
   * @param to optional account id receiving the created funds, issuer account id is used if not defined
   * @param memo optional memo for receiver
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun createAssetIssueOperation(
      assetIdOrSymbol: String,
      amount: Long,
      to: AccountObjectId? = null,
      memo: Memo? = null,
      fee: Fee = Fee()
  ): Single<AssetIssueOperation> = get(assetIdOrSymbol).map { AssetIssueOperation(it.issuer, AssetAmount(amount, it.id), to ?: it.issuer, memo, fee) }

  /**
   * Issue asset. Only the issuer of the asset can issue some funds until maxSupply is reached.
   *
   * @param credentials account credentials issuing the asset
   * @param assetIdOrSymbol asset to issue
   * @param amount raw amount to issue
   * @param to optional account id receiving the created funds, issuer account id is used if not defined
   * @param memo optional memo for receiver
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun issue(
      credentials: Credentials,
      assetIdOrSymbol: String,
      amount: Long,
      to: AccountObjectId? = null,
      memo: Memo? = null,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createAssetIssueOperation(assetIdOrSymbol, amount, to, memo, fee)
      .broadcast(credentials)

  /**
   * Create fund asset pool operation. Any account can fund a pool.
   *
   * @param assetIdOrSymbol which asset to fund
   * @param uia UIA raw amount
   * @param dct DCT raw amount
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun createFundPoolsOperation(
      assetIdOrSymbol: String,
      uia: Long,
      dct: Long,
      fee: Fee = Fee()
  ): Single<AssetFundPoolsOperation> = get(assetIdOrSymbol).map { AssetFundPoolsOperation(it.issuer, AssetAmount(uia, it.id), AssetAmount(dct), fee) }

  /**
   * Fund asset pool. Any account can fund a pool.
   *
   * @param credentials account credentials funding the pool
   * @param assetIdOrSymbol which asset to fund
   * @param uia UIA raw amount
   * @param dct DCT raw amount
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun fund(
      credentials: Credentials,
      assetIdOrSymbol: String,
      uia: Long,
      dct: Long,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createFundPoolsOperation(assetIdOrSymbol, uia, dct, fee)
      .broadcast(credentials)

  /**
   * Create claim fees operation. Claim funds from asset pool, only the asset issuer can claim.
   *
   * @param assetIdOrSymbol which asset to claim from
   * @param uia UIA raw amount
   * @param dct DCT raw amount
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun createClaimFeesOperation(
      assetIdOrSymbol: String,
      uia: Long,
      dct: Long,
      fee: Fee = Fee()
  ): Single<AssetClaimFeesOperation> = get(assetIdOrSymbol).map { AssetClaimFeesOperation(it.issuer, AssetAmount(uia, it.id), AssetAmount(dct), fee) }

  /**
   * Claim fees. Claim funds from asset pool, only the asset issuer can claim.
   *
   * @param credentials account credentials issuing the asset
   * @param assetIdOrSymbol which asset to claim from
   * @param uia UIA raw amount
   * @param dct DCT raw amount
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun claim(
      credentials: Credentials,
      assetIdOrSymbol: String,
      uia: Long,
      dct: Long,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createClaimFeesOperation(assetIdOrSymbol, uia, dct, fee)
      .broadcast(credentials)

  /**
   * Create reserve funds operation. Return issued funds to the issuer of the asset.
   *
   * @param assetIdOrSymbol which asset to reserve from
   * @param amount raw amount to remove from current supply
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun createReserveOperation(
      assetIdOrSymbol: String,
      amount: Long,
      fee: Fee = Fee()
  ): Single<AssetReserveOperation> = get(assetIdOrSymbol).map { AssetReserveOperation(it.issuer, AssetAmount(amount, it.id), fee) }

  /**
   * Reserve funds. Return issued funds to the issuer of the asset.
   *
   * @param credentials account credentials returning the asset
   * @param assetIdOrSymbol which asset to reserve from
   * @param amount raw amount to remove from current supply
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun reserve(
      credentials: Credentials,
      assetIdOrSymbol: String,
      amount: Long,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createReserveOperation(assetIdOrSymbol, amount, fee)
      .broadcast(credentials)


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

  private fun get(idOrSymbol: String) = if (idOrSymbol.isValidId<AssetObjectId>()) get(idOrSymbol.toObjectId<AssetObjectId>()) else getByName(idOrSymbol)

}
