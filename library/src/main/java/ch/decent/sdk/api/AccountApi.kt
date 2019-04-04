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
   * Check if the account exist.
   *
   * @param nameOrId account id or name
   *
   * @return account exists in DCore database
   */
  fun exist(nameOrId: String): Single<Boolean> = get(nameOrId).map { true }.onErrorResumeNext {
    if (it is IllegalArgumentException || it is ObjectNotFoundException) Single.just(false)
    else Single.error(it)
  }

  /**
   * Get account by id.
   *
   * @param id account id
   *
   * @return an account if exist, [ObjectNotFoundException] if not found
   */
  fun get(id: ChainObject): Single<Account> = getAll(listOf(id)).map { it.first() }

  /**
   * Get account object by name.
   *
   * @param name the name of the account
   *
   * @return an account if found, [ObjectNotFoundException] otherwise
   */
  fun getByName(name: String): Single<Account> = GetAccountByName(name).toRequest()

  /**
   * Get account by name or id.
   *
   * @param nameOrId account id or name
   *
   * @return an account if exist, [ObjectNotFoundException] if not found, or [IllegalStateException] if the account name or id is not valid
   */
  fun get(nameOrId: String): Single<Account> = when {
    ChainObject.isValid(nameOrId) -> get(nameOrId.toChainObject())
    Account.isValidName(nameOrId) -> getByName(nameOrId)
    else -> Single.error(IllegalArgumentException("not a valid account name or id"))
  }

  /**
   * Get the total number of accounts registered on the blockchain.
   */
  fun countAll(): Single<Long> = GetAccountCount.toRequest()

  /**
   * Get account object ids by public key addresses.
   *
   * @param keys formatted public keys of the account, eg. DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4
   *
   * @return a list of account object ids
   */
  fun findAllReferencesByKeys(keys: List<Address>): Single<List<List<ChainObject>>> = GetKeyReferences(keys).toRequest()

  /**
   * Get all accounts that refer to the account id in their owner or active authorities.
   *
   * @param accountId account object id
   *
   * @return a list of account object ids
   */
  fun findAllReferencesByAccount(accountId: ChainObject): Single<List<ChainObject>> =
      GetAccountReferences(accountId).toRequest()

  /**
   * Get account objects by ids.
   *
   * @param accountIds object ids of the account, 1.2.*
   *
   * @return an account list if found, [ObjectNotFoundException] otherwise
   */
  fun getAll(accountIds: List<ChainObject>): Single<List<Account>> = GetAccountById(accountIds).toRequest()

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
   * Get a list of accounts by name.
   *
   * @param names account names to retrieve
   *
   * @return list of accounts or [ObjectNotFoundException] if none exist
   */
  fun getAllByNames(names: List<String>): Single<List<Account>> =
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
  fun listAllRelative(lowerBound: String, limit: Int = 1000): Single<Map<String, ChainObject>> =
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
  fun findAll(
      searchTerm: String,
      order: SearchAccountsOrder = SearchAccountsOrder.NAME_DESC,
      id: ChainObject = ObjectType.NULL_OBJECT.genericId,
      limit: Int = 1000
  ): Single<List<Account>> = SearchAccounts(searchTerm, order, id, limit).toRequest()

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
  @Deprecated(message = "Use history API")
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
      getByName(account).map { Credentials(it.id, ECKeyPair.fromBase58(privateKey)) }

  /**
   * Create a transfer operation.
   *
   * @param credentials account credentials
   * @param nameOrId account id or account name
   * @param amount amount to send with asset type
   * @param memo optional message
   * @param encrypted encrypted is visible only for sender and receiver, unencrypted is visible publicly
   * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun createTransfer(
      credentials: Credentials,
      nameOrId: String,
      amount: AssetAmount,
      memo: String? = null,
      encrypted: Boolean = true,
      fee: AssetAmount = BaseOperation.FEE_UNSET
  ): Single<TransferOperation> =
      if ((memo.isNullOrBlank() || !encrypted) && ChainObject.isValid(nameOrId)) {
        Single.just(TransferOperation(credentials.account, nameOrId.toChainObject(), amount, memo?.let { Memo(it) }, fee))
      } else {
        get(nameOrId).map { receiver ->
          val msg = memo?.let { if (encrypted) Memo(memo, credentials, receiver) else Memo(memo) }
          TransferOperation(credentials.account, receiver.id, amount, msg, fee)
        }
      }

  /**
   * Make a transfer.
   *
   * @param credentials account credentials
   * @param nameOrId account id or account name
   * @param amount amount to send with asset type
   * @param memo optional message
   * @param encrypted encrypted is visible only for sender and receiver, unencrypted is visible publicly
   * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
   *
   * @return a transaction confirmation
   */
  @JvmOverloads
  fun transfer(
      credentials: Credentials,
      nameOrId: String,
      amount: AssetAmount,
      memo: String? = null,
      encrypted: Boolean = true,
      fee: AssetAmount = BaseOperation.FEE_UNSET
  ): Single<TransactionConfirmation> =
      createTransfer(credentials, nameOrId, amount, memo, encrypted, fee).flatMap {
        api.broadcastApi.broadcastWithCallback(credentials.keyPair, it)
      }

  /**
   * Create a new account.
   *
   * @param registrar credentials used to register the new account
   * @param name new account name
   * @param address new account public key address
   * @param fee [AssetAmount] fee for the operation, if left [BaseOperation.FEE_UNSET] the fee will be computed in DCT asset
   *
   * @return a transaction confirmation
   */
  fun create(
      registrar: Credentials,
      name: String,
      address: Address,
      fee: AssetAmount = BaseOperation.FEE_UNSET
  ): Single<TransactionConfirmation> =
      api.broadcastApi.broadcastWithCallback(registrar.keyPair, AccountCreateOperation(registrar.account, name, address, fee))

}
