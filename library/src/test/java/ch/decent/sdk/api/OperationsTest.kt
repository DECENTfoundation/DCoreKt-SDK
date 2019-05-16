package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.Helpers
import ch.decent.sdk.crypto.address
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.Authority
import ch.decent.sdk.model.CoAuthors
import ch.decent.sdk.model.RegionalPrice
import ch.decent.sdk.model.Synopsis
import ch.decent.sdk.model.toChainObject
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.slf4j.LoggerFactory
import org.threeten.bp.LocalDateTime

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class OperationsTest {

  companion object {
    private lateinit var timestamp: String
    private lateinit var uri: String


    @BeforeClass @JvmStatic fun before() {
      timestamp = System.currentTimeMillis().toString()
      uri = "http://hello.world.io?timestamp=$timestamp"
    }
  }

  private lateinit var api: DCoreApi

  private val accountName: String
    get() = "sdk-account-$timestamp"

  @Before fun init() {
    val logger = LoggerFactory.getLogger("RxWebSocket")
    api = DCoreSdk.createForWebSocket(Helpers.client(logger), Helpers.wsUrl, logger)
  }

  @After fun finish() {
  }

  @Test fun `accounts-1 should create account`() {
    val test = api.accountApi.create(Helpers.credentials, accountName, Helpers.public.address())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `accounts-2 should make a transfer to new account`() {
    val test = api.accountApi.transfer(Helpers.credentials, accountName, DCoreConstants.DCT.amount(1.0))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `accounts-3 should update credentials on a new account`() {
    val test = api.accountApi.createCredentials(accountName, Helpers.private)
        .flatMap { api.accountApi.update(it, active = Authority(Helpers.public2.address())) }
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `accounts-4 should make a vote on a new account`() {
    val test = api.accountApi.createCredentials(accountName, Helpers.private2)
        .flatMap { api.miningApi.vote(it, listOf("1.4.4".toChainObject())) }
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should send message`() {
    val test = api.messagingApi.send(Helpers.credentials, listOf(Helpers.account2 to "test message encrypted t=$timestamp"))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should send unencrypted message`() {
    val test = api.messagingApi.sendUnencrypted(Helpers.credentials, listOf(Helpers.account2 to "test message plain t=$timestamp"))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should make a transfer to content`() {
    val test = api.contentApi.transfer(Helpers.credentials, "2.13.3".toChainObject(), AssetAmount(1), "transfer to content")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `content-1 should add a content`() {
    val test = api.contentApi.add(
        Helpers.credentials,
        CoAuthors(mapOf(Helpers.account2 to 1000)), // 10%
        uri,
        listOf(RegionalPrice(AssetAmount(2))),
        LocalDateTime.now().plusDays(100),
        Synopsis("hello", "world")
    )
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()

  }

  @Test fun `content-2 should update a content`() {
    val test = api.contentApi.update(
        Helpers.credentials,
        uri,
        Synopsis("hello", "update")
    )
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `content-3 should make a purchase`() {
    val test = api.contentApi.purchase(Helpers.credentials, uri)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()

  }

  @Test fun `content-4 should rate and comment a purchased content`() {
    val test = api.purchaseApi.rateAndComment(
        Helpers.credentials,
        uri,
        4,
        "hello comment"
    )
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()

  }

  @Test fun `content-5 should remove a content`() {
    val test = api.contentApi.remove(Helpers.credentials, uri)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()

  }
}
