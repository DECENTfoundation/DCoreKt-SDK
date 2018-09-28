package ch.decent.sdk.api

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.*
import io.reactivex.Single

interface AccountApi {

  /**
   * get Account object by name
   *
   * @param name the name of the account
   * @return an account if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getAccountByName(name: String): Single<Account>

  /**
   * get Account objects by ids
   *
   * @param accountIds object ids of the account, 1.2.*
   * @return an account list if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getAccountsByIds(accountIds: List<ChainObject>): Single<List<Account>>

  /**
   * get account object ids by public key addresses
   *
   * @param keys formatted public keys of the account, eg. DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4
   * @return an account object ids if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getAccountIdsByKeys(keys: List<Address>): Single<List<List<ChainObject>>>

  /**
   * get Account object by public key address
   *
   * @param address formatted public key of the account, eg. DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4
   * @return first found account if exist, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getAccountByAddress(address: Address): Single<Account> = getAccountIdsByKeys(listOf(address)).flatMap { getAccountsByIds(it.first()).map { it.first() } }

  /**
   * check if account exist
   *
   * @param reference account id, name or pub key
   * @return account exists in DCore database
   */
  fun accountExist(reference: String): Single<Boolean> = getAccountId(reference).map { true }.onErrorReturnItem(false)

  /**
   * get account id by reference
   *
   * @param reference account id, name or pub key
   * @return first found account id if exist, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   * @throws IllegalStateException if the account reference is not valid
   */

  fun getAccountId(reference: String): Single<ChainObject> = when {
    ChainObject.isValid(reference) -> getAccountsByIds(listOf(reference.toChainObject())).map { it.first().id }
    Address.isValid(reference) -> getAccountIdsByKeys(listOf(Address.decode(reference))).map { it[0][0] }
    Account.isValidName(reference) -> getAccountByName(reference).map { it.id }
    else -> throw IllegalArgumentException("not a valid account reference")
  }

  /**
   * get account by reference
   *
   * @param reference account id, name or pub key
   * @return first found account if exist, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   * @throws IllegalStateException if the account reference is not valid
   */
  fun getAccount(reference: String): Single<Account> = when {
    ChainObject.isValid(reference) -> getAccountsByIds(listOf(reference.toChainObject())).map { it.first() }
    Address.isValid(reference) -> getAccountIdsByKeys(listOf(Address.decode(reference))).map { it[0][0] }
        .flatMap { getAccountsByIds(listOf(it)).map { it.first() } }
    Account.isValidName(reference) -> getAccountByName(reference)
    else -> throw IllegalArgumentException("not a valid account reference")
  }

  /**
   * search account history
   *
   * @param accountId object id of the account, 1.2.*
   * @param order order of items
   * @param from object id of the history object to start from, use [ObjectType.NULL_OBJECT.genericId] to ignore
   * @param limit number of entries, max 100
   */
  fun searchAccountHistory(
      accountId: ChainObject,
      order: SearchAccountHistoryOrder = SearchAccountHistoryOrder.TIME_DESC,
      from: ChainObject = ObjectType.NULL_OBJECT.genericId,
      limit: Int = 100
  ): Single<List<TransactionDetail>>

  /**
   * create api credentials
   *
   * @param account account name
   * @param privateKey private key in wif base58 format, eg. 5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn
   * @return credentials
   */
  fun createCredentials(account: String, privateKey: String): Single<Credentials> =
      getAccountByName(account).map { Credentials(it.id, ECKeyPair.fromBase58(privateKey)) }

}