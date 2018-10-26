package ch.decent.sdk.api

import ch.decent.sdk.accountName2
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.address
import ch.decent.sdk.exception.DCoreException
import ch.decent.sdk.model.BlockData
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.private
import ch.decent.sdk.public
import ch.decent.sdk.public2
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.threeten.bp.ZoneOffset

class ValidationApiTest(channel: Channel) : BaseApiTest(channel) {
  override val useMock: Boolean = false

  private val signature = "1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"
  private val signatureInvalid = "1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089"
  private val trx: Transaction
    get() = api.transactionApi.getTransaction(1370282, 0).blockingGet().let {
      Transaction(BlockData(it.refBlockNum, it.refBlockPrefix, it.expiration.toEpochSecond(ZoneOffset.UTC)), it.operations, "")
    }

  @Test fun `should get required signatures`() {
    val test = api.validationApi.getRequiredSignatures(trx, listOf(public.address(), public2.address()))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue(listOf(public.address()))
  }

  @Test fun `should get potential signatures`() {
    val test = api.validationApi.getPotentialSignatures(trx)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue(listOf(public.address()))
  }

  @Test fun `should verify signed transaction`() {
    val test = api.validationApi.verifyAuthority(trx.copy(signatures = listOf(signature)))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should fail to verify signed transaction`() {
    val test = api.validationApi.verifyAuthority(trx.copy(signatures = listOf(signatureInvalid)))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(DCoreException::class.java)
  }

  // bug in dcore, fails
  @Test fun `should verify signers for account`() {
    val test = api.validationApi.verifyAccountAuthority(accountName2, listOf(public2.address()))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(DCoreException::class.java)
  }

  @Test fun `should validate signed transaction`() {
    val trxNew = api.transactionApi.createTransaction(trx.operations).blockingGet()
        .withSignature(ECKeyPair.fromBase58(private))
    val test = api.validationApi.validateTransaction(trxNew)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should fail to validate signed transaction`() {
    val trxNew = api.transactionApi.createTransaction(trx.operations).blockingGet()
        .withSignature(ECKeyPair.fromBase58(private))
    val test = api.validationApi.validateTransaction(trxNew.copy(signatures = listOf(signatureInvalid)))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(DCoreException::class.java)
  }

}