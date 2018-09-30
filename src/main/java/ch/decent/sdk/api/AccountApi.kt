package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.GetAccountById
import ch.decent.sdk.net.model.request.GetAccountByName
import ch.decent.sdk.net.model.request.GetKeyReferences
import ch.decent.sdk.net.model.request.SearchAccountHistory
import io.reactivex.Single

class AccountApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * get Account object by name
   *
   * @param name the name of the account
   *
   * @return an account if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  private fun getAccountByName(name: String): Single<Account> = GetAccountByName(name).toRequest()

  /**
   * get Account objects by ids
   *
   * @param accountIds object ids of the account, 1.2.*
   *
   * @return an account list if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getAccounts(accountIds: List<ChainObject>): Single<List<Account>> = GetAccountById(accountIds).toRequest()

  /**
   * get account object ids by public key addresses
   *
   * @param keys formatted public keys of the account, eg. DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4
   *
   * @return an account object ids if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getAccountIds(keys: List<Address>): Single<List<List<ChainObject>>> = GetKeyReferences(keys).run {
    toRequest()
        .doOnSuccess { if (it.size == 1 && it[0].isEmpty()) throw ObjectNotFoundException(description()) }
  }

  /**
   * get Account object by public key address
   *
   * @param address formatted public key of the account, eg. DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4
   *
   * @return first found account if exist, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
//  fun getAccountByAddress(address: Address): Single<Account> = getAccountIdsByKeys(listOf(address)).flatMap { getAccountsByIds(it.first()).map { it.first() } }

  /**
   * check if account exist
   *
   * @param reference account id, name or pub key
   *
   * @return account exists in DCore database
   */
  fun accountExist(reference: String): Single<Boolean> = getAccount(reference).map { true }.onErrorReturnItem(false)

  /**
   * get account by reference
   *
   * @param reference account id, name or pub key
   *
   * @return first found account if exist, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   * @throws IllegalStateException if the account reference is not valid
   */
  fun getAccount(reference: String): Single<Account> = when {
    ChainObject.isValid(reference) -> getAccounts(listOf(reference.toChainObject())).map { it.first() }
    Address.isValid(reference) -> getAccountIds(listOf(Address.decode(reference))).map { it[0][0] }
        .flatMap { getAccounts(listOf(it)).map { it.first() } }
    Account.isValidName(reference) -> getAccountByName(reference)
    else -> throw IllegalArgumentException("not a valid account reference")
  }

  /**
   * search account history
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
   * create api credentials
   *
   * @param account account name
   * @param privateKey private key in wif base58 format, eg. 5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn
   *
   * @return credentials
   */
  fun createCredentials(account: String, privateKey: String): Single<Credentials> =
      getAccountByName(account).map { Credentials(it.id, ECKeyPair.fromBase58(privateKey)) }

}