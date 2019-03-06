package ch.decent.sdk.api

import ch.decent.sdk.*
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
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
            """{"id":3,"result":{"id":"2.1.0","head_block_number":4364411,"head_block_id":"0042987bdf9453eac6b4257f78db4cc790219c7c","time":"2019-02-22T15:57:00","current_miner":"1.4.8","next_maintenance_time":"2019-02-23T00:00:00","last_budget_time":"2019-02-22T00:00:00","unspent_fee_budget":3354697,"mined_rewards":"349021000000","miner_budget_from_fees":7373155,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":9,"recently_missed_count":4,"current_aslot":10405938,"recent_slots_filled":"318349415066694580176222834493180803053","dynamic_flags":0,"last_irreversible_block_num":4364411}}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_required_fees",[[[18,{"id":1,"payer":"1.2.34","required_auths":["1.2.34"],"data":"7b2266726f6d223a22312e322e3334222c227265636569766572735f64617461223a5b7b22746f223a22312e322e3335222c2264617461223a2239623933633537343130656137643830626136373530383139653036643665326237626138316366393162626637653331643236353930396234363739306164222c227075625f746f223a224443543662566d696d745953765751747764726b56565147486b5673544a5a564b74426955716634596d4a6e724a506e6b38395150222c226e6f6e6365223a31303231363235343531393132323634363031367d5d2c227075625f66726f6d223a22444354364d41355451513655624d794d614c506d505845325379683547335a566876355362466564714c507164464368536571547a227d","fee":{"amount":0,"asset_id":"1.3.0"}}]],"1.3.0"]],"id":4}""",
            """{"id":4,"result":[{"amount":500002,"asset_id":"1.3.0"}]}"""
        )
        .enqueue(
            """{"method":"call","params":[2,"broadcast_transaction_with_callback",[6,{"expiration":"2019-02-22T15:57:32","ref_block_num":39035,"ref_block_prefix":3931346143,"extensions":[],"operations":[[18,{"id":1,"payer":"1.2.34","required_auths":["1.2.34"],"data":"7b2266726f6d223a22312e322e3334222c227265636569766572735f64617461223a5b7b22746f223a22312e322e3335222c2264617461223a2239623933633537343130656137643830626136373530383139653036643665326237626138316366393162626637653331643236353930396234363739306164222c227075625f746f223a224443543662566d696d745953765751747764726b56565147486b5673544a5a564b74426955716634596d4a6e724a506e6b38395150222c226e6f6e6365223a31303231363235343531393132323634363031367d5d2c227075625f66726f6d223a22444354364d41355451513655624d794d614c506d505845325379683547335a566876355362466564714c507164464368536571547a227d","fee":{"amount":500002,"asset_id":"1.3.0"}}]],"signatures":["1f597c8d0f6fdda0f5afd3968ef64e6bbc04bc14d30be0d3d2b18c92ec2818a14c44ac4a1922f99fefab2b42a2616577e955a6df61d1d185551c6b0253bbe4e2db"]}]],"id":5}""",
            """{"method":"notice","params":[6,[{"id":"177476c0cafa8dddc30bcd6449d3d47195e00d09","block_num":4364412,"trx_num":0,"trx":{"ref_block_num":39035,"ref_block_prefix":3931346143,"expiration":"2019-02-22T15:57:32","operations":[[18,{"fee":{"amount":500002,"asset_id":"1.3.0"},"payer":"1.2.34","required_auths":["1.2.34"],"id":1,"data":"7b2266726f6d223a22312e322e3334222c227265636569766572735f64617461223a5b7b22746f223a22312e322e3335222c2264617461223a2239623933633537343130656137643830626136373530383139653036643665326237626138316366393162626637653331643236353930396234363739306164222c227075625f746f223a224443543662566d696d745953765751747764726b56565147486b5673544a5a564b74426955716634596d4a6e724a506e6b38395150222c226e6f6e6365223a31303231363235343531393132323634363031367d5d2c227075625f66726f6d223a22444354364d41355451513655624d794d614c506d505845325379683547335a566876355362466564714c507164464368536571547a227d"}]],"extensions":[],"signatures":["1f597c8d0f6fdda0f5afd3968ef64e6bbc04bc14d30be0d3d2b18c92ec2818a14c44ac4a1922f99fefab2b42a2616577e955a6df61d1d185551c6b0253bbe4e2db"],"operation_results":[[0,{}]]}}]]}"""
        )

    val sender = api.accountApi.get(account).blockingGet()
    val recipient = api.accountApi.get(account2).blockingGet()
    val keyPair = ECKeyPair.fromBase58(private)
    val msg = Memo("hello messaging api", keyPair, recipient.options.memoKey, BigInteger("10216254519122646016"))
    val payloadReceiver = MessagePayloadReceiver(recipient.id, msg.message, recipient.options.memoKey, msg.nonce)
    val payload = MessagePayload(sender.id, listOf(payloadReceiver), sender.options.memoKey)
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

  @Test fun `should send unencrypted message`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_chain_id",[]],"id":0}""",
            """{"id":0,"result":"17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc"}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_dynamic_global_properties",[]],"id":1}""",
            """{"id":1,"result":{"id":"2.1.0","head_block_number":4364343,"head_block_id":"004298378768bfcc66bb1c8974b6d75d5b723c8d","time":"2019-02-22T15:49:55","current_miner":"1.4.1","next_maintenance_time":"2019-02-23T00:00:00","last_budget_time":"2019-02-22T00:00:00","unspent_fee_budget":3383665,"mined_rewards":"346505000000","miner_budget_from_fees":7373155,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":9,"recently_missed_count":0,"current_aslot":10405853,"recent_slots_filled":"249850723296557969923040622545875230591","dynamic_flags":0,"last_irreversible_block_num":4364343}}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_required_fees",[[[18,{"id":1,"payer":"1.2.34","required_auths":["1.2.34"],"data":"7b2266726f6d223a22312e322e3334222c227265636569766572735f64617461223a5b7b22746f223a22312e322e3335222c2264617461223a2230303030303030303638363536633663366632303664363537333733363136373639366536373230363137303639323037353665363536653633373237393730373436353634227d5d7d","fee":{"amount":0,"asset_id":"1.3.0"}}]],"1.3.0"]],"id":2}""",
            """{"id":2,"result":[{"amount":500001,"asset_id":"1.3.0"}]}"""
        )
        .enqueue(
            """{"method":"call","params":[2,"broadcast_transaction_with_callback",[4,{"expiration":"2019-02-22T15:50:39","ref_block_num":38967,"ref_block_prefix":3435096199,"extensions":[],"operations":[[18,{"id":1,"payer":"1.2.34","required_auths":["1.2.34"],"data":"7b2266726f6d223a22312e322e3334222c227265636569766572735f64617461223a5b7b22746f223a22312e322e3335222c2264617461223a2230303030303030303638363536633663366632303664363537333733363136373639366536373230363137303639323037353665363536653633373237393730373436353634227d5d7d","fee":{"amount":500001,"asset_id":"1.3.0"}}]],"signatures":["1f43188b754f7e443675c5240d5a4ae2e326a53335b5f35908eea5b55af67bfa1230a73021b4db6a2cc0dd6743ff9c42dbbc1cfa5b06cb9b49edae6998ab83d7b8"]}]],"id":3}""",
            """{"method":"notice","params":[4,[{"id":"4cafe3a8f039ad8f370782b7572ae77207940a36","block_num":4364344,"trx_num":0,"trx":{"ref_block_num":38967,"ref_block_prefix":3435096199,"expiration":"2019-02-22T15:50:39","operations":[[18,{"fee":{"amount":500001,"asset_id":"1.3.0"},"payer":"1.2.34","required_auths":["1.2.34"],"id":1,"data":"7b2266726f6d223a22312e322e3334222c227265636569766572735f64617461223a5b7b22746f223a22312e322e3335222c2264617461223a2230303030303030303638363536633663366632303664363537333733363136373639366536373230363137303639323037353665363536653633373237393730373436353634227d5d7d"}]],"extensions":[],"signatures":["1f43188b754f7e443675c5240d5a4ae2e326a53335b5f35908eea5b55af67bfa1230a73021b4db6a2cc0dd6743ff9c42dbbc1cfa5b06cb9b49edae6998ab83d7b8"],"operation_results":[[0,{}]]}}]]}"""
        )

    val credentials = Credentials(account, private)
    val op = api.messagingApi.createMessageOperationUnencrypted(credentials, account2, "hello messaging api unencrypted")

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