@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.NftDataObjectId
import ch.decent.sdk.model.NftModel
import ch.decent.sdk.model.NftObjectId
import ch.decent.sdk.model.NftOptions
import ch.decent.sdk.net.serialization.Variant
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import java.lang.Class
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.emptyList
import kotlin.reflect.KClass

class NftApi internal constructor(
  private val api: ch.decent.sdk.api.NftApi
) {
  fun get(idOrSymbol: String) = api.get(idOrSymbol).blockingGet()
  fun getAll(ids: List<NftObjectId>) = api.getAll(ids).blockingGet()
  fun get(id: NftObjectId) = api.get(id).blockingGet()
  fun getAllBySymbol(symbols: List<String>) = api.getAllBySymbol(symbols).blockingGet()
  fun getBySymbol(symbol: String) = api.getBySymbol(symbol).blockingGet()
  fun <T : NftModel> getAllData(ids: List<NftDataObjectId>, clazz: KClass<T>) = api.getAllData(ids,
      clazz).blockingGet()
  fun <T : NftModel> getAllData(ids: List<NftDataObjectId>, clazz: Class<T>) = api.getAllData(ids,
      clazz).blockingGet()
  fun getAllData(ids: List<NftDataObjectId>) = api.getAllData(ids).blockingGet()
  fun getAllDataRaw(ids: List<NftDataObjectId>) = api.getAllDataRaw(ids).blockingGet()
  fun <T : NftModel> getData(id: NftDataObjectId, clazz: KClass<T>) = api.getData(id,
      clazz).blockingGet()
  fun <T : NftModel> getData(id: NftDataObjectId, clazz: Class<T>) = api.getData(id,
      clazz).blockingGet()
  fun getData(id: NftDataObjectId) = api.getData(id).blockingGet()
  fun getDataRaw(id: NftDataObjectId) = api.getDataRaw(id).blockingGet()
  fun countAll() = api.countAll().blockingGet()
  fun countAllData() = api.countAllData().blockingGet()
  fun getNftBalancesRaw(account: AccountObjectId, nftIds: List<NftObjectId> = emptyList()) =
      api.getNftBalancesRaw(account, nftIds).blockingGet()
  fun getNftBalances(account: AccountObjectId, nftIds: List<NftObjectId> = emptyList()) =
      api.getNftBalances(account, nftIds).blockingGet()
  fun <T : NftModel> getNftBalances(
    account: AccountObjectId,
    nftId: NftObjectId,
    clazz: KClass<T>
  ) = api.getNftBalances(account, nftId, clazz).blockingGet()
  fun <T : NftModel> getNftBalances(
    account: AccountObjectId,
    nftId: NftObjectId,
    clazz: Class<T>
  ) = api.getNftBalances(account, nftId, clazz).blockingGet()
  fun listAllRelative(lowerBound: String = "", limit: Int = REQ_LIMIT_MAX) =
      api.listAllRelative(lowerBound, limit).blockingGet()
  fun <T : NftModel> listDataByNft(nftId: NftObjectId, clazz: KClass<T>) = api.listDataByNft(nftId,
      clazz).blockingGet()
  fun <T : NftModel> listDataByNft(nftId: NftObjectId, clazz: Class<T>) = api.listDataByNft(nftId,
      clazz).blockingGet()
  fun listDataByNft(nftId: NftObjectId) = api.listDataByNft(nftId).blockingGet()
  fun listDataByNftRaw(nftId: NftObjectId) = api.listDataByNftRaw(nftId).blockingGet()
  fun searchNftHistory(nftDataId: NftDataObjectId) = api.searchNftHistory(nftDataId).blockingGet()
  fun <T : NftModel> createNftCreateOperation(
    symbol: String,
    options: NftOptions,
    clazz: KClass<T>,
    transferable: Boolean,
    fee: Fee = Fee()
  ) = api.createNftCreateOperation(symbol, options, clazz, transferable, fee).blockingGet()
  fun <T : NftModel> createNftCreateOperation(
    symbol: String,
    options: NftOptions,
    clazz: Class<T>,
    transferable: Boolean,
    fee: Fee = Fee()
  ) = api.createNftCreateOperation(symbol, options, clazz, transferable, fee).blockingGet()
  fun <T : NftModel> create(
    credentials: Credentials,
    symbol: String,
    maxSupply: Long,
    fixedMaxSupply: Boolean,
    description: String,
    clazz: KClass<T>,
    transferable: Boolean,
    fee: Fee = Fee()
  ) = api.create(credentials, symbol, maxSupply, fixedMaxSupply, description, clazz, transferable,
      fee).blockingGet()
  fun <T : NftModel> create(
    credentials: Credentials,
    symbol: String,
    maxSupply: Long,
    fixedMaxSupply: Boolean,
    description: String,
    clazz: Class<T>,
    transferable: Boolean,
    fee: Fee = Fee()
  ) = api.create(credentials, symbol, maxSupply, fixedMaxSupply, description, clazz, transferable,
      fee).blockingGet()
  fun createUpdateOperation(idOrSymbol: String, fee: Fee = Fee()) =
      api.createUpdateOperation(idOrSymbol, fee).blockingGet()
  fun update(
    credentials: Credentials,
    idOrSymbol: String,
    maxSupply: Long? = null,
    fixedMaxSupply: Boolean? = null,
    description: String? = null,
    fee: Fee = Fee()
  ) = api.update(credentials, idOrSymbol, maxSupply, fixedMaxSupply, description, fee).blockingGet()
  fun <T : NftModel> createIssueOperation(
    issuer: AccountObjectId,
    idOrSymbol: String,
    to: AccountObjectId,
    data: T? = null,
    memo: Memo? = null,
    fee: Fee = Fee()
  ) = api.createIssueOperation(issuer, idOrSymbol, to, data, memo, fee).blockingGet()
  fun <T : NftModel> issue(
    credentials: Credentials,
    idOrSymbol: String,
    to: AccountObjectId,
    data: T? = null,
    memo: Memo? = null,
    fee: Fee = Fee()
  ) = api.issue(credentials, idOrSymbol, to, data, memo, fee).blockingGet()
  fun createTransferOperation(
    from: AccountObjectId,
    to: AccountObjectId,
    id: NftDataObjectId,
    memo: Memo? = null,
    fee: Fee = Fee()
  ) = api.createTransferOperation(from, to, id, memo, fee).blockingGet()
  fun transfer(
    credentials: Credentials,
    to: AccountObjectId,
    id: NftDataObjectId,
    memo: Memo? = null,
    fee: Fee = Fee()
  ) = api.transfer(credentials, to, id, memo, fee).blockingGet()
  fun createUpdateDataOperation(
    modifier: AccountObjectId,
    id: NftDataObjectId,
    fee: Fee = Fee()
  ) = api.createUpdateDataOperation(modifier, id, fee).blockingGet()
  fun <T : NftModel> createUpdateDataOperation(
    modifier: AccountObjectId,
    id: NftDataObjectId,
    newData: T,
    fee: Fee = Fee()
  ) = api.createUpdateDataOperation(modifier, id, newData, fee).blockingGet()
  fun updateData(
    credentials: Credentials,
    id: NftDataObjectId,
    values: Map<String, Variant>,
    fee: Fee = Fee()
  ) = api.updateData(credentials, id, values, fee).blockingGet()
  fun <T : NftModel> updateData(
    credentials: Credentials,
    id: NftDataObjectId,
    newData: T,
    fee: Fee = Fee()
  ) = api.updateData(credentials, id, newData, fee).blockingGet()}
