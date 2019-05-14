package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.operation.TransferOperation
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class TransactionApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should create a transaction`() {
    val test = api.transactionApi.createTransaction(TransferOperation(Helpers.account, Helpers.account2, AssetAmount(1)))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertNoErrors()
        .assertComplete()
  }

  @Test fun `should get a list of proposed transactions`() {
    val test = api.transactionApi.getAllProposed(Helpers.account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get expired recent transaction by ID`() {
    val test = api.transactionApi.getRecent("abb2c83679c2217bd20bed723f3a9ffa8653a953")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(ObjectNotFoundException::class.java)
  }

  @Test fun `should get a transaction by ID`() {
    val test = api.transactionApi.get("abb2c83679c2217bd20bed723f3a9ffa8653a953")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue { it.id == "abb2c83679c2217bd20bed723f3a9ffa8653a953" }

  }

  @Test fun `should get a transaction by block`() {
    val test = api.transactionApi.get(446532, 0)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()

  }

  @Test fun `should get a transaction by confirmation`() {
    val test = api.transactionApi.get(446532, 0)
        .map { TransactionConfirmation("abb2c83679c2217bd20bed723f3a9ffa8653a953", 446532, 0, it) }
        .flatMap { api.transactionApi.get(it) }
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()

  }

  @Test fun `should get a transaction hex dump`() {
    val test = api.transactionApi.createTransaction(TransferOperation(Helpers.account, Helpers.account2, AssetAmount(1)))
        .flatMap { api.transactionApi.getHexDump(it) }
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}
