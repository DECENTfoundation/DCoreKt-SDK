package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.crypto.address
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.operation.OperationType
import ch.decent.sdk.model.operation.TransferOperation
import ch.decent.sdk.testCheck
import org.junit.Test

class ValidationApiTest(channel: Channel) : BaseApiTest(channel) {

  private val trx
    get() = api.transactionApi.createTransaction(TransferOperation(Helpers.account, Helpers.account2, AssetAmount(1)))

  @Test fun `should get required signatures`() {
    trx.flatMap { api.validationApi.getRequiredSignatures(it, listOf(Helpers.public.address(), Helpers.public2.address())) }.testCheck {
      assertValue(listOf(Helpers.public.address()))
    }
  }

  @Test fun `should get potential signatures`() {
    trx.flatMap { api.validationApi.getPotentialSignatures(it) }.testCheck {
      assertValue(listOf(Helpers.public.address()))
    }
  }

  @Test fun `should verify signed transaction`() {
    trx.flatMap { api.validationApi.verifyAuthority(it.withSignature(Helpers.credentials.keyPair)) }.testCheck {
      assertValue(true)
    }
  }

  @Test fun `should verify signers for account`() {
    api.validationApi.verifyAccountAuthority(Helpers.accountName2, listOf(Helpers.public2.address())).testCheck {
      assertValue(true)
    }
  }

  @Test fun `should validate signed transaction`() {
    trx.flatMap { api.validationApi.validateTransaction(it.withSignature(Helpers.credentials.keyPair)) }.testCheck()
  }

  @Test fun `should get fee for transfer OP`() {
    api.validationApi.getFeeForType(OperationType.TRANSFER2_OPERATION, Helpers.createAssetId).testCheck()
  }

}
