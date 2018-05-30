package ch.decent.sdk

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.*

interface DCoreApi {
  val apiRx: DCoreApiRx

  /**
   * Creates credentials
   *
   * @param accountName the blockchain account to use
   * @param privateKey encoded private key as base58 string eg. 5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn
   *
   * @return account credentials used for blockchain operations
   */
  fun createCredentials(accountName: String, privateKey: String): Credentials =
      apiRx.getAccountByName(accountName).map { Credentials(it.id, ECKeyPair.fromBase58(privateKey)) }.blockingGet()

  /**
   * get account balance
   *
   * @param accountName name of the account
   *
   * @return list of amounts for different assets
   */
  fun getBalance(accountName: String): List<AssetAmount> = apiRx.getBalance(accountName).blockingGet()

  /**
   * If the transaction has not expired, this method will return the transaction for the given ID or it will return null.
   * Just because it is not known does not mean it wasn't included in the blockchain. The ID can be retrieved from [Transaction] or [TransactionConfirmation] objects.
   * You can set up a custom expiration value in [DCoreSdk.transactionExpiration]
   *
   * @param trxId transaction id
   *
   * @return a transaction if found and not expired, null otherwise
   */
  fun getRecentTransaction(trxId: String): ProcessedTransaction? = try {
    apiRx.getRecentTransaction(trxId).blockingGet()
  } catch (e: ObjectNotFoundException) {
    null
  }

  /**
   * get account history
   *
   * @param account account name
   *
   * @return list of history operations, first 100 entries
   */
  fun getAccountHistory(
      account: String
  ): List<OperationHistory> = apiRx.getAccountHistory(account).blockingGet()

  /**
   * make a transfer
   *
   * @param credentials user credentials
   * @param to receiver account name
   * @param amount amount to send in DCT
   * @param memo optional message
   * @param encrypted encrypted is visible only for sender and receiver, unencrypted is visible publicly
   *
   * @return a transaction confirmation
   */
  fun transfer(
      credentials: Credentials,
      to: String,
      amount: Double,
      memo: String? = null,
      encrypted: Boolean = true
  ): TransactionConfirmation = apiRx.transfer(credentials, to, amount, memo, encrypted).blockingGet()

}