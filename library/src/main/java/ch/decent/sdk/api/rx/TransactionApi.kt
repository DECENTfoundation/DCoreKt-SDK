@file:Suppress("TooManyFunctions", "LongParameterList")

package ch.decent.sdk.api.rx

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.OpWrapper
import ch.decent.sdk.model.ProcessedTransaction
import ch.decent.sdk.model.Proposal
import ch.decent.sdk.model.ProposalObjectId
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.operation.BaseOperation
import ch.decent.sdk.model.operation.ProposalCreateOperation
import ch.decent.sdk.model.operation.ProposalDeleteOperation
import ch.decent.sdk.model.operation.ProposalUpdateOperation
import ch.decent.sdk.net.model.request.GetProposalById
import ch.decent.sdk.net.model.request.GetProposedTransactions
import ch.decent.sdk.net.model.request.GetRecentTransactionById
import ch.decent.sdk.net.model.request.GetTransaction
import ch.decent.sdk.net.model.request.GetTransactionById
import ch.decent.sdk.net.model.request.GetTransactionHex
import io.reactivex.Single
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

class TransactionApi internal constructor(api: DCoreApi) : BaseApi(api) {

  internal val transactionExpiration: Duration
    get() = api.transactionExpiration

  /**
   * create unsigned transaction
   *
   * @param operations operations to include in transaction
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  @JvmOverloads
  fun createTransaction(operations: List<BaseOperation>, expiration: Duration = api.transactionExpiration): Single<Transaction> =
      api.core.prepareTransaction(operations, expiration)

  /**
   * create unsigned transaction
   *
   * @param operation operation to include in transaction
   * @param expiration transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  @JvmOverloads
  fun createTransaction(operation: BaseOperation, expiration: Duration = api.transactionExpiration): Single<Transaction> =
      api.core.prepareTransaction(listOf(operation), expiration)

  /**
   * Get the set of proposed transactions relevant to the specified account id.
   *
   * @param accountId account object id, 1.2.*
   *
   * @return a set of proposed transactions
   */
  fun getAllProposed(accountId: AccountObjectId): Single<List<Proposal>> = GetProposedTransactions(accountId).toRequest()

  fun getAllProposed(ids: List<ProposalObjectId>): Single<List<Proposal>> = GetProposalById(ids).toRequest()

  fun getProposed(id: ProposalObjectId): Single<Proposal> = getAllProposed(listOf(id)).map { it.single() }

  /**
   * If the transaction has not expired, this method will return the transaction for the given ID or it will return [ch.decent.sdk.exception.ObjectNotFoundException].
   * Just because it is not known does not mean it wasn't included in the DCore. The ID can be retrieved from [Transaction] or [TransactionConfirmation] objects.
   *
   * @param trxId transaction id
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getRecent(trxId: String): Single<ProcessedTransaction> = GetRecentTransactionById(trxId).toRequest()

  /**
   * This method will return the transaction for the given ID or it will return [ch.decent.sdk.exception.ObjectNotFoundException].
   * The ID can be retrieved from [Transaction] or [TransactionConfirmation] objects.
   *
   * Note: By default these objects are not tracked, the transaction_history_plugin must be loaded for these objects to be maintained.
   * Just because it is not known does not mean it wasn't included in the DCore.
   *
   * @param trxId transaction id
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun get(trxId: String): Single<ProcessedTransaction> = GetTransactionById(trxId).toRequest()

  /**
   * get applied transaction
   *
   * @param blockNum block number
   * @param trxInBlock position of the transaction in block
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun get(blockNum: Long, trxInBlock: Long): Single<ProcessedTransaction> = GetTransaction(blockNum, trxInBlock).toRequest()

  /**
   * get applied transaction
   *
   * @param confirmation confirmation returned from transaction broadcast
   *
   * @return a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun get(confirmation: TransactionConfirmation): Single<ProcessedTransaction> = get(confirmation.blockNum, confirmation.trxNum)

  /**
   * Get a hexdump of the serialized binary form of a transaction.
   *
   * @param transaction a signed transaction
   *
   * @return hexadecimal string
   */
  fun getHexDump(transaction: Transaction): Single<String> = GetTransactionHex(transaction).toRequest()

  fun createProposalCreateOperation(
      payer: AccountObjectId,
      ops: List<BaseOperation>,
      expiration: LocalDateTime,
      reviewPeriod: Duration? = null,
      fee: Fee = Fee()
  ): Single<ProposalCreateOperation> = Single.just(ProposalCreateOperation(payer, ops.map { OpWrapper(it) }, expiration, reviewPeriod?.seconds, fee))

  fun createProposal(
      credentials: Credentials,
      ops: List<BaseOperation>,
      expiration: LocalDateTime,
      reviewPeriod: Duration? = null,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createProposalCreateOperation(credentials.account, ops, expiration, reviewPeriod, fee)
      .broadcast(credentials)

  fun createProposalUpdateOperation(
      payer: AccountObjectId,
      proposal: ProposalObjectId,
      activeApprovalsAdd: List<AccountObjectId> = emptyList(),
      activeApprovalsRemove: List<AccountObjectId> = emptyList(),
      ownerApprovalsAdd: List<AccountObjectId> = emptyList(),
      ownerApprovalsRemove: List<AccountObjectId> = emptyList(),
      keyApprovalsAdd: List<Address> = emptyList(),
      keyApprovalsRemove: List<Address> = emptyList(),
      fee: Fee = Fee()
  ): Single<ProposalUpdateOperation> = Single.just(ProposalUpdateOperation(payer, proposal, activeApprovalsAdd, activeApprovalsRemove, ownerApprovalsAdd, ownerApprovalsRemove,
      keyApprovalsAdd, keyApprovalsRemove, fee))

  fun updateProposal(
      credentials: Credentials,
      proposal: ProposalObjectId,
      activeApprovalsAdd: List<AccountObjectId> = emptyList(),
      activeApprovalsRemove: List<AccountObjectId> = emptyList(),
      ownerApprovalsAdd: List<AccountObjectId> = emptyList(),
      ownerApprovalsRemove: List<AccountObjectId> = emptyList(),
      keyApprovalsAdd: List<Address> = emptyList(),
      keyApprovalsRemove: List<Address> = emptyList(),
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> =
      createProposalUpdateOperation(credentials.account, proposal, activeApprovalsAdd, activeApprovalsRemove, ownerApprovalsAdd, ownerApprovalsRemove,
          keyApprovalsAdd, keyApprovalsRemove, fee)
          .broadcast(credentials)

  fun createProposalDeleteOperation(
      payer: AccountObjectId,
      proposal: ProposalObjectId,
      usingOwnerAuthority: Boolean = false,
      fee: Fee = Fee()
  ): Single<ProposalDeleteOperation> = Single.just(ProposalDeleteOperation(payer, proposal, usingOwnerAuthority, fee))

  fun deleteProposal(
      credentials: Credentials,
      proposal: ProposalObjectId,
      usingOwnerAuthority: Boolean = false,
      fee: Fee = Fee()
  ): Single<TransactionConfirmation> = createProposalDeleteOperation(credentials.account, proposal, usingOwnerAuthority, fee)
      .broadcast(credentials)
}
