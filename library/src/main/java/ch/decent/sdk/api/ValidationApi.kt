package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.exception.DCoreException
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
import io.reactivex.Single

class ValidationApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * This API will take a partially signed transaction and a set of public keys that the owner has the ability to sign for
   * and return the minimal subset of public keys that should add signatures to the transaction.
   *
   * @param transaction partially signed transaction
   * @param keys available owner public keys
   *
   * @return public keys that should add signatures
   */
  fun getRequiredSignatures(transaction: Transaction, keys: List<Address>): Single<List<Address>> = GetRequiredSignatures(transaction, keys).toRequest()

  /**
   * This method will return the set of all public keys that could possibly sign for a given transaction.
   * This call can be used by wallets to filter their set of public keys to just the relevant subset prior to calling get_required_signatures() to get the minimum subset.
   *
   * @param transaction unsigned transaction
   *
   * @return public keys that can sign transaction
   */
  fun getPotentialSignatures(transaction: Transaction): Single<List<Address>> = GetPotentialSignatures(transaction).toRequest()

  /**
   * Verifies required signatures of a transaction.
   *
   * @param transaction signed transaction to verify
   *
   * @return if the transaction has all of the required signatures
   */
  fun verifyAuthority(transaction: Transaction): Single<Boolean> = VerifyAuthority(transaction).toRequest().onErrorReturnItem(false)

  /**
   * Verifies if the signers have enough authority to authorize an account.
   *
   * @param account account name or object id
   * @param keys signer keys
   *
   * @return if the signers have enough authority
   */
  fun verifyAccountAuthority(account: String, keys: List<Address>): Single<Boolean> = VerifyAccountAuthority(account, keys).toRequest()

  /**
   * Validates a transaction against the current state without broadcasting it on the network.
   *
   * @param transaction signed transaction
   *
   * @return [ProcessedTransaction] or fails with [DCoreException]
   */
  fun validateTransaction(transaction: Transaction): Single<ProcessedTransaction> = ValidateTransaction(transaction).toRequest()

  /**
   * Returns fees for operation.
   *
   * @param op list of operations
   * @param assetId asset id eg. DCT id is 1.3.0
   *
   * @return a list of fee asset amounts
   */
  @JvmOverloads
  fun getFees(op: List<BaseOperation>, assetId: ChainObject = DCoreConstants.DCT.id): Single<List<AssetAmount>> = GetRequiredFees(op, assetId).toRequest()

  /**
   * Returns fee for operation.
   *
   * @param op operation
   * @param assetId asset id eg. DCT id is 1.3.0
   *
   * @return a fee asset amount
   */
  @JvmOverloads
  fun getFee(op: BaseOperation, assetId: ChainObject = DCoreConstants.DCT.id): Single<AssetAmount> = getFees(listOf(op), assetId).map { it.single() }

  /**
   * Returns fee for operation type, not valid for operation per size fees:
   * [OperationType.PROPOSAL_CREATE_OPERATION],
   * [OperationType.PROPOSAL_UPDATE_OPERATION],
   * [OperationType.WITHDRAW_PERMISSION_CLAIM_OPERATION],
   * [OperationType.CUSTOM_OPERATION]
   *
   * @param type operation type
   *
   * @return a fee asset amount
   */
  @JvmOverloads
  fun getFee(type: OperationType, assetId: ChainObject = DCoreConstants.DCT.id): Single<AssetAmount> =
      require(listOf(
          OperationType.PROPOSAL_CREATE_OPERATION,
          OperationType.PROPOSAL_UPDATE_OPERATION,
          OperationType.WITHDRAW_PERMISSION_CLAIM_OPERATION,
          OperationType.CUSTOM_OPERATION)
          .contains(type).not()
      ).let { getFee(EmptyOperation(type), assetId) }

}