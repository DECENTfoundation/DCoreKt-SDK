package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.Helpers
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.address
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.toChainObject
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.runners.MethodSorters
import org.slf4j.LoggerFactory
import java.math.BigDecimal

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class OperationsTest {

  companion object {
    private lateinit var timestamp: String

    @BeforeClass @JvmStatic fun before() {
      timestamp = System.currentTimeMillis().toString()
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
    val test = api.accountApi.transfer(Helpers.credentials, accountName, DCoreConstants.DCT.amount(BigDecimal(1)))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `accounts-3 should make a vote on a new account`() {
    val test = api.accountApi.createCredentials(accountName, Helpers.private)
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

  @Ignore // already commented
  @Test fun `leave rating and comment`() {
    val test = api.purchaseApi.rateAndComment(
        Credentials("1.2.27".toChainObject(), "5Hxwqx6JJUBYWjQNt8DomTNJ6r6YK8wDJym4CMAH1zGctFyQtzt"),
        "ipfs:QmUuWZihBKYnC7TrhCMjtZrLPrEnPCQLeAkkDEP2tvNcqC",
        2,
        "comment KT"
    )
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}
