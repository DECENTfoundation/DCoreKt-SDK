package ch.decent.sdk.api

import ch.decent.sdk.*
import ch.decent.sdk.net.model.request.GetAccountHistory
import ch.decent.sdk.net.ws.CustomWebSocketService
import io.reactivex.schedulers.Schedulers
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runners.Parameterized
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class HistoryApiTest {

  private lateinit var mockWebSocket: CustomWebSocketService
  private lateinit var api: DCoreApi
  private val useMock = true

  @Before fun init() {
    val logger = LoggerFactory.getLogger("RxWebSocket")
    mockWebSocket = CustomWebSocketService().apply { start() }
    api = DCoreSdk.createForWebSocket(client(logger), if (useMock) mockWebSocket.getUrl() else url, logger)
  }

  @After fun finish() {
    mockWebSocket.shutdown()
  }

  @Test fun `should get account history`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"get_account_history",["1.2.34","1.7.0",100,"1.7.0"]],"id":1}""", """{"id":1,"result":[{"id":"1.7.557","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":480676,"trx_in_block":0,"op_in_trx":0,"virtual_op":1231},{"id":"1.7.556","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":480675,"trx_in_block":0,"op_in_trx":0,"virtual_op":1228},{"id":"1.7.456","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":419229,"trx_in_block":0,"op_in_trx":0,"virtual_op":902},{"id":"1.7.455","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":419228,"trx_in_block":0,"op_in_trx":0,"virtual_op":899},{"id":"1.7.368","op":[0,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":150000000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"9637208953715299072","message":"b7da6b1f94635777b2649309af699cee7ece00f31f0a652eb786a03b32ff2714"},"extensions":[]}],"result":[0,{}],"block_num":385238,"trx_in_block":0,"op_in_trx":0,"virtual_op":632},{"id":"1.7.367","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":385122,"trx_in_block":0,"op_in_trx":0,"virtual_op":629}]}""")
        .enqueue("""{"method":"call","params":[1,"history",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    val test = api.history.getAccountHistory(account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should fail for HTTP`() {
    api = DCoreSdk.createForHttp(client(), restUrl)

    val test = api.history.getAccountHistory(account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(IllegalArgumentException::class.java)
  }

}