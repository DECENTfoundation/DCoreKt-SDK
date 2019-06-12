@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.GenericNft
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.Nft
import ch.decent.sdk.model.NftData
import ch.decent.sdk.model.NftDataType
import ch.decent.sdk.model.NftModel
import ch.decent.sdk.model.NftOptions
import ch.decent.sdk.model.TransactionConfirmation
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
import ch.decent.sdk.net.serialization.Variant
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import io.reactivex.Single
import java.math.BigInteger
import kotlin.reflect.KClass

class NftApi internal constructor(api: DCoreApi) : BaseApi(api) {

/*
  fun <T : NftModel> registerNft(id: ChainObject, clazz: KClass<T>) {
//    NftTypeFactory.idToModel[id] = clazz
  }

  fun <T : NftModel> registerNft(id: ChainObject, clazz: Class<T>) {
//    NftTypeFactory.idToModel[id] = clazz.kotlin
  }
*/

  // ONFE
  fun get(idOrSymbol: String) =
      if (ChainObject.isValid(idOrSymbol)) get(idOrSymbol.toChainObject())
      else getBySymbol(idOrSymbol)

  // ONFE
  fun getAll(ids: List<ChainObject>): Single<List<Nft>> = GetNfts(ids).toRequest()

  // ONFE
  fun get(id: ChainObject): Single<Nft> = getAll(listOf(id)).map { it.single() }

  // ONFE
  fun getAllBySymbol(symbols: List<String>) = GetNftsBySymbol(symbols).toRequest()

  // ONFE
  fun getBySymbol(symbol: String) = getAllBySymbol(listOf(symbol)).map { it.single() }

  // ONFE
  fun <T : NftModel> getAllData(ids: List<ChainObject>, clazz: KClass<T>): Single<List<NftData<T>>> = GetNftData(ids).toRequest().make(clazz)

  // ONFE
  fun <T : NftModel> getAllData(ids: List<ChainObject>, clazz: Class<T>): Single<List<NftData<T>>> = getAllData(ids, clazz.kotlin)

  // ONFE
  fun getAllData(ids: List<ChainObject>): Single<List<NftData<GenericNft>>> = GetNftData(ids).toRequest()

  // ONFE
  fun <T : NftModel> getData(id: ChainObject, clazz: KClass<T>): Single<NftData<T>> = getAllData(listOf(id), clazz).map { it.single() }

  // ONFE
  fun <T : NftModel> getData(id: ChainObject, clazz: Class<T>): Single<NftData<T>> = getAllData(listOf(id), clazz).map { it.single() }

  // ONFE
  fun getData(id: ChainObject): Single<NftData<GenericNft>> = getAllData(listOf(id)).map { it.single() }

  fun countAll(): Single<BigInteger> = GetNftCount.toRequest()

  fun countAllData(): Single<BigInteger> = GetNftDataCount.toRequest()

  fun getNftBalances(account: ChainObject, nftIds: List<ChainObject> = emptyList()): Single<List<NftData<GenericNft>>> =
      GetNftsBalances(account, nftIds).toRequest()

  fun <T : NftModel> getNftBalances(account: ChainObject, nftId: ChainObject, clazz: KClass<T>): Single<List<NftData<T>>> =
      GetNftsBalances(account, listOf(nftId)).toRequest().make(clazz)

  fun <T : NftModel> getNftBalances(account: ChainObject, nftId: ChainObject, clazz: Class<T>): Single<List<NftData<T>>> =
      getNftBalances(account, nftId, clazz.kotlin)

  fun listAllRelative(lowerBound: String = "", limit: Int = REQ_LIMIT_MAX): Single<List<Nft>> =
      ListNfts(lowerBound, limit).toRequest()

  fun <T : NftModel> getDataByNft(nftId: ChainObject, clazz: KClass<T>): Single<List<NftData<T>>> = ListNftData(nftId).toRequest().make(clazz)

  fun <T : NftModel> getDataByNft(nftId: ChainObject, clazz: Class<T>): Single<List<NftData<T>>> = getDataByNft(nftId, clazz.kotlin)

  fun getDataByNft(nftId: ChainObject): Single<List<NftData<GenericNft>>> = ListNftData(nftId).toRequest()

  fun createNftCreateOperation(
      symbol: String,
      options: NftOptions,
      definitions: List<NftDataType>,
      transferable: Boolean,
      fee: Fee = Fee()
  ): Single<NftCreateOperation> = Single.just(NftCreateOperation(symbol, options, definitions, transferable, fee))

  fun create(
      credentials: Credentials,
      symbol: String,
      maxSupply: Long,
      fixedMaxSupply: Boolean,
      description: String,
      definitions: List<NftDataType>,
      transferable: Boolean,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> =
      createNftCreateOperation(symbol, NftOptions(credentials.account, maxSupply, fixedMaxSupply, description), definitions, transferable, fee)
          .broadcast(credentials)

  fun createUpdateOperation(
      idOrSymbol: String,
      fee: Fee = Fee()
  ): Single<NftUpdateOperation> = get(idOrSymbol).map { NftUpdateOperation(it.options.issuer, it.id, it.options, fee) }

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

  fun <T : NftModel> createIssueOperation(
      issuer: ChainObject,
      idOrSymbol: String,
      to: ChainObject,
      data: T? = null,
      memo: Memo? = null,
      fee: Fee = Fee()
  ): Single<NftIssueOperation> = get(idOrSymbol).map { NftIssueOperation(issuer, it.id, to, data?.values() ?: emptyList(), memo, fee) }

  fun <T : NftModel> issue(
      credentials: Credentials,
      idOrSymbol: String,
      to: ChainObject,
      data: T? = null,
      memo: Memo? = null,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createIssueOperation(credentials.account, idOrSymbol, to, data, memo, fee)
      .broadcast(credentials)

  fun createTransferOperation(
      from: ChainObject,
      to: ChainObject,
      id: ChainObject,
      memo: Memo?,
      fee: Fee = Fee()
  ): Single<NftTransferOperation> = Single.just(NftTransferOperation(from, to, id, memo, fee))

  fun transfer(
      credentials: Credentials,
      to: ChainObject,
      id: ChainObject,
      memo: Memo? = null,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createTransferOperation(credentials.account, to, id, memo, fee)
      .broadcast(credentials)

  fun createUpdateDataOperation(
      modifier: ChainObject,
      id: ChainObject,
      fee: Fee = Fee()
  ): Single<NftUpdateDataOperation> = getData(id).flatMap { data ->
    get(data.nftId).map {
      val toUpdate = data.data?.createUpdate(it)
      requireNotNull(toUpdate) { "no values to update" }
      NftUpdateDataOperation(modifier, data.id, toUpdate.toMutableMap(), fee)
    }
  }

  fun <T : NftModel> createUpdateDataOperation(
      modifier: ChainObject,
      id: ChainObject,
      newData: T,
      fee: Fee = Fee()
  ): Single<NftUpdateDataOperation> =
      Single.just(NftUpdateDataOperation(modifier, id, newData.createUpdate().toMutableMap(), fee))

  fun updateData(
      credentials: Credentials,
      id: ChainObject,
      values: Map<String, Variant>,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createUpdateDataOperation(credentials.account, id, fee)
      .doOnSuccess { it.data.putAll(values) }
      .broadcast(credentials)

  fun <T : NftModel> updateData(
      credentials: Credentials,
      id: ChainObject,
      newData: T,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createUpdateDataOperation(credentials.account, id, newData, fee)
      .broadcast(credentials)

  fun <T : NftModel> Single<List<NftData<GenericNft>>>.make(clazz: KClass<T>) =
      map { it.map { nft -> NftData(nft.id, nft.nftId, nft.owner, nft.data?.make(clazz)) } }

}
