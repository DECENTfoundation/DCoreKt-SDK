@file:Suppress(
  "TooManyFunctions",
  "LongParameterList"
)

package ch.decent.sdk.api.blocking

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AccountOptions
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.Authority
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.SearchAccountHistoryOrder
import ch.decent.sdk.model.SearchAccountsOrder
import ch.decent.sdk.model.TransactionDetailObjectId
import ch.decent.sdk.utils.REQ_LIMIT_MAX
import ch.decent.sdk.utils.REQ_LIMIT_MAX_1K
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List

class AccountApi internal constructor(
  private val api: ch.decent.sdk.api.AccountApi
) {
  fun exist(nameOrId: String) = api.exist(nameOrId).blockingGet()
  fun get(id: AccountObjectId) = api.get(id).blockingGet()
  fun getByName(name: String) = api.getByName(name).blockingGet()
  fun get(nameOrId: String) = api.get(nameOrId).blockingGet()
  fun countAll() = api.countAll().blockingGet()
  fun findAllReferencesByKeys(keys: List<Address>) = api.findAllReferencesByKeys(keys).blockingGet()
  fun findAllReferencesByAccount(accountId: AccountObjectId) =
      api.findAllReferencesByAccount(accountId).blockingGet()
  fun getAll(accountIds: List<AccountObjectId>) = api.getAll(accountIds).blockingGet()
  fun getFullAccounts(namesOrIds: List<String>, subscribe: Boolean = false) =
      api.getFullAccounts(namesOrIds, subscribe).blockingGet()
  fun getAllByNames(names: List<String>) = api.getAllByNames(names).blockingGet()
  fun listAllRelative(lowerBound: String, limit: Int = REQ_LIMIT_MAX_1K) =
      api.listAllRelative(lowerBound, limit).blockingGet()
  fun findAll(
    searchTerm: String,
    order: SearchAccountsOrder = SearchAccountsOrder.NAME_DESC,
    id: AccountObjectId? = null,
    limit: Int = REQ_LIMIT_MAX_1K
  ) = api.findAll(searchTerm, order, id, limit).blockingGet()
  fun searchAccountHistory(
    accountId: AccountObjectId,
    from: TransactionDetailObjectId? = null,
    order: SearchAccountHistoryOrder = SearchAccountHistoryOrder.TIME_DESC,
    limit: Int = REQ_LIMIT_MAX
  ) = api.searchAccountHistory(accountId, from, order, limit).blockingGet()
  fun createCredentials(account: String, privateKey: String) = api.createCredentials(account,
      privateKey).blockingGet()
  fun createMemo(
    message: String,
    recipient: String? = null,
    keyPair: ECKeyPair? = null
  ) = api.createMemo(message, recipient, keyPair).blockingGet()
  fun createTransfer(
    credentials: Credentials,
    nameOrId: String,
    amount: AssetAmount,
    memo: String? = null,
    encrypted: Boolean = true,
    fee: Fee = Fee()
  ) = api.createTransfer(credentials, nameOrId, amount, memo, encrypted, fee).blockingGet()
  fun transfer(
    credentials: Credentials,
    nameOrId: String,
    amount: AssetAmount,
    memo: String? = null,
    encrypted: Boolean = true,
    fee: Fee = Fee()
  ) = api.transfer(credentials, nameOrId, amount, memo, encrypted, fee).blockingGet()
  fun createAccountOperation(
    registrar: AccountObjectId,
    name: String,
    address: Address,
    fee: Fee = Fee()
  ) = api.createAccountOperation(registrar, name, address, fee).blockingGet()
  fun create(
    registrar: Credentials,
    name: String,
    address: Address,
    fee: Fee = Fee()
  ) = api.create(registrar, name, address, fee).blockingGet()
  fun createUpdateOperation(nameOrId: String, fee: Fee = Fee()) =
      api.createUpdateOperation(nameOrId, fee).blockingGet()
  fun update(
    credentials: Credentials,
    options: AccountOptions? = null,
    active: Authority? = null,
    owner: Authority? = null,
    fee: Fee = Fee()
  ) = api.update(credentials, options, active, owner, fee).blockingGet()}
