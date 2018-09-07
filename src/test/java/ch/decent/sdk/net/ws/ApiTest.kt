package ch.decent.sdk.net.ws

import ch.decent.sdk.*
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.DumpedPrivateKey
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.address
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
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
    socket = RxWebSocket(
        client,
        mockWebServer.getUrl(),
//        url,
        logger = LoggerFactory.getLogger("RxWebSocket"),
        gson = DCoreSdk.gsonBuilder.create()
    )
  }

  @After fun finish() {
    mockWebServer.shutdown()
  }

  @Test fun loginTest() {
    mockWebServer
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":0}""", """{"id":0,"result":true}""")

    val test = socket.request(Login)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue(true)
  }

  @Test fun `should login, request db access and get balance for account`() {
    mockWebServer
        .enqueue("""{"method":"call","params":[2,"get_account_balances",["1.2.34",[]]],"id":0}""", """{"id":0,"result":[{"amount":50500000,"asset_id":"1.3.0"}]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":1}""", """{"id":1,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")

    val test = socket.request(GetAccountBalances(account))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should login, request db access and get account`() {
    mockWebServer
        .enqueue("""{"method":"call","params":[2,"get_objects",[["1.2.35"]]],"id":0}""", """{"id":0,"result":[{"id":"1.2.35","registrar":"1.2.15","name":"u3a7b78084e7d3956442d5a4d439dad51","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP",1]]},"options":{"memo_key":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"rights_to_publish":{"is_publishing_manager":false,"publishing_rights_received":[],"publishing_rights_forwarded":[]},"statistics":"2.5.35","top_n_control_flags":0}]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":1}""", """{"id":1,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")

    val test = socket.request(GetAccountById(account2))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should login, request db access and get account by name`() {
    mockWebServer
        .enqueue("""{"method":"call","params":[2,"get_account_by_name",["u961279ec8b7ae7bd62f304f7c1c3d345"]],"id":0}""", """{"id":0,"result":{"id":"1.2.34","registrar":"1.2.15","name":"u961279ec8b7ae7bd62f304f7c1c3d345","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"rights_to_publish":{"is_publishing_manager":false,"publishing_rights_received":[],"publishing_rights_forwarded":[]},"statistics":"2.5.34","top_n_control_flags":0}}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":1}""", """{"id":1,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")

    val test = socket.request(GetAccountByName(accountName))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should login, request db access and get account history`() {
    mockWebServer
        .enqueue("""{"method":"call","params":[2,"search_account_history",["1.2.34","-time","0.0.0",100]],"id":0}""", """{"id":0,"result":[{"id":"2.17.263","m_from_account":"1.2.34","m_to_account":"1.2.35","m_operation_type":0,"m_transaction_amount":{"amount":150000000,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"transfer","m_transaction_encrypted_memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"580996120356000000","message":"d9134fd4a08699851e137aab2fc256dea9a6f5b525aad31a227702013a81ed4d"},"m_timestamp":"2018-05-24T12:26:00"},{"id":"2.17.231","m_from_account":"1.2.34","m_to_account":"1.2.38","m_operation_type":1,"m_transaction_amount":{"amount":0,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"","m_timestamp":"2018-05-21T16:03:40"},{"id":"2.17.230","m_from_account":"1.2.34","m_to_account":"1.2.35","m_operation_type":0,"m_transaction_amount":{"amount":150000000,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"transfer","m_transaction_encrypted_memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"16653614573311238400","message":"5e1918bf52d9805d3463392ca833e63ed7b4c1f24bed949f9e2851e66b2d02b8"},"m_timestamp":"2018-05-21T15:54:25"},{"id":"2.17.229","m_from_account":"1.2.34","m_to_account":"1.2.35","m_operation_type":0,"m_transaction_amount":{"amount":150000000,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"transfer","m_transaction_encrypted_memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"16653614573311238400","message":"5e1918bf52d9805d3463392ca833e63ed7b4c1f24bed949f9e2851e66b2d02b8"},"m_timestamp":"2018-05-21T15:54:25"},{"id":"2.17.157","m_from_account":"1.2.17","m_to_account":"1.2.34","m_operation_type":3,"m_transaction_amount":{"amount":100000000,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":0,"asset_id":"1.3.0"},"m_str_description":"Product 2","m_timestamp":"2018-05-15T08:59:10"},{"id":"2.17.155","m_from_account":"1.2.15","m_to_account":"1.2.34","m_operation_type":0,"m_transaction_amount":{"amount":"10000000000","asset_id":"1.3.0"},"m_transaction_fee":{"amount":100000,"asset_id":"1.3.0"},"m_str_description":"transfer","m_timestamp":"2018-05-15T08:58:55"},{"id":"2.17.153","m_from_account":"1.2.15","m_to_account":"1.2.34","m_operation_type":1,"m_transaction_amount":{"amount":0,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"","m_timestamp":"2018-05-15T08:50:45"}]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":1}""", """{"id":1,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")

    val test = socket.request(SearchAccountHistory(account))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get miners, load their accounts and put it into map with miner names`() {
    mockWebServer
        .enqueue("""{"method":"call","params":[2,"lookup_miner_accounts",["",100]],"id":0}""", """{"id":0,"result":[["init0","1.4.1"],["init1","1.4.2"],["init10","1.4.11"],["init2","1.4.3"],["init3","1.4.4"],["init4","1.4.5"],["init5","1.4.6"],["init6","1.4.7"],["init7","1.4.8"],["init8","1.4.9"],["init9","1.4.10"],["u46f36fcd24d74ae58c9b0e49a1f0103c","1.4.12"]]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":1}""", """{"id":1,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")
        .enqueue("""{"method":"call","params":[2,"get_objects",[["1.4.1","1.4.2","1.4.11","1.4.3","1.4.4","1.4.5","1.4.6","1.4.7","1.4.8","1.4.9","1.4.10","1.4.12"]]],"id":3}""", """{"id":3,"result":[{"id":"1.4.1","miner_account":"1.2.4","last_aslot":5739518,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.6","vote_id":"0:0","total_votes":"1883948815097","url":"","total_missed":477296,"last_confirmed_block_num":485250},{"id":"1.4.2","miner_account":"1.2.5","last_aslot":5739523,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.4","vote_id":"0:1","total_votes":"891228003003","url":"","total_missed":477300,"last_confirmed_block_num":485255},{"id":"1.4.11","miner_account":"1.2.14","last_aslot":5739515,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.9","vote_id":"0:10","total_votes":"891227003003","url":"","total_missed":475818,"last_confirmed_block_num":485248},{"id":"1.4.3","miner_account":"1.2.6","last_aslot":5739522,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.2","vote_id":"0:2","total_votes":248000000,"url":"","total_missed":472864,"last_confirmed_block_num":485254},{"id":"1.4.4","miner_account":"1.2.7","last_aslot":5739513,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.7","vote_id":"0:3","total_votes":"1883458812094","url":"","total_missed":477283,"last_confirmed_block_num":485246},{"id":"1.4.5","miner_account":"1.2.8","last_aslot":5739524,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.0","vote_id":"0:4","total_votes":248000000,"url":"","total_missed":477302,"last_confirmed_block_num":485256},{"id":"1.4.6","miner_account":"1.2.9","last_aslot":5739520,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.3","vote_id":"0:5","total_votes":"890986000000","url":"","total_missed":477298,"last_confirmed_block_num":485252},{"id":"1.4.7","miner_account":"1.2.10","last_aslot":5739519,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.10","vote_id":"0:6","total_votes":"992961815097","url":"","total_missed":477300,"last_confirmed_block_num":485251},{"id":"1.4.8","miner_account":"1.2.11","last_aslot":5739521,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.5","vote_id":"0:7","total_votes":"992720812094","url":"","total_missed":477299,"last_confirmed_block_num":485253},{"id":"1.4.9","miner_account":"1.2.12","last_aslot":5739516,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.8","vote_id":"0:8","total_votes":"890986000000","url":"","total_missed":477299,"last_confirmed_block_num":485249},{"id":"1.4.10","miner_account":"1.2.13","last_aslot":5695113,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.1","vote_id":"0:9","total_votes":0,"url":"","total_missed":5935,"last_confirmed_block_num":444827},{"id":"1.4.12","miner_account":"1.2.27","last_aslot":0,"signing_key":"DCT8cYDtKZvcAyWfFRusy6ja1Hafe9Ys4UPJS92ajTmcrufHnGgjp","vote_id":"0:11","total_votes":"992472812094","url":"","total_missed":3982,"last_confirmed_block_num":0}]}""")

    val test = socket.request(LookupMinerAccounts())
        .flatMap { result ->
          socket.request(GetMiners(result.map { ChainObject.parse(it[1].asString) }.toSet()))
              .map { result.map { it.get(0).asString!! }.zip(it).toMap() }
        }
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should login, request history access and get account history`() {
    mockWebServer
        .enqueue("""{"method":"call","params":[2,"get_account_history",["1.2.34","1.7.0",100,"1.7.0"]],"id":0}""", """{"id":0,"result":[{"id":"1.7.557","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":480676,"trx_in_block":0,"op_in_trx":0,"virtual_op":1231},{"id":"1.7.556","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":480675,"trx_in_block":0,"op_in_trx":0,"virtual_op":1228},{"id":"1.7.456","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":419229,"trx_in_block":0,"op_in_trx":0,"virtual_op":902},{"id":"1.7.455","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":419228,"trx_in_block":0,"op_in_trx":0,"virtual_op":899},{"id":"1.7.368","op":[0,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":150000000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"9637208953715299072","message":"b7da6b1f94635777b2649309af699cee7ece00f31f0a652eb786a03b32ff2714"},"extensions":[]}],"result":[0,{}],"block_num":385238,"trx_in_block":0,"op_in_trx":0,"virtual_op":632},{"id":"1.7.367","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":385122,"trx_in_block":0,"op_in_trx":0,"virtual_op":629}]}""")
        .enqueue("""{"method":"call","params":[1,"history",[]],"id":1}""", """{"id":1,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")

    val test = socket.request(GetAccountHistory(account))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get assets`() {
    mockWebServer
        .enqueue("""{"method":"call","params":[2,"get_assets",[["1.3.0"]]],"id":0}""", """{"id":0,"result":[{"id":"1.3.0","symbol":"DCT","precision":8,"issuer":"1.2.1","description":"","options":{"max_supply":"7319777577456900","core_exchange_rate":{"base":{"amount":1,"asset_id":"1.3.0"},"quote":{"amount":1,"asset_id":"1.3.0"}},"is_exchangeable":true,"extensions":[]},"dynamic_asset_data_id":"2.3.0"}]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":1}""", """{"id":1,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")

    val test = socket.request(GetAssets(listOf("1.3.0".toChainObject())))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `transaction test`() {
    val dpk = DumpedPrivateKey.fromBase58(private)
    val key = ECKeyPair.fromPrivate(dpk.bytes, dpk.compressed)
    val memo = Memo("hello memo here i am", key, Address.decode(public2), BigInteger("735604672334802432"))
    mockWebServer
        .enqueue("""{"method":"call","params":[2,"get_dynamic_global_properties",[]],"id":0}""", """{"id":0,"result":{"id":"2.1.0","head_block_number":599091,"head_block_id":"00092433e84dedb18c9b9a378cfea8cdfbb2b637","time":"2018-06-04T12:25:00","current_miner":"1.4.8","next_maintenance_time":"2018-06-05T00:00:00","last_budget_time":"2018-06-04T00:00:00","unspent_fee_budget":96490,"mined_rewards":"301032000000","miner_budget_from_fees":169714,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":1,"recently_missed_count":0,"current_aslot":5859543,"recent_slots_filled":"329648380685469039951165571643239038463","dynamic_flags":0,"last_irreversible_block_num":599091}}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":1}""", """{"id":1,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":2}""", """{"id":2,"result":true}""")
        .enqueue("""{"method":"call","params":[2,"get_required_fees",[[[39,{"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"${memo.message}","nonce":${memo.nonce}},"fee":{"amount":0,"asset_id":"1.3.0"}}]],"1.3.0"]],"id":3}""", """{"id":3,"result":[{"amount":500000,"asset_id":"1.3.0"}]}""")
        .enqueue("""{"method":"call","params":[3,"broadcast_transaction_with_callback",[27185,{"expiration":"2018-06-04T12:25:32","ref_block_num":9267,"ref_block_prefix":2985119208,"extensions":[],"operations":[[39,{"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"${memo.message}","nonce":${memo.nonce}},"fee":{"amount":500000,"asset_id":"1.3.0"}}]],"signatures":["203c168ef8b88e5702cedd7ee2985a67a63fb15a58023323828c0b843c37eb4a6d1b45665414488d83262f7116ac6a0116943d512352c8e858fe636b3bec195265"]}]],"id":4}""", """{"method":"notice","params":[27185,[{"id":"2fd68fb4e7ec4b30b465263ed10177fe8938a8a9","block_num":599092,"trx_num":0,"trx":{"ref_block_num":9267,"ref_block_prefix":2985119208,"expiration":"2018-06-04T12:25:32","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["203c168ef8b88e5702cedd7ee2985a67a63fb15a58023323828c0b843c37eb4a6d1b45665414488d83262f7116ac6a0116943d512352c8e858fe636b3bec195265"],"operation_results":[[0,{}]]}}]]}""")
        .enqueue("""{"method":"call","params":[1,"network_broadcast",[]],"id":5}""", """{"id":5,"result":3}""")


    val op = TransferOperation(
        account,
        account2,
        AssetAmount(1500000),
        memo
    )
    val props = socket.request(GetDynamicGlobalProps).blockingGet()
    val fees = socket.request(GetRequiredFees(listOf(op))).blockingGet()

    val transaction = Transaction(
        BlockData(props),
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
        BlockData(props),
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
        BlockData(props),
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

  @Test(expected = IllegalArgumentException::class) fun `require nonnegative price for buy content`() {
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
    val account = socket.request(GetAccountById(account)).blockingGet().first()
    val op = AccountUpdateOperation(
        account.id, options = account.options.copy(votes = votes)
    )
    val props = socket.request(GetDynamicGlobalProps).blockingGet()
    val fees = socket.request(GetRequiredFees(listOf(op))).blockingGet()

    val transaction = Transaction(
        BlockData(props),
        listOf(op.apply { fee = fees.first() })
    ).withSignature(key)

    return socket.request(BroadcastTransactionWithCallback(transaction, 27185))
        .flatMap { socket.request(GetAccountById(account.id)).map { it.first() } }
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
        BlockData(props),
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
        BlockData(props),
        listOf(op.apply { fee = fees.first() })
    ).withSignature(key)

    val test = socket.request(BroadcastTransactionWithCallback(transaction, 27185))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}