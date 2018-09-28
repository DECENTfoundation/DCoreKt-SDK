package ch.decent.sdk.api

import ch.decent.sdk.*
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.net.ws.CustomWebSocketService
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory

abstract class AccountApiTest : TimeOutTest() {

  private lateinit var api: DCoreApi
  private lateinit var mockWebServer: CustomWebSocketService

  abstract fun api(): DCoreApi

  @Before fun init() {
    mockWebServer = CustomWebSocketService().apply { start() }
    api = api()
  }

  @After fun finish() {
    mockWebServer.shutdown()
  }

  @Test fun `get account by id`() {
    val test = api.account.getAccount(account.objectId)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get account by id not found`() {
    val test = api.account.getAccount("1.2.34000")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(ObjectNotFoundException::class.java)
  }

  @Test fun `get account by address`() {
    val test = api.account.getAccount(public)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get account by address not found`() {
    val test = api.account.getAccount("DCT5Abm5dCdy3hJ1C5ckXkqUH2Me7dXqi9Y7yjn9ACaiSJ9h8r8mL")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(ObjectNotFoundException::class.java)
  }

  @Test fun `get account by name`() {
    val test = api.account.getAccount(accountName)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get account by name not found`() {
    val test = api.account.getAccount("helloooo")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(ObjectNotFoundException::class.java)
  }

  @Test fun `search account history`() {
    val test = api.account.searchAccountHistory(account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue { it.isNotEmpty() }
  }

  @Test fun `search account history not found`() {
    val test = api.account.searchAccountHistory("1.2.333333".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue(emptyList())
  }
}

class AccountApiRpcTest : AccountApiTest() {
  override fun api(): DCoreApi = with(LoggerFactory.getLogger("RpcService")) {
    DCoreSdk.createForHttp(client(this), restUrl, this)
  }
}

class AccountApiWsTest : AccountApiTest() {
  override fun api(): DCoreApi = with(LoggerFactory.getLogger("RxWebSocket")) {
    DCoreSdk.createForWebSocket(client(this), url, this)
  }
}