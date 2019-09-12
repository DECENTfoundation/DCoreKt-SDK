package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.address
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.ProposalObjectId
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.operation.TransferOperation
import ch.decent.sdk.model.toObjectId
import ch.decent.sdk.testCheck
import org.junit.FixMethodOrder
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Suite
import org.threeten.bp.LocalDateTime

@Suite.SuiteClasses(TransactionOperationsTest::class, TransactionApiTest::class)
@RunWith(Suite::class)
class TransactionSuite

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TransactionOperationsTest : BaseOperationsTest() {

  @Test fun `1 create proposal for update`() {
    api.transactionApi.createProposal(
        Helpers.credentials,
        listOf(TransferOperation(Helpers.account2, Helpers.account, AssetAmount(11L))),
        LocalDateTime.now().plusDays(3)
    ).testCheck()
  }

  @Test fun `1 create proposal for delete`() {
    api.transactionApi.createProposal(
        Helpers.credentials,
        listOf(TransferOperation(Helpers.account2, Helpers.account, AssetAmount(22L))),
        LocalDateTime.now().plusDays(3)
    ).testCheck()
  }

  @Test fun `2 update proposal`() {
    api.transactionApi.updateProposal(
        Credentials(Helpers.account, Helpers.private),
        ProposalObjectId(0),
        activeApprovalsAdd = listOf(Helpers.account)
    ).testCheck()
  }

  @Test fun `2 delete proposal`() {
    api.transactionApi.deleteProposal(
        Credentials(Helpers.account2, Helpers.private2),
        ProposalObjectId(1)
    ).testCheck()
  }

}

class TransactionApiTest(channel: Channel) : BaseApiTest(channel) {
  val trx
    get() = api.historyApi.getOperation(Helpers.account, "1.7.0".toObjectId()).map { it.operation.blockNum }
        .flatMap { api.transactionApi.get(it, 0) }

  @Test fun `should create a transaction`() {
    api.transactionApi.createTransaction(TransferOperation(Helpers.account, Helpers.account2, AssetAmount(1))).testCheck()
  }

  @Test fun `should get a list of proposed transactions`() {
    api.transactionApi.getAllProposed(Helpers.account).testCheck()
  }

  @Test fun `should get proposed transaction by id`() {
    api.transactionApi.getProposed(ProposalObjectId(0)).testCheck()
  }

  @Ignore //todo trx is found even after expiration for some time, we would need to wait for a while here  <1min
  @Test fun `should get expired recent transaction by ID`() {
    trx.flatMap { api.transactionApi.getRecent(it.id) }.testCheck {
      assertError(ObjectNotFoundException::class.java)
    }
  }

  @Test fun `should get a transaction by ID`() {
    trx.flatMap { api.transactionApi.get(it.id) }.testCheck()
  }

  @Test fun `should get a transaction by block`() {
    trx.testCheck()
  }

  @Test fun `should get a transaction by confirmation`() {
    trx.flatMap { api.transactionApi.get(it.id) }
        .flatMap { api.transactionApi.get(TransactionConfirmation(it.id, it.refBlockNum.toLong(), 0, it)) }
        .testCheck()
  }

  @Test fun `should get a transaction hex dump`() {
    api.transactionApi.createTransaction(TransferOperation(Helpers.account, Helpers.account2, AssetAmount(1)))
        .flatMap { api.transactionApi.getHexDump(it) }
        .testCheck()
  }
}
