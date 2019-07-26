@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.Nft
import ch.decent.sdk.model.NftData
import ch.decent.sdk.model.NftModel
import ch.decent.sdk.model.NftOptions
import ch.decent.sdk.model.RawNft
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.TransactionDetail
import ch.decent.sdk.model.operation.NftCreateOperation
import ch.decent.sdk.model.operation.NftIssueOperation
import ch.decent.sdk.model.operation.NftTransferOperation
import ch.decent.sdk.model.operation.NftUpdateDataOperation
import ch.decent.sdk.model.operation.NftUpdateOperation
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.net.model.request.GetNftCount
import ch.decent.sdk.net.model.request.GetNftData
import ch.decent.sdk.net.model.request.GetNftDataCount
import ch.decent.sdk.net.model.request.GetNfts
import ch.decent.sdk.net.model.request.GetNftsBalances
import ch.decent.sdk.net.model.request.GetNftsBySymbol
import ch.decent.sdk.net.model.request.ListNftData
import ch.decent.sdk.net.model.request.ListNfts
import ch.decent.sdk.net.model.request.SearchNftHistory
import ch.decent.sdk.net.serialization.Variant
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import io.reactivex.Single
import kotlin.reflect.KClass

class NftApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get NFT by id or symbol
   *
   * @param idOrSymbol NFT object id or symbol
   *
   * @return NFT object, or [ObjectNotFoundException] if none found
   */
  fun get(idOrSymbol: String): Single<Nft> =
      if (ChainObject.isValid(idOrSymbol)) get(idOrSymbol.toChainObject())
      else getBySymbol(idOrSymbol)

  /**
   * Get NFTs by id
   *
   * @param ids NFT object ids
   *
   * @return NFT objects, or [ObjectNotFoundException] if none found
   */
  fun getAll(ids: List<ChainObject>): Single<List<Nft>> = GetNfts(ids).toRequest()

  /**
   * Get NFT by id
   *
   * @param id NFT object id
   *
   * @return NFT object, or [ObjectNotFoundException] if none found
   */
  fun get(id: ChainObject): Single<Nft> = getAll(listOf(id)).map { it.single() }

  /**
   * Get NFTs by symbol
   *
   * @param symbols NFT symbols
   *
   * @return NFT objects, or [ObjectNotFoundException] if none found
   */
  fun getAllBySymbol(symbols: List<String>): Single<List<Nft>> = GetNftsBySymbol(symbols).toRequest()

  /**
   * Get NFT by symbol
   *
   * @param symbol NFT symbol
   *
   * @return NFT object, or [ObjectNotFoundException] if none found
   */
  fun getBySymbol(symbol: String): Single<Nft> = getAllBySymbol(listOf(symbol)).map { it.single() }

  /**
   * Get NFT data instances with parsed model
   *
   * @param ids NFT data object ids
   * @param clazz NFT data model class reference
   *
   * @return NFT data objects, or [ObjectNotFoundException] if none found
   */
  @JvmName("getAllDataKt")
  fun <T : NftModel> getAllData(ids: List<ChainObject>, clazz: KClass<T>): Single<List<NftData<T>>> = getAllDataRaw(ids).make(clazz)

  /**
   * Get NFT data instances with parsed model
   *
   * @param ids NFT data object ids
   * @param clazz NFT data model class reference
   *
   * @return NFT data objects, or [ObjectNotFoundException] if none found
   */
  fun <T : NftModel> getAllData(ids: List<ChainObject>, clazz: Class<T>): Single<List<NftData<T>>> = getAllData(ids, clazz.kotlin)

  /**
   * Get NFT data instances with registered model, use [DCoreApi.registerNft] to register nft model by object id,
   * if the model is not registered, [RawNft] will be used
   *
   * @param ids NFT data object ids
   *
   * @return NFT data objects, or [ObjectNotFoundException] if none found
   */
  fun getAllData(ids: List<ChainObject>): Single<List<NftData<out NftModel>>> = getAllDataRaw(ids).make()

  /**
   * Get NFT data instances with raw model
   *
   * @param ids NFT data object ids
   *
   * @return NFT data objects, or [ObjectNotFoundException] if none found
   */
  fun getAllDataRaw(ids: List<ChainObject>): Single<List<NftData<RawNft>>> = GetNftData(ids).toRequest()

  /**
   * Get NFT data instance with parsed model
   *
   * @param id NFT data object id
   * @param clazz NFT data model class reference
   *
   * @return NFT data object, or [ObjectNotFoundException] if none found
   */
  @JvmName("getDataKt")
  fun <T : NftModel> getData(id: ChainObject, clazz: KClass<T>): Single<NftData<T>> = getAllData(listOf(id), clazz).map { it.single() }

  /**
   * Get NFT data instance with parsed model
   *
   * @param id NFT data object id
   * @param clazz NFT data model class reference
   *
   * @return NFT data object, or [ObjectNotFoundException] if none found
   */
  fun <T : NftModel> getData(id: ChainObject, clazz: Class<T>): Single<NftData<T>> = getAllData(listOf(id), clazz).map { it.single() }

  /**
   * Get NFT data instances with registered model, use [DCoreApi.registerNfts] to register nft model by object id,
   * if the model is not registered, [RawNft] will be used
   *
   * @param id NFT data object id
   *
   * @return NFT data object, or [ObjectNotFoundException] if none found
   */
  fun getData(id: ChainObject): Single<NftData<out NftModel>> = getAllData(listOf(id)).map { it.single() }

  /**
   * Get NFT data instance with raw model
   *
   * @param id NFT data object id
   *
   * @return NFT data object, or [ObjectNotFoundException] if none found
   */
  fun getDataRaw(id: ChainObject): Single<NftData<RawNft>> = getAllDataRaw(listOf(id)).map { it.single() }

  /**
   * Count all NFTs
   *
   * @return count of NFT definitions
   */
  fun countAll(): Single<Long> = GetNftCount.toRequest()

  /**
   * Count all NFT data instances
   *
   * @return count of NFT data instances
   */
  fun countAllData(): Single<Long> = GetNftDataCount.toRequest()

  /**
   * Get NFT balances per account with raw model
   *
   * @param account account object id
   * @param nftIds NFT object ids to filter, or empty list to fetch all
   *
   * @return NFT data instances with raw model
   */
  @JvmOverloads
  fun getNftBalancesRaw(account: ChainObject, nftIds: List<ChainObject> = emptyList()): Single<List<NftData<RawNft>>> =
      GetNftsBalances(account, nftIds).toRequest()

  /**
   * Get NFT balances per account with registered model, use [DCoreApi.registerNfts] to register nft model by object id,
   * if the model is not registered, [RawNft] will be used
   *
   * @param account account object id
   * @param nftIds NFT object ids to filter, or empty list to fetch all
   *
   * @return NFT data instances
   */
  @JvmOverloads
  fun getNftBalances(account: ChainObject, nftIds: List<ChainObject> = emptyList()): Single<List<NftData<out NftModel>>> =
      getNftBalancesRaw(account, nftIds).make()

  /**
   * Get NFT balances per account with parsed model
   *
   * @param account account object id
   * @param nftId NFT object id
   * @param clazz NFT data model class reference
   *
   * @return NFT data instances
   */
  @JvmName("getNftBalancesKt")
  fun <T : NftModel> getNftBalances(account: ChainObject, nftId: ChainObject, clazz: KClass<T>): Single<List<NftData<T>>> =
      getNftBalancesRaw(account, listOf(nftId)).make(clazz)

  /**
   * Get NFT balances per account with parsed model
   *
   * @param account account object id
   * @param nftId NFT object id
   * @param clazz NFT data model class reference
   *
   * @return NFT data instances
   */
  fun <T : NftModel> getNftBalances(account: ChainObject, nftId: ChainObject, clazz: Class<T>): Single<List<NftData<T>>> =
      getNftBalances(account, nftId, clazz.kotlin)

  /**
   * Get NFTs alphabetically by symbol name
   *
   * @param lowerBound lower bound of symbol names to retrieve
   * @param limit maximum number of NFTs to fetch (must not exceed 100)
   *
   * @return the NFTs found
   */
  @JvmOverloads
  fun listAllRelative(lowerBound: String = "", limit: Int = REQ_LIMIT_MAX): Single<List<Nft>> =
      ListNfts(lowerBound, limit).toRequest()

  /**
   * Get NFT data instances with parsed model
   *
   * @param nftId NFT object id
   * @param clazz NFT data model class reference
   *
   * @return NFT data objects
   */
  @JvmName("listDataByNftKt")
  fun <T : NftModel> listDataByNft(nftId: ChainObject, clazz: KClass<T>): Single<List<NftData<T>>> = listDataByNftRaw(nftId).make(clazz)

  /**
   * Get NFT data instances with parsed model
   *
   * @param nftId NFT object id
   * @param clazz NFT data model class reference
   *
   * @return NFT data objects
   */
  fun <T : NftModel> listDataByNft(nftId: ChainObject, clazz: Class<T>): Single<List<NftData<T>>> = listDataByNft(nftId, clazz.kotlin)

  /**
   * Get NFT data instances with registered model, use [DCoreApi.registerNfts] to register nft model by object id,
   * if the model is not registered, [RawNft] will be used
   *
   * @param nftId NFT object id
   *
   * @return NFT data objects
   */
  fun listDataByNft(nftId: ChainObject): Single<List<NftData<out NftModel>>> = listDataByNftRaw(nftId).make()

  /**
   * Get NFT data instances with raw model
   *
   * @param nftId NFT object id
   *
   * @return NFT data objects
   */
  fun listDataByNftRaw(nftId: ChainObject): Single<List<NftData<RawNft>>> = ListNftData(nftId).toRequest()

  /**
   * Search nft history, lists transfer and issue operations for nft data instance object id
   *
   * @param nftDataId NFT data object id
   *
   * @return transaction detail list
   */
  fun searchNftHistory(nftDataId: ChainObject): Single<List<TransactionDetail>> = SearchNftHistory(nftDataId).toRequest()

  /**
   * Create NFT create operation
   *
   * @param symbol NFT symbol
   * @param options NFT options
   * @param clazz NFT model data class reference
   * @param transferable allow transfer of NFT data instances to other accounts
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  @JvmName("createNftCreateOperationKt")
  fun <T : NftModel> createNftCreateOperation(
      symbol: String,
      options: NftOptions,
      clazz: KClass<T>,
      transferable: Boolean,
      fee: Fee = Fee()
  ): Single<NftCreateOperation> = Single.just(NftCreateOperation(symbol, options, NftModel.createDefinitions(clazz), transferable, fee))

  /**
   * Create NFT create operation
   *
   * @param symbol NFT symbol
   * @param options NFT options
   * @param clazz NFT model data class reference
   * @param transferable allow transfer of NFT data instances to other accounts
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun <T : NftModel> createNftCreateOperation(
      symbol: String,
      options: NftOptions,
      clazz: Class<T>,
      transferable: Boolean,
      fee: Fee = Fee()
  ): Single<NftCreateOperation> = createNftCreateOperation(symbol, options, clazz.kotlin, transferable, fee)

  /**
   * Create NFT
   *
   * @param credentials account credentials issuing the NFT
   * @param symbol NFT symbol
   * @param maxSupply NFT max supply
   * @param fixedMaxSupply NFT max supply is fixed and cannot be changed with update
   * @param description text description
   * @param clazz NFT model data class reference
   * @param transferable allow transfer of NFT data instances to other accounts
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  @JvmName("createKt")
  fun <T : NftModel> create(
      credentials: Credentials,
      symbol: String,
      maxSupply: Long,
      fixedMaxSupply: Boolean,
      description: String,
      clazz: KClass<T>,
      transferable: Boolean,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> =
      createNftCreateOperation(symbol, NftOptions(credentials.account, maxSupply, fixedMaxSupply, description), clazz, transferable, fee)
          .broadcast(credentials)

  /**
   * Create NFT
   *
   * @param credentials account credentials issuing the NFT
   * @param symbol NFT symbol
   * @param maxSupply NFT max supply
   * @param fixedMaxSupply NFT max supply is fixed and cannot be changed with update
   * @param description text description
   * @param clazz NFT model data class reference
   * @param transferable allow transfer of NFT data instances to other accounts
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun <T : NftModel> create(
      credentials: Credentials,
      symbol: String,
      maxSupply: Long,
      fixedMaxSupply: Boolean,
      description: String,
      clazz: Class<T>,
      transferable: Boolean,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = create(credentials, symbol, maxSupply, fixedMaxSupply, description, clazz.kotlin, transferable, fee)

  /**
   * Create NFT update operation. Fills model with actual values.
   *
   * @param idOrSymbol NFT object id or symbol
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun createUpdateOperation(
      idOrSymbol: String,
      fee: Fee = Fee()
  ): Single<NftUpdateOperation> = get(idOrSymbol).map { NftUpdateOperation(it.options.issuer, it.id, it.options, fee) }

  /**
   * Update NFT
   *
   * @param credentials issuer account credentials
   * @param idOrSymbol NFT object id or symbol
   * @param maxSupply update max supply
   * @param fixedMaxSupply update max supply is fixed
   * @param description update text description
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun update(
      credentials: Credentials,
      idOrSymbol: String,
      maxSupply: Long? = null,
      fixedMaxSupply: Boolean? = null,
      description: String? = null,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createUpdateOperation(idOrSymbol, fee)
      .doOnSuccess { it.options = it.options.update(maxSupply, description, fixedMaxSupply) }
      .broadcast(credentials)

  /**
   * Create NFT issue operation. Creates a NFT data instance.
   *
   * @param issuer NFT issuer
   * @param idOrSymbol NFT object id or symbol
   * @param to account object id receiving the NFT data instance
   * @param data data model with values
   * @param memo optional message
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun <T : NftModel> createIssueOperation(
      issuer: ChainObject,
      idOrSymbol: String,
      to: ChainObject,
      data: T? = null,
      memo: Memo? = null,
      fee: Fee = Fee()
  ): Single<NftIssueOperation> = getId(idOrSymbol).map { NftIssueOperation(issuer, it, to, data?.values() ?: emptyList(), memo, fee) }

  /**
   * Issue NFT. Creates a NFT data instance.
   *
   * @param credentials NFT issuer credentials
   * @param idOrSymbol NFT object id or symbol
   * @param to account object id receiving the NFT data instance
   * @param data data model with values
   * @param memo optional message
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun <T : NftModel> issue(
      credentials: Credentials,
      idOrSymbol: String,
      to: ChainObject,
      data: T? = null,
      memo: Memo? = null,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createIssueOperation(credentials.account, idOrSymbol, to, data, memo, fee)
      .broadcast(credentials)

  /**
   * Create NFT data instance transfer operation
   *
   * @param from NFT data instance owner account object id
   * @param to receiver account object id
   * @param id NFT data instance object id
   * @param memo optional message
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun createTransferOperation(
      from: ChainObject,
      to: ChainObject,
      id: ChainObject,
      memo: Memo? = null,
      fee: Fee = Fee()
  ): Single<NftTransferOperation> = Single.just(NftTransferOperation(from, to, id, memo, fee))

  /**
   * Transfer NFT data instance
   *
   * @param credentials NFT data instance owner credentials
   * @param to receiver account object id
   * @param id NFT data instance object id
   * @param memo optional message
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun transfer(
      credentials: Credentials,
      to: ChainObject,
      id: ChainObject,
      memo: Memo? = null,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createTransferOperation(credentials.account, to, id, memo, fee)
      .broadcast(credentials)

  /**
   * Create NFT data instance update operation
   *
   * @param modifier NFT data instance owner account object id, updatable values are set in [NftUpdateDataOperation.data] map
   * @param id NFT data instance object id
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun createUpdateDataOperation(
      modifier: ChainObject,
      id: ChainObject,
      fee: Fee = Fee()
  ): Single<NftUpdateDataOperation> = getDataRaw(id).flatMap { data ->
    get(data.nftId).map {
      val toUpdate = data.data?.createUpdate(it)
      requireNotNull(toUpdate) { "no values to update" }
      NftUpdateDataOperation(modifier, data.id, toUpdate.toMutableMap(), fee)
    }
  }

  /**
   * Create NFT data instance update operation
   *
   * @param modifier NFT data instance owner account object id
   * @param id NFT data instance object id
   * @param newData data model with values, updatable values are set to [NftUpdateDataOperation.data] map
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun <T : NftModel> createUpdateDataOperation(
      modifier: ChainObject,
      id: ChainObject,
      newData: T,
      fee: Fee = Fee()
  ): Single<NftUpdateDataOperation> =
      Single.just(NftUpdateDataOperation(modifier, id, newData.createUpdate().toMutableMap(), fee))

  /**
   * Update NFT data instance
   *
   * @param credentials NFT data instance credentials
   * @param id NFT data instance object id
   * @param values map of field name to value which should be updated
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun updateData(
      credentials: Credentials,
      id: ChainObject,
      values: Map<String, Variant>,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createUpdateDataOperation(credentials.account, id, fee)
      .doOnSuccess { it.data.putAll(values) }
      .broadcast(credentials)

  /**
   * Update NFT data instance
   *
   * @param credentials NFT data instance credentials
   * @param id NFT data instance object id
   * @param newData data model with values
   * @param fee [Fee] fee for the operation, by default the fee will be computed in DCT asset.
   * When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough
   */
  @JvmOverloads
  fun <T : NftModel> updateData(
      credentials: Credentials,
      id: ChainObject,
      newData: T,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createUpdateDataOperation(credentials.account, id, newData, fee)
      .broadcast(credentials)

  private fun getId(idOrString: String): Single<ChainObject> =
      if (ChainObject.isValid(idOrString)) Single.just(idOrString.toChainObject())
      else getBySymbol(idOrString).map { it.id }

  private fun <T : NftModel> Single<List<NftData<RawNft>>>.make(clazz: KClass<T>) =
      map { it.map { nft -> NftData(nft, clazz) } }

  private fun Single<List<NftData<RawNft>>>.make() =
      map {
        it.map { nft ->
          val clazz = api.registeredNfts[nft.nftId]
          if (clazz != null) NftData(nft, clazz) else nft
        }
      }

}
