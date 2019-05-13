package ch.decent.sdk.api

import ch.decent.sdk.*
import ch.decent.sdk.crypto.address
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class AccountApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `account should not exist`() {
    val test = api.accountApi.exist("invalid-account")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue(false)
  }

  @Test fun `should get account by id`() {
    val test = api.accountApi.get(Helpers.account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get account by name`() {
    val test = api.accountApi.getByName(Helpers.accountName)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get account by name or id`() {
    val test = api.accountApi.get("1.2.12")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get account count`() {
    val test = api.accountApi.countAll()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get account references by address`() {
    val test = api.accountApi.findAllReferencesByKeys(listOf(Helpers.public.address())).map { it.single().first() }
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get account references`() {
    val test = api.accountApi.findAllReferencesByAccount(Helpers.account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get accounts by ids`() {
    val test = api.accountApi.getAll(listOf(Helpers.account, Helpers.account2))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get full accounts`() {
    val test = api.accountApi.getFullAccounts(listOf("u961279ec8b7ae7bd62f304f7c1c3d345", "1.2.34"))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get accounts by names`() {
    val test = api.accountApi.getAllByNames(listOf(Helpers.accountName, Helpers.accountName2))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should lookup accounts by lower bound`() {
    val test = api.accountApi.listAllRelative("alax", 10)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `search accounts by term`() {
    val test = api.accountApi.findAll("decent")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Suppress("DEPRECATION")
  @Test fun `search account history`() {
    val test = api.accountApi.searchAccountHistory(Helpers.account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue { it.isNotEmpty() }
  }

  @Test fun `should create credentials`() {
    val test = api.accountApi.createCredentials(Helpers.accountName, Helpers.private)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}