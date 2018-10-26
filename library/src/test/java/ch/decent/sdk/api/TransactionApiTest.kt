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

  //  transaction_history_plugin not loaded ?
  @Ignore
  @Test fun `should get a transaction by ID`() {
    val test = api.transactionApi.getTransaction("a9e89715f18f0bb0595b012720a10700000000000022230000000000020160e3160000000000000102c03f8e840c1699fd7808c2bb858e249c688c5be8acf0a0c1c484ab0cfb27f0a802e0ced80260630f641f61f6d6959f32b5c43b1a38be55666b98abfe8bafcc556b002ea2558d64350a204bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a70000011f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()

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