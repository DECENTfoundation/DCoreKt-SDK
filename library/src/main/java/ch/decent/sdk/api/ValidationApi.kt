package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.ProcessedTransaction
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.net.model.request.*
import io.reactivex.Single

class ValidationApi internal constructor(api: DCoreApi) : BaseApi(api) {

  fun getRequiredSignatures(transaction: Transaction, keys: List<Address>): Single<List<Address>> = GetRequiredSignatures(transaction, keys).toRequest()

  fun getPotentialSignatures(transaction: Transaction): Single<List<Address>> = GetPotentialSignatures(transaction).toRequest()

  fun verifyAuthority(transaction: Transaction): Single<Boolean> = VerifyAuthority(transaction).toRequest()

  fun verifyAccountAuthority(account: String, keys: List<Address>): Single<Boolean> = VerifyAccountAuthority(account, keys).toRequest()

  fun validateTransaction(transaction: Transaction): Single<ProcessedTransaction> = ValidateTransaction(transaction).toRequest()
}