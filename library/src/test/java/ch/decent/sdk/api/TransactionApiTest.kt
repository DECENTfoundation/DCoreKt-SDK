package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.operation.TransferOperation
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.print
import ch.decent.sdk.testCheck
import org.junit.Ignore
import org.junit.Test
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

class TransactionApiTest(channel: Channel) : BaseApiTest(channel) {
  val trx
    get() = api.historyApi.getOperation(Helpers.account, "1.7.1".toChainObject()).map { it.operation.blockNum }
        .flatMap { api.transactionApi.get(it, 0) }.blockingGet()

  @Test fun `should create a transaction`() {
    api.transactionApi.createTransaction(TransferOperation(Helpers.account, Helpers.account2, AssetAmount(1))).testCheck()
  }

  @Test fun `should get a list of proposed transactions`() {
    api.transactionApi.getAllProposed(Helpers.account).testCheck()
  }

  @Ignore //todo trx is found even after expiration for some time, we would need to wait for a while here  <1min
  @Test fun `should get expired recent transaction by ID`() {
    Instant.now().print()
    api.transactionApi.getRecent(trx.id).testCheck {
      assertError(ObjectNotFoundException::class.java)
    }
  }

  @Test fun `should get a transaction by ID`() {
    api.transactionApi.get(trx.id).testCheck {
      assertValue { it.id == trx.id }
    }
  }

  @Test fun `should get a transaction by block`() {
    api.transactionApi.get(trx.refBlockNum.toLong(), 0).testCheck()
  }

  @Test fun `should get a transaction by confirmation`() {
    api.transactionApi.get(trx.id)
        .flatMap { api.transactionApi.get(TransactionConfirmation(it.id, it.refBlockNum.toLong(), 0, it)) }
        .testCheck()
  }

  @Test fun `should get a transaction hex dump`() {
    api.transactionApi.createTransaction(TransferOperation(Helpers.account, Helpers.account2, AssetAmount(1)))
        .flatMap { api.transactionApi.getHexDump(it) }
        .testCheck()
  }
}
