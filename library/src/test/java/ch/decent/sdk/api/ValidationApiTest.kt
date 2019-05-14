package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.crypto.address
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.operation.OperationType
import ch.decent.sdk.model.operation.TransferOperation
import ch.decent.sdk.model.toChainObject
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class ValidationApiTest(channel: Channel) : BaseApiTest(channel) {

  private val trx
    get() = api.transactionApi.createTransaction(TransferOperation(Helpers.account, Helpers.account2, AssetAmount(1)))

  @Test fun `should get required signatures`() {
    val test = trx.flatMap { api.validationApi.getRequiredSignatures(it, listOf(Helpers.public.address(), Helpers.public2.address())) }
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue(listOf(Helpers.public.address()))
  }

  @Test fun `should get potential signatures`() {
    val test = trx.flatMap { api.validationApi.getPotentialSignatures(it) }
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue(listOf(Helpers.public.address()))
  }

  @Test fun `should verify signed transaction`() {
    val test = trx.flatMap { api.validationApi.verifyAuthority(it.withSignature(Helpers.credentials.keyPair)) }
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue(true)
  }

  @Test fun `should verify signers for account`() {
    val test = api.validationApi.verifyAccountAuthority(Helpers.accountName2, listOf(Helpers.public2.address()))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertValue(true)
  }

  @Test fun `should validate signed transaction`() {
    val test = trx.flatMap { api.validationApi.validateTransaction(it.withSignature(Helpers.credentials.keyPair)) }
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get fee for transfer OP`() {
    val test = api.validationApi.getFeeForType(OperationType.TRANSFER2_OPERATION, "1.3.35".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

}
