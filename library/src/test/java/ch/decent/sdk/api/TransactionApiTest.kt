package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.operation.TransferOperation
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.testCheck
import org.junit.Ignore
import org.junit.Test

class TransactionApiTest(channel: Channel) : BaseApiTest(channel) {
  val trx
    get() = api.historyApi.getOperation(Helpers.account, "1.7.0".toChainObject()).map { it.operation.blockNum }
        .flatMap { api.transactionApi.get(it, 0) }

  @Test fun `should create a transaction`() {
    api.transactionApi.createTransaction(TransferOperation(Helpers.account, Helpers.account2, AssetAmount(1))).testCheck()
  }

  @Test fun `should get a list of proposed transactions`() {
    api.transactionApi.getAllProposed(Helpers.account).testCheck()
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
