package ch.decent.sdk.api

import ch.decent.sdk.*
import ch.decent.sdk.net.ws.CustomWebSocketService
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException

// todo, unable to cancel callback
class CallbackApiTest {

  private lateinit var mockWebSocket: CustomWebSocketService
  private lateinit var api: DCoreApi

  @Before fun init() {
    val logger = LoggerFactory.getLogger("RxWebSocket")
    mockWebSocket = CustomWebSocketService().apply { start() }
    api = DCoreSdk.createForWebSocket(client(logger), mockWebSocket.getUrl(), logger)
//    api = DCoreSdk.createForWebSocket(client(logger), url, logger)
  }

  @After fun finish() {
    mockWebSocket.shutdown()
  }

  @Test fun `should fail for HTTP`() {
    api = DCoreSdk.createForHttp(client(), restUrl)

    val test = api.callbackApi.onBlockApplied()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(IllegalArgumentException::class.java)
  }

  @Test fun `should cancel all subscriptions`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"cancel_all_subscriptions",[]],"id":1}""",
            """{"id":1,"result":null}"""
        )

    val test = api.callbackApi.cancelAll()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should set block applied callback`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"set_block_applied_callback",[2]],"id":1}""",
            """{"method":"notice","params":[2,["003484ba751fca1fea8177d8bab75116fb8be26a"]]}"""
        )

    val test = api.callbackApi.onBlockApplied()
        .take(1)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Ignore
  @Test fun `should set content update callback`() {
    val test = api.callbackApi.onContentUpdate("http://some.uri")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should set pending transaction callback`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"set_pending_transaction_callback",[2]],"id":1}""",
            """{"method":"notice","params":[2,[{"ref_block_num":34021,"ref_block_prefix":2852349049,"expiration":"2018-12-19T14:59:00","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.11889","amount":{"amount":100000000,"asset_id":"1.3.0"},"memo":{"from":"DCT1111111111111111111111111111111114T1Anm","to":"DCT1111111111111111111111111111111114T1Anm","nonce":0,"message":"00000000"},"extensions":[]}]],"extensions":[],"signatures":["200337f7bc315bf791cef2925a0b3379fe441f8a82d51ea73bc44053dd70e7296b71755bceb68216f344e66e4b2f13206d5f8e0c13183597ce6bbb20d868161c33"]}]]}"""
        )

    val test = api.callbackApi.onPendingTransaction()
        .take(1)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should set subscribe callback`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"set_subscribe_callback",[2,false]],"id":1}""",
            """{"method":"notice","params":[2,[[{"id":"2.7.34030","block_id":"003484ee95d911b9a2c7aeac303c5c0af3dabed6"},{"id":"1.4.5","miner_account":"1.2.8","last_aslot":9282243,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.0","vote_id":"0:4","total_votes":"994847645155","url":"","total_missed":478994,"last_confirmed_block_num":3441902},{"id":"1.9.0","owner":"1.2.8","balance":{"amount":"13193031582889","asset_id":"1.3.0"},"policy":[1,{"vesting_seconds":86400,"start_claim":"1970-01-01T00:00:00","coin_seconds_earned":"1139874731851536000","coin_seconds_earned_last_update":"2018-12-19T14:59:30"}]},{"id":"2.1.0","head_block_number":3441902,"head_block_id":"003484ee95d911b9a2c7aeac303c5c0af3dabed6","time":"2018-12-19T14:59:30","current_miner":"1.4.5","next_maintenance_time":"2018-12-20T00:00:00","last_budget_time":"2018-12-19T00:00:00","unspent_fee_budget":10769536,"mined_rewards":"327043000000","miner_budget_from_fees":22030422,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":12,"recently_missed_count":8,"current_aslot":9282243,"recent_slots_filled":"324323827292130661946547205444974147516","dynamic_flags":0,"last_irreversible_block_num":3441902},{"id":"1.4.12","miner_account":"1.2.27","last_aslot":0,"signing_key":"DCT8cYDtKZvcAyWfFRusy6ja1Hafe9Ys4UPJS92ajTmcrufHnGgjp","vote_id":"0:11","total_votes":"1530588818359","url":"http://ardstudio.studenthosting.sk","total_missed":284573,"last_confirmed_block_num":0},{"id":"1.4.13","miner_account":"1.2.85","last_aslot":0,"signing_key":"DCT6ZNZ5KGadKr346doCUvUUYu1fTgDoTwEV1aCbrNqgP82oN9ADt","vote_id":"0:12","total_votes":"5116212862873020","url":"my new URL","total_missed":290199,"last_confirmed_block_num":0}]]]}"""
        )

    val test = api.callbackApi.onGlobal(false)
        .take(1)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}