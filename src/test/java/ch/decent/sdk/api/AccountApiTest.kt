package ch.decent.sdk.api

import ch.decent.sdk.account
import ch.decent.sdk.account2
import ch.decent.sdk.accountName
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.public
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class AccountApiTest(channel: Channel) : BaseApiTest(channel) {
//  override val useMock: Boolean = false

  @Test fun `get account by id`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"get_objects",[["1.2.35"]]],"id":1}""", """{"id":1,"result":[{"id":"1.2.35","registrar":"1.2.15","name":"u3a7b78084e7d3956442d5a4d439dad51","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP",1]]},"options":{"memo_key":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"rights_to_publish":{"is_publishing_manager":false,"publishing_rights_received":[],"publishing_rights_forwarded":[]},"statistics":"2.5.35","top_n_control_flags":0}]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    mockHttp.enqueue("""{"id":0,"result":[{"id":"1.2.35","registrar":"1.2.15","name":"u3a7b78084e7d3956442d5a4d439dad51","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP",1]]},"options":{"memo_key":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"rights_to_publish":{"is_publishing_manager":false,"publishing_rights_received":[],"publishing_rights_forwarded":[]},"statistics":"2.5.35","top_n_control_flags":0}]}""")

    val test = api.account.getAccount(account2.objectId)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get account by id not found`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"get_objects",[["1.2.34000"]]],"id":1}""", """{"id":1,"result":[null]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    mockHttp.enqueue("""{"id":0,"result":[null]}""")

    val test = api.account.getAccount("1.2.34000")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(ObjectNotFoundException::class.java)
  }

  @Test fun `get account by address`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"get_key_references",[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz"]]],"id":1}""", """{"id":1,"result":[["1.2.34","1.2.775"]]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")
        .enqueue("""{"method":"call","params":[2,"get_objects",[["1.2.34"]]],"id":4}""", """{"id":4,"result":[{"id":"1.2.34","registrar":"1.2.15","name":"u961279ec8b7ae7bd62f304f7c1c3d345","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"rights_to_publish":{"is_publishing_manager":false,"publishing_rights_received":[],"publishing_rights_forwarded":[]},"statistics":"2.5.34","top_n_control_flags":0}]}""")

    mockHttp.enqueue("""{"id":0,"result":[["1.2.34","1.2.775"]]}""")
    mockHttp.enqueue("""{"id":0,"result":[{"id":"1.2.34","registrar":"1.2.15","name":"u961279ec8b7ae7bd62f304f7c1c3d345","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"rights_to_publish":{"is_publishing_manager":false,"publishing_rights_received":[],"publishing_rights_forwarded":[]},"statistics":"2.5.34","top_n_control_flags":0}]}""")

    val test = api.account.getAccount(public)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get account by address not found`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"get_key_references",[["DCT5Abm5dCdy3hJ1C5ckXkqUH2Me7dXqi9Y7yjn9ACaiSJ9h8r8mL"]]],"id":1}""", """{"id":1,"result":[[]]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    mockHttp.enqueue("""{"id":0,"result":[[]]}""")

    val test = api.account.getAccount("DCT5Abm5dCdy3hJ1C5ckXkqUH2Me7dXqi9Y7yjn9ACaiSJ9h8r8mL")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(ObjectNotFoundException::class.java)
  }

  @Test fun `get account by name`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"get_account_by_name",["u961279ec8b7ae7bd62f304f7c1c3d345"]],"id":1}""", """{"id":1,"result":{"id":"1.2.34","registrar":"1.2.15","name":"u961279ec8b7ae7bd62f304f7c1c3d345","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"rights_to_publish":{"is_publishing_manager":false,"publishing_rights_received":[],"publishing_rights_forwarded":[]},"statistics":"2.5.34","top_n_control_flags":0}}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    mockHttp.enqueue("""{"id":0,"result":{"id":"1.2.35","registrar":"1.2.15","name":"u3a7b78084e7d3956442d5a4d439dad51","owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP",1]]},"active":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP",1]]},"options":{"memo_key":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"rights_to_publish":{"is_publishing_manager":false,"publishing_rights_received":[],"publishing_rights_forwarded":[]},"statistics":"2.5.35","top_n_control_flags":0}}""")

    val test = api.account.getAccount(accountName)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get account by name not found`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"get_account_by_name",["helloooo"]],"id":1}""", """{"id":1,"result":null}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    mockHttp.enqueue("""{"id":0,"result":null}""")

    val test = api.account.getAccount("helloooo")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(ObjectNotFoundException::class.java)
  }

  @Test fun `search account history`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"search_account_history",["1.2.34","-time","1.0.0",100]],"id":1}""", """{"id":1,"result":[{"id":"2.17.263","m_from_account":"1.2.34","m_to_account":"1.2.35","m_operation_type":0,"m_transaction_amount":{"amount":150000000,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"transfer","m_transaction_encrypted_memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"580996120356000000","message":"d9134fd4a08699851e137aab2fc256dea9a6f5b525aad31a227702013a81ed4d"},"m_timestamp":"2018-05-24T12:26:00"},{"id":"2.17.231","m_from_account":"1.2.34","m_to_account":"1.2.38","m_operation_type":1,"m_transaction_amount":{"amount":0,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"","m_timestamp":"2018-05-21T16:03:40"},{"id":"2.17.230","m_from_account":"1.2.34","m_to_account":"1.2.35","m_operation_type":0,"m_transaction_amount":{"amount":150000000,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"transfer","m_transaction_encrypted_memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"16653614573311238400","message":"5e1918bf52d9805d3463392ca833e63ed7b4c1f24bed949f9e2851e66b2d02b8"},"m_timestamp":"2018-05-21T15:54:25"},{"id":"2.17.229","m_from_account":"1.2.34","m_to_account":"1.2.35","m_operation_type":0,"m_transaction_amount":{"amount":150000000,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"transfer","m_transaction_encrypted_memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"16653614573311238400","message":"5e1918bf52d9805d3463392ca833e63ed7b4c1f24bed949f9e2851e66b2d02b8"},"m_timestamp":"2018-05-21T15:54:25"},{"id":"2.17.157","m_from_account":"1.2.17","m_to_account":"1.2.34","m_operation_type":3,"m_transaction_amount":{"amount":100000000,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":0,"asset_id":"1.3.0"},"m_str_description":"Product 2","m_timestamp":"2018-05-15T08:59:10"},{"id":"2.17.155","m_from_account":"1.2.15","m_to_account":"1.2.34","m_operation_type":0,"m_transaction_amount":{"amount":"10000000000","asset_id":"1.3.0"},"m_transaction_fee":{"amount":100000,"asset_id":"1.3.0"},"m_str_description":"transfer","m_timestamp":"2018-05-15T08:58:55"},{"id":"2.17.153","m_from_account":"1.2.15","m_to_account":"1.2.34","m_operation_type":1,"m_transaction_amount":{"amount":0,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"","m_timestamp":"2018-05-15T08:50:45"}]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    mockHttp.enqueue("""{"id":0,"result":[{"id":"2.17.263","m_from_account":"1.2.34","m_to_account":"1.2.35","m_operation_type":0,"m_transaction_amount":{"amount":150000000,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"transfer","m_transaction_encrypted_memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"580996120356000000","message":"d9134fd4a08699851e137aab2fc256dea9a6f5b525aad31a227702013a81ed4d"},"m_timestamp":"2018-05-24T12:26:00"},{"id":"2.17.231","m_from_account":"1.2.34","m_to_account":"1.2.38","m_operation_type":1,"m_transaction_amount":{"amount":0,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"","m_timestamp":"2018-05-21T16:03:40"},{"id":"2.17.230","m_from_account":"1.2.34","m_to_account":"1.2.35","m_operation_type":0,"m_transaction_amount":{"amount":150000000,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"transfer","m_transaction_encrypted_memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"16653614573311238400","message":"5e1918bf52d9805d3463392ca833e63ed7b4c1f24bed949f9e2851e66b2d02b8"},"m_timestamp":"2018-05-21T15:54:25"},{"id":"2.17.229","m_from_account":"1.2.34","m_to_account":"1.2.35","m_operation_type":0,"m_transaction_amount":{"amount":150000000,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"transfer","m_transaction_encrypted_memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"16653614573311238400","message":"5e1918bf52d9805d3463392ca833e63ed7b4c1f24bed949f9e2851e66b2d02b8"},"m_timestamp":"2018-05-21T15:54:25"},{"id":"2.17.157","m_from_account":"1.2.17","m_to_account":"1.2.34","m_operation_type":3,"m_transaction_amount":{"amount":100000000,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":0,"asset_id":"1.3.0"},"m_str_description":"Product 2","m_timestamp":"2018-05-15T08:59:10"},{"id":"2.17.155","m_from_account":"1.2.15","m_to_account":"1.2.34","m_operation_type":0,"m_transaction_amount":{"amount":"10000000000","asset_id":"1.3.0"},"m_transaction_fee":{"amount":100000,"asset_id":"1.3.0"},"m_str_description":"transfer","m_timestamp":"2018-05-15T08:58:55"},{"id":"2.17.153","m_from_account":"1.2.15","m_to_account":"1.2.34","m_operation_type":1,"m_transaction_amount":{"amount":0,"asset_id":"1.3.0"},"m_transaction_fee":{"amount":500000,"asset_id":"1.3.0"},"m_str_description":"","m_timestamp":"2018-05-15T08:50:45"}]}""")

    val test = api.account.searchAccountHistory(account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue { it.isNotEmpty() }
  }

  @Test fun `search account history not found`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"search_account_history",["1.2.333333","-time","1.0.0",100]],"id":1}""", """{"id":1,"result":[]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    mockHttp.enqueue("""{"id":0,"result":[]}""")

    val test = api.account.searchAccountHistory("1.2.333333".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue(emptyList())
  }
}