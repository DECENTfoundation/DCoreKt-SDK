package ch.decent.sdk.api

import ch.decent.sdk.*
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.address
import ch.decent.sdk.model.*
import ch.decent.sdk.net.serialization.bytes
import ch.decent.sdk.net.ws.CustomWebSocketService
import ch.decent.sdk.utils.hex
import ch.decent.sdk.utils.unhex
import io.reactivex.schedulers.Schedulers
import org.amshove.kluent.`should be equal to`
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.slf4j.LoggerFactory
import java.math.BigInteger
import kotlin.reflect.jvm.internal.impl.types.VarianceCheckerKt

class OperationsTest {

  private lateinit var mockWebSocket: CustomWebSocketService
  private lateinit var api: DCoreApi

  @Before fun init() {
    val logger = LoggerFactory.getLogger("RxWebSocket")
    mockWebSocket = CustomWebSocketService().apply { start() }
//    api = DCoreSdk.createForWebSocket(client(logger), mockWebSocket.getUrl(), logger)
    api = DCoreSdk.createForWebSocket(client(logger), url, logger)
  }

  @After fun finish() {
    mockWebSocket.shutdown()
  }

  @Test fun `should fail for HTTP`() {
    api = DCoreSdk.createForHttp(client(), restUrl)

    val test = api.broadcastApi.broadcastWithCallback(private, emptyList())
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
        .enqueue(
            """{"method":"call","params":[0,"get_chain_id",[]],"id":0}""",
            """{"id":0,"result":"17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc"}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_dynamic_global_properties",[]],"id":1}""",
            """{"id":1,"result":{"id":"2.1.0","head_block_number":3441407,"head_block_id":"003482ff012880f806baa6f220538425804136be","time":"2018-12-19T14:08:30","current_miner":"1.4.9","next_maintenance_time":"2018-12-20T00:00:00","last_budget_time":"2018-12-19T00:00:00","unspent_fee_budget":11400166,"mined_rewards":"308728000000","miner_budget_from_fees":22030422,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":8,"recently_missed_count":0,"current_aslot":9281631,"recent_slots_filled":"317672346624442248850332726400554761855","dynamic_flags":0,"last_irreversible_block_num":3441407}}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_required_fees",[[[39,{"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7","nonce":735604672334802432},"fee":{"amount":0,"asset_id":"1.3.0"}}]],"1.3.0"]],"id":2}""",
            """{"id":2,"result":[{"amount":500000,"asset_id":"1.3.0"}]}"""
        )
        .enqueue(
            """{"method":"call","params":[2,"broadcast_transaction_with_callback",[4,{"expiration":"2018-12-19T14:09:01","ref_block_num":33535,"ref_block_prefix":4169148417,"extensions":[],"operations":[[39,{"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7","nonce":735604672334802432},"fee":{"amount":500000,"asset_id":"1.3.0"}}]],"signatures":["202760acbe00037b01faa2fb86da9b22a6c3cd739a8de63f55e40b47b3a94ba46f20063c040d8b34de25b36379e96749f2d941f45a221e8c381a821ddb295a0e80"]}]],"id":3}""",
            """{"method":"notice","params":[4,[{"id":"30429898f56b61a01691f195aed6290525320ba3","block_num":3441408,"trx_num":0,"trx":{"ref_block_num":33535,"ref_block_prefix":4169148417,"expiration":"2018-12-19T14:09:01","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["202760acbe00037b01faa2fb86da9b22a6c3cd739a8de63f55e40b47b3a94ba46f20063c040d8b34de25b36379e96749f2d941f45a221e8c381a821ddb295a0e80"],"operation_results":[[0,{}]]}}]]}"""
        )

    val op = TransferOperation(
        account,
        account2,
        AssetAmount(1),
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

  @Ignore
  @Test fun `should create account`() {
    val key = ECKeyPair.fromBase58(private)
    val op = AccountCreateOperation(account, "hello.johnny", public.address())
    val trx = api.transactionApi.createTransaction(op)
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

  @Test fun `should send message`() {
    val credentials = Credentials(account, ECKeyPair.fromBase58(private))
    val op = api.messagingApi.createMessageOperation(credentials, account2, "hello messaging api")
        .blockingGet()

    val trx = api.transactionApi.createTransaction(op)
        .map { it.withSignature(credentials.keyPair) }
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