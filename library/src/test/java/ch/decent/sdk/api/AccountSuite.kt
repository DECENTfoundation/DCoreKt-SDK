package ch.decent.sdk.api

import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.Helpers
import ch.decent.sdk.crypto.address
import ch.decent.sdk.model.Authority
import ch.decent.sdk.model.toObjectId
import ch.decent.sdk.testCheck
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized
import org.junit.runners.Suite

@Suite.SuiteClasses(AccountOperationsTest::class, AccountApiTest::class)
@RunWith(Suite::class)
class AccountSuite

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AccountOperationsTest : BaseOperationsTest() {

  @Test fun `accounts-1 should create account`() {
    api.accountApi.create(Helpers.credentials, Helpers.createAccount, Helpers.public.address())
        .testCheck()
  }

  @Test fun `accounts-2 should make a transfer to new account`() {
    api.accountApi.transfer(Helpers.credentials, Helpers.createAccount, DCoreConstants.DCT.amount(1.0))
        .testCheck()
  }

  @Test fun `accounts-3 should update credentials on a new account`() {
    api.accountApi.createCredentials(Helpers.createAccount, Helpers.private)
        .flatMap { api.accountApi.update(it, active = Authority(Helpers.public2.address())) }
        .testCheck()
  }

  @Test fun `accounts-4 should make a vote on a new account`() {
    api.accountApi.createCredentials(Helpers.createAccount, Helpers.private2)
        .flatMap { api.miningApi.vote(it, listOf("1.4.4".toObjectId())) }
        .testCheck()
  }
}

@RunWith(Parameterized::class)
class AccountApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `account should not exist`() {
    api.accountApi.exist("invalid-account").testCheck { assertValue(false) }
  }

  @Test fun `should get account by id`() {
    api.accountApi.get(Helpers.account).testCheck()
  }

  @Test fun `should get account by name`() {
    api.accountApi.getByName(Helpers.accountName).testCheck()
  }

  @Test fun `should get account by name or id`() {
    api.accountApi.get("1.2.12").testCheck()
  }

  @Test fun `should get account count`() {
    api.accountApi.countAll().testCheck()
  }

  @Test fun `should get account references by address`() {
    api.accountApi.findAllReferencesByKeys(listOf(Helpers.public.address())).map { it.single().first() }.testCheck()
  }

  @Test fun `should get account references`() {
    api.accountApi.findAllReferencesByAccount(Helpers.account).testCheck()
  }

  @Test fun `should get accounts by ids`() {
    api.accountApi.getAll(listOf(Helpers.account, Helpers.account2)).testCheck()
  }

  @Test fun `should get full accounts`() {
    api.accountApi.getFullAccounts(listOf("u961279ec8b7ae7bd62f304f7c1c3d345", "1.2.34")).testCheck()
  }

  @Test fun `should get accounts by names`() {
    api.accountApi.getAllByNames(listOf(Helpers.accountName, Helpers.accountName2)).testCheck()
  }

  @Test fun `should lookup accounts by lower bound`() {
    api.accountApi.listAllRelative("alax", 10).testCheck()
  }

  @Test fun `search accounts by term`() {
    api.accountApi.findAll("decent").testCheck()
  }

  @Suppress("DEPRECATION")
  @Test fun `search account history`() {
    api.accountApi.searchAccountHistory(Helpers.account).testCheck { assertValue { it.isNotEmpty() } }
  }

  @Test fun `should create credentials`() {
    api.accountApi.createCredentials(Helpers.accountName, Helpers.private).testCheck()
  }
}
