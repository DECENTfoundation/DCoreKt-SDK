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
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import io.reactivex.Single
import java.math.BigInteger

class NftApi internal constructor(api: DCoreApi) : BaseApi(api) {
// ONFE
  fun get(idOrSymbol: String) =
      if (ChainObject.isValid(idOrSymbol)) get(idOrSymbol.toChainObject())
      else getBySymbol(idOrSymbol)

// ONFE
  fun getAll(ids: List<ChainObject>): Single<List<Nft>> = GetNfts(ids).toRequest()

// ONFE
  fun get(id: ChainObject): Single<Nft> = getAll(listOf(id)).map { it.single() }

// ONFE
  fun getAllBySymbols(symbols: List<String>) = GetNftsBySymbol(symbols).toRequest()

// ONFE
  fun getBySymbol(symbol: String) = getAllBySymbols(listOf(symbol)).map { it.single() }

// ONFE
  fun <T : NftModel> getAllData(clazz: Class<T>, ids: List<ChainObject>): Single<List<NftData<T>>> = GetNftData<T>(ids).toRequest()

// ONFE
  fun <T : NftModel> getData(clazz: Class<T>, id: ChainObject): Single<NftData<T>> = getAllData(clazz, listOf(id)).map { it.single() }

  fun countAll(): Single<BigInteger> = GetNftCount.toRequest()

  fun countAllData(): Single<BigInteger> = GetNftDataCount.toRequest()

  fun <T : NftModel> getNftBalances(account: ChainObject, nftIds: List<ChainObject> = emptyList()): Single<List<NftData<T>>> =
      GetNftsBalances<T>(account, nftIds).toRequest()

  fun listAllRelative(lowerBound: String, limit: Int = REQ_LIMIT_MAX): Single<List<Nft>> =
      ListNfts(lowerBound, limit).toRequest()

  fun <T : NftModel> getDataByNft(nftId: ChainObject): Single<List<NftData<T>>> = ListNftData<T>(nftId).toRequest()

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

}
