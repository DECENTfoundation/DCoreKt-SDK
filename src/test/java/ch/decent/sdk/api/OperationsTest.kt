package ch.decent.sdk.api

import ch.decent.sdk.*
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.TransferOperation
import ch.decent.sdk.net.ws.CustomWebSocketService
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException
import java.math.BigInteger

class OperationsTest {

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

  @Test fun `should fail for HTTP`() {
    api = DCoreSdk.createForHttp(client(), restUrl)

    val test = api.broadcastApi.broadcast(private, emptyList())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(IllegalArgumentException::class.java)
  }

  @Test fun `should create a transfer operation and broadcast`() {
    val key = ECKeyPair.fromBase58(private)
    val memo = Memo("hello memo here i am", key, Address.decode(public2), BigInteger("735604672334802432"))
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"get_chain_id",[]],"id":0}""", """{"id":0,"result":"17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc"}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":1}""", """{"id":1,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")
        .enqueue("""{"method":"call","params":[2,"get_dynamic_global_properties",[]],"id":3}""", """{"id":3,"result":{"id":"2.1.0","head_block_number":599091,"head_block_id":"00092433e84dedb18c9b9a378cfea8cdfbb2b637","time":"2018-06-04T12:25:00","current_miner":"1.4.8","next_maintenance_time":"2018-06-05T00:00:00","last_budget_time":"2018-06-04T00:00:00","unspent_fee_budget":96490,"mined_rewards":"301032000000","miner_budget_from_fees":169714,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":1,"recently_missed_count":0,"current_aslot":5859543,"recent_slots_filled":"329648380685469039951165571643239038463","dynamic_flags":0,"last_irreversible_block_num":599091}}""")
        .enqueue("""{"method":"call","params":[2,"get_required_fees",[[[39,{"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"${memo.message}","nonce":${memo.nonce}},"fee":{"amount":0,"asset_id":"1.3.0"}}]],"1.3.0"]],"id":4}""", """{"id":4,"result":[{"amount":500000,"asset_id":"1.3.0"}]}""")
        .enqueue("""{"method":"call","params":[3,"broadcast_transaction_with_callback",[5,{"expiration":"2018-06-04T12:25:32","ref_block_num":9267,"ref_block_prefix":2985119208,"extensions":[],"operations":[[39,{"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"${memo.message}","nonce":${memo.nonce}},"fee":{"amount":500000,"asset_id":"1.3.0"}}]],"signatures":["203c168ef8b88e5702cedd7ee2985a67a63fb15a58023323828c0b843c37eb4a6d1b45665414488d83262f7116ac6a0116943d512352c8e858fe636b3bec195265"]}]],"id":6}""", """{"method":"notice","params":[5,[{"id":"2fd68fb4e7ec4b30b465263ed10177fe8938a8a9","block_num":599092,"trx_num":0,"trx":{"ref_block_num":9267,"ref_block_prefix":2985119208,"expiration":"2018-06-04T12:25:32","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["203c168ef8b88e5702cedd7ee2985a67a63fb15a58023323828c0b843c37eb4a6d1b45665414488d83262f7116ac6a0116943d512352c8e858fe636b3bec195265"],"operation_results":[[0,{}]]}}]]}""")
        .enqueue("""{"method":"call","params":[1,"network_broadcast",[]],"id":7}""", """{"id":7,"result":3}""")

    val op = TransferOperation(
        account,
        account2,
        AssetAmount(1500000),
        memo
    )

    val trx = api.transactionApi.createTransaction(listOf(op))
        .map { it.withSignature(key) }
        .blockingGet()

    val test = api.broadcastApi.broadcastWithCallback(trx)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue { it.id == trx.id }

  }

}