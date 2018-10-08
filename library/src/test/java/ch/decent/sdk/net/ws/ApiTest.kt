package ch.decent.sdk.net.ws

import ch.decent.sdk.*
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.DumpedPrivateKey
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.address
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
import ch.decent.sdk.utils.hex
import ch.decent.sdk.utils.publicElGamal
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory
import org.threeten.bp.LocalDateTime
import java.math.BigInteger

/**
 * simulate calling the api from different threads
 */
class ApiTest : TimeOutTest() {

  private lateinit var socket: RxWebSocket
  private lateinit var mockWebServer: CustomWebSocketService

  @Before fun init() {
    mockWebServer = CustomWebSocketService().apply { start() }
    val logger = LoggerFactory.getLogger("RxWebSocket")
    socket = RxWebSocket(
        client(logger),
//        mockWebServer.getUrl(),
        url,
        logger = logger,
        gson = DCoreSdk.gsonBuilder.create()
    )
  }

  @After fun finish() {
    mockWebServer.shutdown()
  }




/*

  @Test fun `transfer to content test`() {
    val dpk = DumpedPrivateKey.fromBase58(private)
    val key = ECKeyPair.fromPrivate(dpk.bytes, dpk.compressed)
    mockWebServer
        .enqueue("""{"method":"call","params":[2,"get_dynamic_global_properties",[]],"id":0}""", """{"id":0,"result":{"id":"2.1.0","head_block_number":599091,"head_block_id":"00092433e84dedb18c9b9a378cfea8cdfbb2b637","time":"2018-06-04T12:25:00","current_miner":"1.4.8","next_maintenance_time":"2018-06-05T00:00:00","last_budget_time":"2018-06-04T00:00:00","unspent_fee_budget":96490,"mined_rewards":"301032000000","miner_budget_from_fees":169714,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":1,"recently_missed_count":0,"current_aslot":5859543,"recent_slots_filled":"329648380685469039951165571643239038463","dynamic_flags":0,"last_irreversible_block_num":599091}}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":1}""", """{"id":1,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")
        .enqueue("""{"method":"call","params":[2,"get_required_fees",[[[39,{"from":"1.2.34","to":"2.13.74","amount":{"amount":1500000,"asset_id":"1.3.0"},"fee":{"amount":0,"asset_id":"1.3.0"}}]],"1.3.0"]],"id":3}""", """{"id":3,"result":[{"amount":500000,"asset_id":"1.3.0"}]}""")
        .enqueue("""{"method":"call","params":[3,"broadcast_transaction_with_callback",[27185,{"expiration":"2018-06-04T12:25:35","ref_block_num":9267,"ref_block_prefix":2985119208,"extensions":[],"operations":[[39,{"from":"1.2.34","to":"2.13.74","amount":{"amount":1500000,"asset_id":"1.3.0"},"fee":{"amount":500000,"asset_id":"1.3.0"}}]],"signatures":["1f380d2c1fcff9f4c3ba1349cd3bc9318d8c670c8f204b7e6ea58c0c45bbffc626640a3b02cf04b30c53eeb7ff7158ba2d17b046817212c22ea9f347f67e2e81e9"]}]],"id":4}""", """{"method":"notice","params":[27185,[{"id":"b238a0bec414566c3d3bc9cb06b36179f070b07f","block_num":599092,"trx_num":0,"trx":{"ref_block_num":9267,"ref_block_prefix":2985119208,"expiration":"2018-06-04T12:25:35","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"2.13.74","amount":{"amount":1500000,"asset_id":"1.3.0"},"extensions":[]}]],"extensions":[],"signatures":["1f380d2c1fcff9f4c3ba1349cd3bc9318d8c670c8f204b7e6ea58c0c45bbffc626640a3b02cf04b30c53eeb7ff7158ba2d17b046817212c22ea9f347f67e2e81e9"],"operation_results":[[0,{}]]}}]]}""")
        .enqueue("""{"method":"call","params":[1,"network_broadcast",[]],"id":5}""", """{"id":5,"result":3}""")


    val op = TransferOperation(
        account,
        "2.13.74".toChainObject(),
        AssetAmount(1500000)
    )
    val props = socket.request(GetDynamicGlobalProps).blockingGet()
    val fees = socket.request(GetRequiredFees(listOf(op))).blockingGet()

    val transaction = Transaction(
        BlockData(props, DCoreSdk.defaultExpiration),
        listOf(op.apply { fee = fees.first() })
    ).withSignature(key)

    val test = socket.request(BroadcastTransactionWithCallback(transaction, 27185))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue { it.id == transaction.id }
  }

  private fun testBuyContent(price: Long, signature: String, transactionId: String, expiration: String) {
    mockWebServer
        .enqueue("""{"method":"call","params":[2,"get_objects",[["2.13.74"]]],"id":0}""", """{"id":0,"result":[{"id":"2.13.74","author":"1.2.17","co_authors":[],"expiration":"2019-05-21T09:37:21","created":"2018-05-21T09:37:20","price":{"map_price":[[1,{"amount":$price,"asset_id":"1.3.0"}]]},"size":1,"synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"New product 2\",\"description\":\"{\\\"productId\\\":1,\\\"applicationId\\\":1}\"}","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F1&version=949da412-18bd-4b8d-acba-e8fd7a594d88","quorum":0,"key_parts":[],"_hash":"0000000000000000000000000000000000000000","last_proof":[],"is_blocked":false,"AVG_rating":0,"num_of_ratings":0,"times_bought":0,"publishing_fee_escrow":{"amount":0,"asset_id":"1.3.0"},"seeder_price":[]}]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":1}""", """{"id":1,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")
        .enqueue("""{"method":"call","params":[2,"get_dynamic_global_properties",[]],"id":3}""", """{"id":3,"result":{"id":"2.1.0","head_block_number":516024,"head_block_id":"0007dfb8478ca274d1c4e21d79e419317662032b","time":"2018-05-30T11:34:40","current_miner":"1.4.7","next_maintenance_time":"2018-05-31T00:00:00","last_budget_time":"2018-05-30T00:00:00","unspent_fee_budget":36688567,"mined_rewards":"306693000000","miner_budget_from_fees":70507687,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":0,"recently_missed_count":0,"current_aslot":5772554,"recent_slots_filled":"340282366920938463463374607431768211455","dynamic_flags":0,"last_irreversible_block_num":516024}}""")
        .enqueue("""{"method":"call","params":[2,"get_required_fees",[[[21,{"URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F1&version=949da412-18bd-4b8d-acba-e8fd7a594d88","consumer":"1.2.34","price":{"amount":$price,"asset_id":"1.3.0"},"pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"region_code_from":1,"fee":{"amount":0,"asset_id":"1.3.0"}}]],"1.3.0"]],"id":4}""", """{"id":4,"result":[{"amount":0,"asset_id":"1.3.0"}]}""")
        .enqueue("""{"method":"call","params":[3,"broadcast_transaction_with_callback",[27185,{"expiration":"$expiration","ref_block_num":57272,"ref_block_prefix":1956809799,"extensions":[],"operations":[[21,{"URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F1&version=949da412-18bd-4b8d-acba-e8fd7a594d88","consumer":"1.2.34","price":{"amount":$price,"asset_id":"1.3.0"},"pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"region_code_from":1,"fee":{"amount":0,"asset_id":"1.3.0"}}]],"signatures":["$signature"]}]],"id":5}""", """{"method":"notice","params":[27185,[{"id":"$transactionId","block_num":516025,"trx_num":1,"trx":{"ref_block_num":57272,"ref_block_prefix":1956809799,"expiration":"$expiration","operations":[[21,{"fee":{"amount":0,"asset_id":"1.3.0"},"URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F1&version=949da412-18bd-4b8d-acba-e8fd7a594d88","consumer":"1.2.34","price":{"amount":$price,"asset_id":"1.3.0"},"region_code_from":1,"pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."}}]],"extensions":[],"signatures":["$signature"],"operation_results":[[0,{}]]}}]]}""")
        .enqueue("""{"method":"call","params":[1,"network_broadcast",[]],"id":6}""", """{"id":6,"result":3}""")


    val key = ECKeyPair.fromBase58(private)
    val content = socket.request(GetContentById("2.13.74".toChainObject())).blockingGet().first()
    val op = BuyContentOperation(
        content.uri,
        account,
        content.price(),
        key.publicElGamal()
    )

    val props = socket.request(GetDynamicGlobalProps).blockingGet()
    val fees = socket.request(GetRequiredFees(listOf(op))).blockingGet()

    val transaction = Transaction(
        BlockData(props, DCoreSdk.defaultExpiration),
        listOf(op.apply { fee = fees.first() })
    ).withSignature(key)

    val test = socket.request(BroadcastTransactionWithCallback(transaction, 27185))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue { it.id == transaction.id }
  }

  @Test fun `buy content`() = testBuyContent(100000000, "204d792108ef89ed570e3fcc57a2da6fa8eab4ece0d27672d0021883add926e24637058d66bb18afcf64786e3238c703d85b5ee6d4e36ffab80f2f618fa46e1d0f", "70d7e960885eaa4dfa885631c3cef6ea5abf5d62", "2018-05-30T11:35:15")

  @Test fun `buy free content`() = testBuyContent(0, "1f51b2c15930ff46cb08f75d24488e2ac0a5c75567962d43b8384af6f03d5bde603c4c36949bd7b9c5ba007e73e95703a27a7d43e6936a8f9f68323b47d64d2ed5", "65650fc8c317e5e9d008fe6f694ad4fc1246c176", "2018-05-30T11:35:16")

  @Test(expected = IllegalArgumentException::class) fun `require non negative price for buy content`() {
    val key = ECKeyPair.fromBase58(private)
    BuyContentOperation(
        "http://alax.io/?scheme=alax%3A%2F%2F1%2F1&version=949da412-18bd-4b8d-acba-e8fd7a594d88",
        account,
        AssetAmount(BigInteger.valueOf(-1), "1.3.0".toChainObject()),
        key.publicElGamal()
    )
  }

  @Test fun `vote for miners`() {
    mockWebServer
        .enqueue("""{"method":"call","params":[2,"get_objects",[["1.2.34"]]],"id":0}""", """{"id":0,"result":[{"id":"1.2.34","registrar":"1.2.15","name":"u961279ec8b7ae7bd62f304f7c1c3d345","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"rights_to_publish":{"is_publishing_manager":false,"publishing_rights_received":[],"publishing_rights_forwarded":[]},"statistics":"2.5.34","top_n_control_flags":0}]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":1}""", """{"id":1,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")
        .enqueue("""{"method":"call","params":[2,"get_dynamic_global_properties",[]],"id":3}""", """{"id":3,"result":{"id":"2.1.0","head_block_number":485151,"head_block_id":"0007671fb754a6b8cf3e5df7c8d8668f0c96cef1","time":"2018-05-28T13:32:00","current_miner":"1.4.9","next_maintenance_time":"2018-05-29T00:00:00","last_budget_time":"2018-05-28T00:00:00","unspent_fee_budget":5530514,"mined_rewards":"328005000000","miner_budget_from_fees":11345954,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":1,"recently_missed_count":3,"current_aslot":5739408,"recent_slots_filled":"319012120304339705501019736411954184189","dynamic_flags":0,"last_irreversible_block_num":485151}}""")
        .enqueue("""{"method":"call","params":[2,"get_required_fees",[[[2,{"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"fee":{"amount":0,"asset_id":"1.3.0"}}]],"1.3.0"]],"id":4}""", """{"id":4,"result":[{"amount":500000,"asset_id":"1.3.0"}]}""")
        .enqueue("""{"method":"call","params":[3,"broadcast_transaction_with_callback",[27185,{"expiration":"2018-05-28T13:32:33","ref_block_num":26399,"ref_block_prefix":3097908407,"extensions":[],"operations":[[2,{"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"fee":{"amount":500000,"asset_id":"1.3.0"}}]],"signatures":["1f32983b7f22a6b4f8abf4c4084eb8d7a602e924fd74636576722b56fec766f7115e85694ba4c48b30ae9ef8499e2ebeb2df9e58945baf70dfb7a6b61f5ef2723f"]}]],"id":5}""", """{"method":"notice","params":[27185,[{"id":"7a36485857261f61eafb70e54ac7994baeba15f2","block_num":485152,"trx_num":0,"trx":{"ref_block_num":26399,"ref_block_prefix":3097908407,"expiration":"2018-05-28T13:32:33","operations":[[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}]],"extensions":[],"signatures":["1f32983b7f22a6b4f8abf4c4084eb8d7a602e924fd74636576722b56fec766f7115e85694ba4c48b30ae9ef8499e2ebeb2df9e58945baf70dfb7a6b61f5ef2723f"],"operation_results":[[0,{}]]}}]]}""")
        .enqueue("""{"method":"call","params":[2,"get_objects",[["1.2.34"]]],"id":6}""", """{"id":6,"result":[{"id":"1.2.34","registrar":"1.2.15","name":"u961279ec8b7ae7bd62f304f7c1c3d345","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"rights_to_publish":{"is_publishing_manager":false,"publishing_rights_received":[],"publishing_rights_forwarded":[]},"statistics":"2.5.34","top_n_control_flags":0}]}""")
        .enqueue("""{"method":"call","params":[2,"get_dynamic_global_properties",[]],"id":7}""", """{"id":7,"result":{"id":"2.1.0","head_block_number":485151,"head_block_id":"0007671fb754a6b8cf3e5df7c8d8668f0c96cef1","time":"2018-05-28T13:32:00","current_miner":"1.4.9","next_maintenance_time":"2018-05-29T00:00:00","last_budget_time":"2018-05-28T00:00:00","unspent_fee_budget":5530514,"mined_rewards":"328005000000","miner_budget_from_fees":11345954,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":1,"recently_missed_count":3,"current_aslot":5739408,"recent_slots_filled":"319012120304339705501019736411954184189","dynamic_flags":0,"last_irreversible_block_num":485151}}""")
        .enqueue("""{"method":"call","params":[2,"get_required_fees",[[[2,{"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"fee":{"amount":0,"asset_id":"1.3.0"}}]],"1.3.0"]],"id":8}""", """{"id":8,"result":[{"amount":500000,"asset_id":"1.3.0"}]}""")
        .enqueue("""{"method":"call","params":[3,"broadcast_transaction_with_callback",[27185,{"expiration":"2018-05-28T13:32:34","ref_block_num":26399,"ref_block_prefix":3097908407,"extensions":[],"operations":[[2,{"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"fee":{"amount":500000,"asset_id":"1.3.0"}}]],"signatures":["2066279380490926435ad860568ba23e0deabc3073c6f24866adf0db2980df10325e10902a23ec69ca66242b229a22b943f001129e5a507b79c54268114912a7fa"]}]],"id":9}""", """{"method":"notice","params":[27185,[{"id":"fbabe85706822779b7808729606aba651ecd6e90","block_num":485153,"trx_num":0,"trx":{"ref_block_num":26399,"ref_block_prefix":3097908407,"expiration":"2018-05-28T13:32:34","operations":[[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}]],"extensions":[],"signatures":["2066279380490926435ad860568ba23e0deabc3073c6f24866adf0db2980df10325e10902a23ec69ca66242b229a22b943f001129e5a507b79c54268114912a7fa"],"operation_results":[[0,{}]]}}]]}""")
        .enqueue("""{"method":"call","params":[1,"network_broadcast",[]],"id":10}""", """{"id":10,"result":3}""")
        .enqueue("""{"method":"call","params":[2,"get_objects",[["1.2.34"]]],"id":11}""", """{"id":11,"result":[{"id":"1.2.34","registrar":"1.2.15","name":"u961279ec8b7ae7bd62f304f7c1c3d345","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"rights_to_publish":{"is_publishing_manager":false,"publishing_rights_received":[],"publishing_rights_forwarded":[]},"statistics":"2.5.34","top_n_control_flags":0}]}""")
        .enqueue("""{"method":"call","params":[2,"get_objects",[["1.2.34"]]],"id":12}""", """{"id":12,"result":[{"id":"1.2.34","registrar":"1.2.15","name":"u961279ec8b7ae7bd62f304f7c1c3d345","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"rights_to_publish":{"is_publishing_manager":false,"publishing_rights_received":[],"publishing_rights_forwarded":[]},"statistics":"2.5.34","top_n_control_flags":0}]}""")

    val votes = setOf("0:5", "0:8")

    val test = vote(votes).concatWith(vote(emptySet()))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValueAt(0, { it.options.votes == votes })
        .assertValueAt(1, { it.options.votes.isEmpty() })
  }

  private fun vote(votes: Set<String>): Single<Account> {
    val key = ECKeyPair.fromBase58(private)
    val account = socket.request(GetAccountById(listOf(account))).blockingGet().first()
    val op = AccountUpdateOperation(
        account.id, options = account.options.copy(votes = votes)
    )
    val props = socket.request(GetDynamicGlobalProps).blockingGet()
    val fees = socket.request(GetRequiredFees(listOf(op))).blockingGet()

    val transaction = Transaction(
        BlockData(props, DCoreSdk.defaultExpiration),
        listOf(op.apply { fee = fees.first() })
    ).withSignature(key)

    return socket.request(BroadcastTransactionWithCallback(transaction, 27185))
        .flatMap { socket.request(GetAccountById(listOf(account.id))).map { it.first() } }
  }

  @Test fun `create account`() {
    mockWebServer
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":1}""", """{"id":1,"result":2}""")
        .enqueue("""{"method":"call","params":[2,"get_dynamic_global_properties",[]],"id":0}""", """{"id":0,"result":{"id":"2.1.0","head_block_number":483808,"head_block_id":"000761e0b1d5fbffd947cb42b397d355e4e25246","time":"2018-05-28T11:29:05","current_miner":"1.4.1","next_maintenance_time":"2018-05-29T00:00:00","last_budget_time":"2018-05-28T00:00:00","unspent_fee_budget":6411522,"mined_rewards":"278314000000","miner_budget_from_fees":11345954,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":0,"recently_missed_count":0,"current_aslot":5737933,"recent_slots_filled":"169974867696766918687181421356601016319","dynamic_flags":0,"last_irreversible_block_num":483808}}""")
        .enqueue("""{"method":"call","params":[2,"get_required_fees",[[[1,{"registrar":"1.2.34","name":"mikeeee","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6718kUCCksnkeYD1YySWkXb1VLpzjkFfHHMirCRPexp5gDPJLU",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6718kUCCksnkeYD1YySWkXb1VLpzjkFfHHMirCRPexp5gDPJLU",1]]},"options":{"memo_key":"DCT6718kUCCksnkeYD1YySWkXb1VLpzjkFfHHMirCRPexp5gDPJLU","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"fee":{"amount":0,"asset_id":"1.3.0"}}]],"1.3.0"]],"id":3}""", """{"id":3,"result":[{"amount":500000,"asset_id":"1.3.0"}]}""")
        .enqueue("""{"method":"call","params":[1,"network_broadcast",[]],"id":5}""", """{"id":5,"result":3}""")
        .enqueue("""{"method":"call","params":[3,"broadcast_transaction_with_callback",[27185,{"expiration":"2018-05-28T11:29:36","ref_block_num":25056,"ref_block_prefix":4294694321,"extensions":[],"operations":[[1,{"registrar":"1.2.34","name":"mikeeee","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6718kUCCksnkeYD1YySWkXb1VLpzjkFfHHMirCRPexp5gDPJLU",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6718kUCCksnkeYD1YySWkXb1VLpzjkFfHHMirCRPexp5gDPJLU",1]]},"options":{"memo_key":"DCT6718kUCCksnkeYD1YySWkXb1VLpzjkFfHHMirCRPexp5gDPJLU","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"fee":{"amount":500000,"asset_id":"1.3.0"}}]],"signatures":["1f246288a9167fa71269bdb2ceaefa02334cecf1363db112b560476aad76ac1d5a66e17ffb6a209261aa6052142373955fb007ed815a46ee3cca1402862a85ae19"]}]],"id":4}""", """{"method":"notice","params":[27185,[{"id":"cc8d5f4c211a0d6d08c78d7c9a762617d064f2c1","block_num":483809,"trx_num":0,"trx":{"ref_block_num":25056,"ref_block_prefix":4294694321,"expiration":"2018-05-28T11:29:36","operations":[[1,{"fee":{"amount":500000,"asset_id":"1.3.0"},"registrar":"1.2.34","name":"mikeeee","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6718kUCCksnkeYD1YySWkXb1VLpzjkFfHHMirCRPexp5gDPJLU",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6718kUCCksnkeYD1YySWkXb1VLpzjkFfHHMirCRPexp5gDPJLU",1]]},"options":{"memo_key":"DCT6718kUCCksnkeYD1YySWkXb1VLpzjkFfHHMirCRPexp5gDPJLU","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}]],"extensions":[],"signatures":["1f246288a9167fa71269bdb2ceaefa02334cecf1363db112b560476aad76ac1d5a66e17ffb6a209261aa6052142373955fb007ed815a46ee3cca1402862a85ae19"],"operation_results":[[1,"1.2.41"]]}}]]}""")


    val key = ECKeyPair.fromBase58(private)
    val public = "DCT6718kUCCksnkeYD1YySWkXb1VLpzjkFfHHMirCRPexp5gDPJLU".address()
    val op = AccountCreateOperation(account, "mikeeee", public)

    val props = socket.request(GetDynamicGlobalProps).blockingGet()
    val fees = socket.request(GetRequiredFees(listOf(op))).blockingGet()

    val transaction = Transaction(
        BlockData(props, DCoreSdk.defaultExpiration),
        listOf(op.apply { fee = fees.first() })
    ).withSignature(key)

    val test = socket.request(BroadcastTransactionWithCallback(transaction, 27185))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }


  @Test fun `content submit`() {

    mockWebServer
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":1}""", """{"id":1,"result":2}""")
        .enqueue("""{"method":"call","params":[2,"get_dynamic_global_properties",[]],"id":0}""", """{"id":0,"result":{"id":"2.1.0","head_block_number":738195,"head_block_id":"000b43939a8dbb62f7a7d95f5d050536a139c485","time":"2018-06-13T08:49:05","current_miner":"1.4.4","next_maintenance_time":"2018-06-14T00:00:00","last_budget_time":"2018-06-13T00:00:00","unspent_fee_budget":3405423,"mined_rewards":"213268000000","miner_budget_from_fees":5105803,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":0,"recently_missed_count":2,"current_aslot":6012445,"recent_slots_filled":"329643350499266886481768439395175624699","dynamic_flags":0,"last_irreversible_block_num":738195}}""")
        .enqueue("""{"method":"call","params":[2,"get_required_fees",[[[20,{"size":10000,"author":"1.2.34","co_authors":[],"URI":"http://hello.io/world2","quorum":0,"price":[{"price":{"amount":1000,"asset_id":"1.3.0"},"region":1}],"hash":"2222222222222222222222222222222222222222","seeders":[],"key_parts":[],"expiration":"2019-05-28T13:32:34","publishing_fee":{"amount":1000,"asset_id":"1.3.0"},"synopsis":"{\"title\":\"Game Title\",\"description\":\"Description\",\"content_type_id\":\"1.2.3\"}","fee":{"amount":0,"asset_id":"1.3.0"}}]],"1.3.0"]],"id":3}""", """{"id":3,"result":[{"amount":0,"asset_id":"1.3.0"}]}""")
        .enqueue("""{"method":"call","params":[1,"network_broadcast",[]],"id":5}""", """{"id":5,"result":3}""")
        .enqueue("""{"method":"call","params":[3,"broadcast_transaction_with_callback",[27185,{"expiration":"2018-06-13T08:49:39","ref_block_num":17299,"ref_block_prefix":1656458650,"extensions":[],"operations":[[20,{"size":10000,"author":"1.2.34","co_authors":[],"URI":"http://hello.io/world2","quorum":0,"price":[{"price":{"amount":1000,"asset_id":"1.3.0"},"region":1}],"hash":"2222222222222222222222222222222222222222","seeders":[],"key_parts":[],"expiration":"2019-05-28T13:32:34","publishing_fee":{"amount":1000,"asset_id":"1.3.0"},"synopsis":"{\"title\":\"Game Title\",\"description\":\"Description\",\"content_type_id\":\"1.2.3\"}","fee":{"amount":0,"asset_id":"1.3.0"}}]],"signatures":["1f2701505f266fac3fc4c6c6d8021a841ccbba281b191dfea940c5f54851333f3e2d17999f172a1ffa4ee2f0f36ff1b9f0bf68e81317191df46b30b8116ce899b8"]}]],"id":4}""", """{"method":"notice","params":[27185,[{"id":"d2bdda78d8ee2ebce0819ff9ff7a30858f27d49e","block_num":738196,"trx_num":1,"trx":{"ref_block_num":17299,"ref_block_prefix":1656458650,"expiration":"2018-06-13T08:49:39","operations":[[20,{"fee":{"amount":0,"asset_id":"1.3.0"},"size":10000,"author":"1.2.34","co_authors":[],"URI":"http://hello.io/world2","quorum":0,"price":[{"region":1,"price":{"amount":1000,"asset_id":"1.3.0"}}],"hash":"2222222222222222222222222222222222222222","seeders":[],"key_parts":[],"expiration":"2019-05-28T13:32:34","publishing_fee":{"amount":1000,"asset_id":"1.3.0"},"synopsis":"{\"title\":\"Game Title\",\"description\":\"Description\",\"content_type_id\":\"1.2.3\"}"}]],"extensions":[],"signatures":["1f2701505f266fac3fc4c6c6d8021a841ccbba281b191dfea940c5f54851333f3e2d17999f172a1ffa4ee2f0f36ff1b9f0bf68e81317191df46b30b8116ce899b8"],"operation_results":[[0,{}]]}}]]}""")

    val key = ECKeyPair.fromBase58(private)

    val op = ContentSubmitOperation(
        10000,
        account,
        listOf(),
        "http://hello.io/world2",
        0,
        listOf(RegionalPrice(AssetAmount(1000), 1)),
        "2222222222222222222222222222222222222222",
        listOf(),
        listOf(),
        LocalDateTime.parse("2019-05-28T13:32:34"),
        AssetAmount(1000),
        Synopsis("Game Title", "Description", "1.2.3".toChainObject()).json
    )

    val props = socket.request(GetDynamicGlobalProps).blockingGet()
    val fees = socket.request(GetRequiredFees(listOf(op))).blockingGet()

    val transaction = Transaction(
        BlockData(props, DCoreSdk.defaultExpiration),
        listOf(op.apply { fee = fees.first() })
    ).withSignature(key)

    val test = socket.request(BroadcastTransactionWithCallback(transaction, 27185))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
*/
}