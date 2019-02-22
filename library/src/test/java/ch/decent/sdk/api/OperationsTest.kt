package ch.decent.sdk.api

import ch.decent.sdk.*
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.address
import ch.decent.sdk.model.*
import ch.decent.sdk.net.ws.CustomWebSocketService
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.slf4j.LoggerFactory
import java.math.BigInteger

class OperationsTest {

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
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_chain_id",[]],"id":0}""",
            """{"id":0,"result":"17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc"}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_objects",[["1.2.34"]]],"id":1}""",
            """{"id":1,"result":[{"id":"1.2.34","registrar":"1.2.15","name":"u961279ec8b7ae7bd62f304f7c1c3d345","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"rights_to_publish":{"is_publishing_manager":false,"publishing_rights_received":[],"publishing_rights_forwarded":[]},"statistics":"2.5.34","top_n_control_flags":0}]}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_objects",[["1.2.35"]]],"id":2}""",
            """{"id":2,"result":[{"id":"1.2.35","registrar":"1.2.15","name":"u3a7b78084e7d3956442d5a4d439dad51","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP",1]]},"options":{"memo_key":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"rights_to_publish":{"is_publishing_manager":false,"publishing_rights_received":[],"publishing_rights_forwarded":[]},"statistics":"2.5.35","top_n_control_flags":0}]}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_dynamic_global_properties",[]],"id":3}""",
            """{"id":3,"result":{"id":"2.1.0","head_block_number":4363486,"head_block_id":"004294de4a5f8586b3bc9ce45ee8d9a1da52051a","time":"2019-02-22T14:23:15","current_miner":"1.4.2","next_maintenance_time":"2019-02-23T00:00:00","last_budget_time":"2019-02-22T00:00:00","unspent_fee_budget":3748747,"mined_rewards":"314796000000","miner_budget_from_fees":7373155,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":9,"recently_missed_count":2,"current_aslot":10404813,"recent_slots_filled":"338620800214496657057279045487686548475","dynamic_flags":0,"last_irreversible_block_num":4363486}}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_required_fees",[[[18,{"id":1,"payer":"1.2.34","required_auths":["1.2.34"],"data":"7b2266726f6d223a22312e322e3334222c227075625f66726f6d223a22444354364d41355451513655624d794d614c506d505845325379683547335a566876355362466564714c507164464368536571547a222c227265636569766572735f64617461223a5b7b22746f223a22312e322e3335222c227075625f746f223a224443543662566d696d745953765751747764726b56565147486b5673544a5a564b74426955716634596d4a6e724a506e6b38395150222c226e6f6e6365223a31303231363235343531393132323634363031362c2264617461223a2239623933633537343130656137643830626136373530383139653036643665326237626138316366393162626637653331643236353930396234363739306164227d5d7d","fee":{"amount":0,"asset_id":"1.3.0"}}]],"1.3.0"]],"id":4}""",
            """{"id":4,"result":[{"amount":500002,"asset_id":"1.3.0"}]}"""
        )
        .enqueue(
            """{"method":"call","params":[2,"broadcast_transaction_with_callback",[6,{"expiration":"2019-02-22T14:23:46","ref_block_num":38110,"ref_block_prefix":2256887626,"extensions":[],"operations":[[18,{"id":1,"payer":"1.2.34","required_auths":["1.2.34"],"data":"7b2266726f6d223a22312e322e3334222c227075625f66726f6d223a22444354364d41355451513655624d794d614c506d505845325379683547335a566876355362466564714c507164464368536571547a222c227265636569766572735f64617461223a5b7b22746f223a22312e322e3335222c227075625f746f223a224443543662566d696d745953765751747764726b56565147486b5673544a5a564b74426955716634596d4a6e724a506e6b38395150222c226e6f6e6365223a31303231363235343531393132323634363031362c2264617461223a2239623933633537343130656137643830626136373530383139653036643665326237626138316366393162626637653331643236353930396234363739306164227d5d7d","fee":{"amount":500002,"asset_id":"1.3.0"}}]],"signatures":["1f1d12b06fc642d5cc572187388249e1ce96406b04ddf8f1372157eccdd4e2d32d1bde142ea81e7ced1cdfbc0e9e2d1e895ee9fb581a4b2992c637a04a15bcd988"]}]],"id":5}""",
            """{"method":"notice","params":[6,[{"id":"d4c69f6c02161cbbd0b6ee71d695624d496f2d14","block_num":4363488,"trx_num":0,"trx":{"ref_block_num":38110,"ref_block_prefix":2256887626,"expiration":"2019-02-22T14:23:46","operations":[[18,{"fee":{"amount":500002,"asset_id":"1.3.0"},"payer":"1.2.34","required_auths":["1.2.34"],"id":1,"data":"7b2266726f6d223a22312e322e3334222c227075625f66726f6d223a22444354364d41355451513655624d794d614c506d505845325379683547335a566876355362466564714c507164464368536571547a222c227265636569766572735f64617461223a5b7b22746f223a22312e322e3335222c227075625f746f223a224443543662566d696d745953765751747764726b56565147486b5673544a5a564b74426955716634596d4a6e724a506e6b38395150222c226e6f6e6365223a31303231363235343531393132323634363031362c2264617461223a2239623933633537343130656137643830626136373530383139653036643665326237626138316366393162626637653331643236353930396234363739306164227d5d7d"}]],"extensions":[],"signatures":["1f1d12b06fc642d5cc572187388249e1ce96406b04ddf8f1372157eccdd4e2d32d1bde142ea81e7ced1cdfbc0e9e2d1e895ee9fb581a4b2992c637a04a15bcd988"],"operation_results":[[0,{}]]}}]]}"""
        )

    val sender = api.accountApi.get(account).blockingGet()
    val recipient = api.accountApi.get(account2).blockingGet()
    val keyPair = ECKeyPair.fromBase58(private)
    val msg = Memo("hello messaging api", keyPair, recipient.options.memoKey, BigInteger("10216254519122646016"))
    val payloadReceiver = MessagePayloadReceiver(recipient.id, recipient.options.memoKey, msg.nonce, msg.message)
    val payload = MessagePayload(sender.id, sender.options.memoKey, listOf(payloadReceiver))
    val op = SendMessageOperation(api.core.gson.toJson(payload), sender.id)

//    we cannot set the nonce with api call
//    val op = api.messagingApi.createMessageOperation(credentials, account2, "hello messaging api")
//        .blockingGet()

    val trx = api.transactionApi.createTransaction(op)
        .map { it.withSignature(keyPair) }
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