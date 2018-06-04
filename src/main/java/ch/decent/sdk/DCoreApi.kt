package ch.decent.sdk

import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.*
import com.google.common.base.Optional
import ch.decent.sdk.model.SearchAccountHistoryOrder
import ch.decent.sdk.model.SearchPurchasesOrder
import io.reactivex.Single
import java.math.BigDecimal

interface DCoreApi {
  val apiRx: DCoreApiRx

  private fun <T> Single<T>.toOptional(): Optional<T> =
      try {
        Optional.of(blockingGet())
      } catch (ex: RuntimeException) {
        if (ex.cause is ObjectNotFoundException) Optional.absent<T>() else throw ex
      }

  /**
   * Creates credentials
   *
   * @param accountName the blockchain account to use
   * @param privateKey encoded private key as base58 string eg. 5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn
   *
   * @return account credentials used for blockchain operations or [Optional.absent] if account does not exist
   */
  fun createCredentials(accountName: String, privateKey: String): Optional<Credentials> =
      apiRx.getAccountByName(accountName).map { Credentials(it.id, ECKeyPair.fromBase58(privateKey)) }.toOptional()

  /**
   * get account balance
   *
   * @param accountName name of the account
   * @param assetSymbol asset symbol eg. DCT
   *
   * @return value of assets owned or [Optional.absent] if symbol does not exist
   */
  fun getBalance(accountName: String, assetSymbol: String): Optional<BigDecimal> = apiRx.getBalance(accountName, assetSymbol).toOptional()

  /**
   * get applied transaction
   *
   * @param confirmation confirmation returned from transaction broadcast
   *
   * @return a transaction if found or [Optional.absent]
   */
  fun getTransaction(confirmation: TransactionConfirmation): Optional<ProcessedTransaction> = apiRx.getTransaction(confirmation).toOptional()

  /**
   * get account history, first 100 entries
   *
   * @param account account name
   *
   * @return list of history operations or empty list
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