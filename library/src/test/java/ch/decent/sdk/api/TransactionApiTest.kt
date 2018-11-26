package ch.decent.sdk.api

import ch.decent.sdk.account
import ch.decent.sdk.crypto.Sha256Hash
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.BlockData
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.print
import ch.decent.sdk.utils.hex
import ch.decent.sdk.utils.unhex
import io.reactivex.schedulers.Schedulers
import org.junit.Ignore
import org.junit.Test
import org.threeten.bp.ZoneOffset

class TransactionApiTest(channel: Channel) : BaseApiTest(channel) {
  override val useMock: Boolean = false

  //  already expired
  @Test fun `should get recent transaction by ID`() {
    val test = api.transactionApi.getRecentTransaction("322d451fb1dc9b3ec6bc521395f4547a8b62eb3f")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(ObjectNotFoundException::class.java)

  }

  @Test fun `should get a transaction by ID`() {
    val test = api.transactionApi.getTransaction("322d451fb1dc9b3ec6bc521395f4547a8b62eb3f")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue { it.id == "322d451fb1dc9b3ec6bc521395f4547a8b62eb3f"}

  }

  @Test fun `should get a transaction by block`() {
    val test = api.transactionApi.getTransaction(1370282, 0)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()

  }

  @Test fun `should get a transaction hex dump`() {
    val id = api.generalApi.getChainId().blockingGet()
    val ptrx = api.transactionApi.getTransaction(1370282, 0).blockingGet()
    val trx = Transaction(BlockData(ptrx.refBlockNum, ptrx.refBlockPrefix, ptrx.expiration.toEpochSecond(ZoneOffset.UTC)), ptrx.operations, id, ptrx.signatures)
    val test = api.transactionApi.getTransactionHex(trx)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get a list of proposed transactions`() {
    val test = api.transactionApi.getProposedTransactions(account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}