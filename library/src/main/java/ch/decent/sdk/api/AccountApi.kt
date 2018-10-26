package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
import io.reactivex.Single

class AccountApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get account object by name.
   *
   * @param name the name of the account
   *
   * @return an account if found, [ObjectNotFoundException] otherwise
   */
  private fun getAccountByName(name: String): Single<Account> = GetAccountByName(name).toRequest()

  /**
   * Get account objects by ids.
   *
   * @param accountIds object ids of the account, 1.2.*
   *
   * @return an account list if found, [ObjectNotFoundException] otherwise
   */
  fun getAccounts(accountIds: List<ChainObject>): Single<List<Account>> = GetAccountById(accountIds).toRequest()

  /**
   * Get account object ids by public key addresses.
   *
   * @param keys formatted public keys of the account, eg. DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4
   *
   * @return an account object ids if found, [ObjectNotFoundException] otherwise
   */
  fun getAccountIds(keys: List<Address>): Single<List<List<ChainObject>>> = GetKeyReferences(keys).run {
    toRequest()
        .doOnSuccess { if (it.size == 1 && it[0].isEmpty()) throw ObjectNotFoundException(description()) }
  }

  /**
   * Check if the account exist.
   *
   * @param reference account id, name or pub key
   *
   * @return account exists in DCore database
   */
  fun accountExist(reference: String): Single<Boolean> = getAccount(reference).map { true }.onErrorReturnItem(false)

  /**
   * Get account by reference.
   *
   * @param reference account id, name or pub key
   *
   * @return first found account if exist, [ObjectNotFoundException] if not found, or [IllegalStateException] if the account reference is not valid
   */
  fun getAccount(reference: String): Single<Account> = when {
    ChainObject.isValid(reference) -> getAccounts(listOf(reference.toChainObject())).map { it.first() }
    Address.isValid(reference) -> getAccountIds(listOf(Address.decode(reference))).map { it[0][0] }
        .flatMap { getAccounts(listOf(it)).map { it.first() } }
    Account.isValidName(reference) -> getAccountByName(reference)
    else -> Single.error(IllegalArgumentException("not a valid account reference"))
  }

  /**
   * Search account history.
   *
   * @param accountId object id of the account, 1.2.*
   * @param from object id of the history object to start from, use [ObjectType.NULL_OBJECT.genericId] to ignore
   * @param order order of items
   * @param limit number of entries, max 100
   *
   * @return account history list
   */
  @JvmOverloads
  fun searchAccountHistory(
      accountId: ChainObject,
      from: ChainObject = ObjectType.NULL_OBJECT.genericId,
      order: SearchAccountHistoryOrder = SearchAccountHistoryOrder.TIME_DESC,
      limit: Int = 100
  ): Single<List<TransactionDetail>> = SearchAccountHistory(accountId, order, from, limit).toRequest()

  /**
   * Create API credentials.
   *
   * @param account account name
   * @param privateKey private key in wif base58 format, eg. 5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn
   *
   * @return credentials
   */
  fun createCredentials(account: String, privateKey: String): Single<Credentials> =
      getAccountByName(account).map { Credentials(it.id, ECKeyPair.fromBase58(privateKey)) }

  /**
   * Fetch all objects relevant to the specified accounts and subscribe to updates.
   *
   * @param namesOrIds list of account names or ids
   * @param subscribe true to subscribe to updates
   *
   * @return map of names or ids to account, or empty map if not present
   */
  @JvmOverloads
  fun getFullAccounts(namesOrIds: List<String>, subscribe: Boolean = false) =
      GetFullAccounts(namesOrIds, subscribe).toRequest()

  /**
   * Get all accounts that refer to the account id in their owner or active authorities.
   *
   * @param accountId account object id
   *
   * @return a list of account object ids
   */
  fun getAccountReferences(accountId: ChainObject): Single<List<ChainObject>> =
      GetAccountReferences(accountId).toRequest()

  /**
   * Get a list of accounts by name.
   *
   * @param names account names to retrieve
   *
   * @return list of accounts or [ObjectNotFoundException] if none exist
   */
  fun lookupAccountNames(names: List<String>): Single<List<Account>> =
      LookupAccountNames(names).toRequest()

  /**
   * Get names and IDs for registered accounts.
   *
   * @param lowerBound lower bound of the first name to return
   * @param limit number of items to get, max 1000
   *
   * @return map of account names to corresponding IDs

   */
  @JvmOverloads
  fun lookupAccounts(lowerBound: String, limit: Int = 1000): Single<Map<String, ChainObject>> =
      LookupAccounts(lowerBound, limit).toRequest()

  /**
   * Get names and IDs for registered accounts that match search term.
   *
   * @param searchTerm will try to partially match account name or id
   * @param order sort data by field
   * @param id object id to start searching from
   * @param limit number of items to get, max 1000
   *
   * @return list of found accounts
   */
  @JvmOverloads
  fun searchAccounts(
      searchTerm: String,
      order: SearchAccountsOrder = SearchAccountsOrder.NAME_DESC,
      id: ChainObject = ObjectType.NULL_OBJECT.genericId,
      limit: Int = 1000
  ): Single<List<Account>> = SearchAccounts(searchTerm, order, id, limit).toRequest()

  /**
   * Get the total number of accounts registered on the blockchain.
   */
  fun getAccountCount(): Single<Long> = GetAccountCount.toRequest()

}