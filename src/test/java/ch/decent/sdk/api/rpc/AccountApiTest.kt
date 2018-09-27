package ch.decent.sdk.api.rpc

import ch.decent.sdk.*
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.net.ws.CustomWebSocketService
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory

class AccountApiTest : TimeOutTest() {

  private lateinit var api: DCoreApi
  private lateinit var mockWebServer: CustomWebSocketService

  @Before fun init() {
    mockWebServer = CustomWebSocketService().apply { start() }
    val logger = LoggerFactory.getLogger("RpcService")
    api = DCoreSdk.createForHttp(client(logger), restUrl, logger)
  }

  @After fun finish() {
    mockWebServer.shutdown()
  }

  @Test fun `get accounts by ids`() {
    val test = api.account.getAccountsByIds(listOf("1.2.31114".toChainObject()))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get accounts by pub key references`() {
    val test = api.account.getAccountIdsByKeys(listOf(Address.decode("DCT5Abm5dCdy3hJ1C5ckXkqUH2Me7dXqi9Y7yjn9ACaiSJ9h8r8mL")))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }


}