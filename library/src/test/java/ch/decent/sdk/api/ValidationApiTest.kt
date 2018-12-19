package ch.decent.sdk.api

import ch.decent.sdk.accountName2
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.address
import ch.decent.sdk.exception.DCoreException
import ch.decent.sdk.model.BlockData
import ch.decent.sdk.model.OperationType
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.private
import ch.decent.sdk.public
import ch.decent.sdk.public2
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.threeten.bp.ZoneOffset

class ValidationApiTest(channel: Channel) : BaseApiTest(channel) {
//  override val useMock: Boolean = false

  private val signature = "1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"
  private val signatureInvalid = "1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089"
  private val trx: Transaction
    get() = api.transactionApi.getTransaction(1370282, 0).blockingGet().let {
      Transaction(BlockData(it.refBlockNum, it.refBlockPrefix, it.expiration.toEpochSecond(ZoneOffset.UTC)), it.operations, "")
    }

  @Test fun `should get required signatures`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_transaction",[1370282,0]],"id":1}""",
            """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_required_signatures",[{"expiration":"2018-07-26T11:27:07","ref_block_num":59561,"ref_block_prefix":2414941591,"extensions":[],"operations":[[39,{"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7","nonce":735604672334802432},"fee":{"amount":500000,"asset_id":"1.3.0"}}]]},["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP"]]],"id":2}""",
            """{"id":2,"result":["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz"]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
    )
    mockHttp.enqueue(
        """{"id":1,"result":["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz"]}"""
    )

    val test = api.validationApi.getRequiredSignatures(trx, listOf(public.address(), public2.address()))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue(listOf(public.address()))
  }

  @Test fun `should get potential signatures`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_transaction",[1370282,0]],"id":1}""",
            """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_potential_signatures",[{"expiration":"2018-07-26T11:27:07","ref_block_num":59561,"ref_block_prefix":2414941591,"extensions":[],"operations":[[39,{"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7","nonce":735604672334802432},"fee":{"amount":500000,"asset_id":"1.3.0"}}]]}]],"id":2}""",
            """{"id":2,"result":["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz"]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
    )
    mockHttp.enqueue(
        """{"id":1,"result":["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz"]}"""
    )

    val test = api.validationApi.getPotentialSignatures(trx)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue(listOf(public.address()))
  }

  @Test fun `should verify signed transaction`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_transaction",[1370282,0]],"id":1}""",
            """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"verify_authority",[{"expiration":"2018-07-26T11:27:07","ref_block_num":59561,"ref_block_prefix":2414941591,"extensions":[],"operations":[[39,{"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7","nonce":735604672334802432},"fee":{"amount":500000,"asset_id":"1.3.0"}}]],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"]}]],"id":2}""",
            """{"id":2,"result":true}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
    )
    mockHttp.enqueue(
        """{"id":1,"result":true}"""
    )

    val test = api.validationApi.verifyAuthority(trx.copy(signatures = listOf(signature)))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue(true)
  }

  @Test fun `should fail to verify signed transaction`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_transaction",[1370282,0]],"id":1}""",
            """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"verify_authority",[{"expiration":"2018-07-26T11:27:07","ref_block_num":59561,"ref_block_prefix":2414941591,"extensions":[],"operations":[[39,{"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7","nonce":735604672334802432},"fee":{"amount":500000,"asset_id":"1.3.0"}}]],"signatures":["1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089"]}]],"id":2}""",
            """{"id":2,"error":{"code":1,"message":"3030001 tx_missing_active_auth: missing required active authority\nMissing Active Authority 1.2.34\n    {\"id\":\"1.2.34\",\"auth\":{\"weight_threshold\":1,\"account_auths\":[],\"key_auths\":[[\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",1]]},\"owner\":{\"weight_threshold\":1,\"account_auths\":[],\"key_auths\":[[\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",1]]}}\n    th_a  transaction.cpp:365 verify_authority1\n\n    {\"ops\":[[39,{\"fee\":{\"amount\":500000,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.34\",\"to\":\"1.2.35\",\"amount\":{\"amount\":1500000,\"asset_id\":\"1.3.0\"},\"memo\":{\"from\":\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",\"to\":\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",\"nonce\":\"735604672334802432\",\"message\":\"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7\"},\"extensions\":[]}]],\"sigs\":\"DCT5w23SR1igZtWDcyEFfQ6A2XtEdKrFPLcqMJiu2b3EsS4189nh8\"}\n    th_a  transaction.cpp:374 verify_authority1\n\n    {\"*this\":{\"ref_block_num\":59561,\"ref_block_prefix\":2414941591,\"expiration\":\"2018-07-26T11:27:07\",\"operations\":[[39,{\"fee\":{\"amount\":500000,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.34\",\"to\":\"1.2.35\",\"amount\":{\"amount\":1500000,\"asset_id\":\"1.3.0\"},\"memo\":{\"from\":\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",\"to\":\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",\"nonce\":\"735604672334802432\",\"message\":\"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7\"},\"extensions\":[]}]],\"extensions\":[],\"signatures\":[\"1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089\"]}}\n    th_a  transaction.cpp:465 verify_authority","data":{"code":3030001,"name":"tx_missing_active_auth","message":"missing required active authority","stack":[{"context":{"level":"error","file":"transaction.cpp","line":365,"method":"verify_authority1","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:24:00"},"format":"Missing Active Authority {id}","data":{"id":"1.2.34","auth":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]}}},{"context":{"level":"warn","file":"transaction.cpp","line":374,"method":"verify_authority1","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:24:00"},"format":"","data":{"ops":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"sigs":"DCT5w23SR1igZtWDcyEFfQ6A2XtEdKrFPLcqMJiu2b3EsS4189nh8"}},{"context":{"level":"warn","file":"transaction.cpp","line":465,"method":"verify_authority","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:24:00"},"format":"","data":{"*this":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089"]}}}]}}}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
    )
    mockHttp.enqueue(
        """{"id":1,"error":{"code":1,"message":"3030001 tx_missing_active_auth: missing required active authority\nMissing Active Authority 1.2.34\n    {\"id\":\"1.2.34\",\"auth\":{\"weight_threshold\":1,\"account_auths\":[],\"key_auths\":[[\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",1]]},\"owner\":{\"weight_threshold\":1,\"account_auths\":[],\"key_auths\":[[\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",1]]}}\n    th_a  transaction.cpp:365 verify_authority1\n\n    {\"ops\":[[39,{\"fee\":{\"amount\":500000,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.34\",\"to\":\"1.2.35\",\"amount\":{\"amount\":1500000,\"asset_id\":\"1.3.0\"},\"memo\":{\"from\":\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",\"to\":\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",\"nonce\":\"735604672334802432\",\"message\":\"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7\"},\"extensions\":[]}]],\"sigs\":\"DCT5w23SR1igZtWDcyEFfQ6A2XtEdKrFPLcqMJiu2b3EsS4189nh8\"}\n    th_a  transaction.cpp:374 verify_authority1\n\n    {\"*this\":{\"ref_block_num\":59561,\"ref_block_prefix\":2414941591,\"expiration\":\"2018-07-26T11:27:07\",\"operations\":[[39,{\"fee\":{\"amount\":500000,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.34\",\"to\":\"1.2.35\",\"amount\":{\"amount\":1500000,\"asset_id\":\"1.3.0\"},\"memo\":{\"from\":\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",\"to\":\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",\"nonce\":\"735604672334802432\",\"message\":\"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7\"},\"extensions\":[]}]],\"extensions\":[],\"signatures\":[\"1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089\"]}}\n    th_a  transaction.cpp:465 verify_authority","data":{"code":3030001,"name":"tx_missing_active_auth","message":"missing required active authority","stack":[{"context":{"level":"error","file":"transaction.cpp","line":365,"method":"verify_authority1","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:24:00"},"format":"Missing Active Authority {id}","data":{"id":"1.2.34","auth":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]}}},{"context":{"level":"warn","file":"transaction.cpp","line":374,"method":"verify_authority1","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:24:00"},"format":"","data":{"ops":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"sigs":"DCT5w23SR1igZtWDcyEFfQ6A2XtEdKrFPLcqMJiu2b3EsS4189nh8"}},{"context":{"level":"warn","file":"transaction.cpp","line":465,"method":"verify_authority","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:24:00"},"format":"","data":{"*this":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089"]}}}]}}}"""
    )

    val test = api.validationApi.verifyAuthority(trx.copy(signatures = listOf(signatureInvalid)))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertValue(false)
  }

  // bug in dcore, fails
  @Test fun `should verify signers for account`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"verify_account_authority",["u3a7b78084e7d3956442d5a4d439dad51",["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP"]]],"id":1}""",
            """{"id":1,"error":{"code":1,"message":"3030001 tx_missing_active_auth: missing required active authority\nMissing Active Authority 1.2.35\n    {\"id\":\"1.2.35\",\"auth\":{\"weight_threshold\":1,\"account_auths\":[],\"key_auths\":[[\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",1]]},\"owner\":{\"weight_threshold\":1,\"account_auths\":[],\"key_auths\":[[\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",1]]}}\n    th_a  transaction.cpp:322 verify_authority\n\n    {\"ops\":[[0,{\"fee\":{\"amount\":0,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.35\",\"to\":\"1.2.0\",\"amount\":{\"amount\":0,\"asset_id\":\"1.3.0\"},\"extensions\":[]}]],\"sigs\":[]}\n    th_a  transaction.cpp:337 verify_authority\n\n    {\"*this\":{\"ref_block_num\":0,\"ref_block_prefix\":0,\"expiration\":\"1970-01-01T00:00:00\",\"operations\":[[0,{\"fee\":{\"amount\":0,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.35\",\"to\":\"1.2.0\",\"amount\":{\"amount\":0,\"asset_id\":\"1.3.0\"},\"extensions\":[]}]],\"extensions\":[],\"signatures\":[]}}\n    th_a  transaction.cpp:465 verify_authority","data":{"code":3030001,"name":"tx_missing_active_auth","message":"missing required active authority","stack":[{"context":{"level":"error","file":"transaction.cpp","line":322,"method":"verify_authority","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:23:56"},"format":"Missing Active Authority {id}","data":{"id":"1.2.35","auth":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP",1]]},"owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP",1]]}}},{"context":{"level":"warn","file":"transaction.cpp","line":337,"method":"verify_authority","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:23:56"},"format":"","data":{"ops":[[0,{"fee":{"amount":0,"asset_id":"1.3.0"},"from":"1.2.35","to":"1.2.0","amount":{"amount":0,"asset_id":"1.3.0"},"extensions":[]}]],"sigs":[]}},{"context":{"level":"warn","file":"transaction.cpp","line":465,"method":"verify_authority","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:23:56"},"format":"","data":{"*this":{"ref_block_num":0,"ref_block_prefix":0,"expiration":"1970-01-01T00:00:00","operations":[[0,{"fee":{"amount":0,"asset_id":"1.3.0"},"from":"1.2.35","to":"1.2.0","amount":{"amount":0,"asset_id":"1.3.0"},"extensions":[]}]],"extensions":[],"signatures":[]}}}]}}}"""
        )

    mockHttp.enqueue(
        """{"id":1,"error":{"code":1,"message":"3030001 tx_missing_active_auth: missing required active authority\nMissing Active Authority 1.2.35\n    {\"id\":\"1.2.35\",\"auth\":{\"weight_threshold\":1,\"account_auths\":[],\"key_auths\":[[\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",1]]},\"owner\":{\"weight_threshold\":1,\"account_auths\":[],\"key_auths\":[[\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",1]]}}\n    th_a  transaction.cpp:322 verify_authority\n\n    {\"ops\":[[0,{\"fee\":{\"amount\":0,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.35\",\"to\":\"1.2.0\",\"amount\":{\"amount\":0,\"asset_id\":\"1.3.0\"},\"extensions\":[]}]],\"sigs\":[]}\n    th_a  transaction.cpp:337 verify_authority\n\n    {\"*this\":{\"ref_block_num\":0,\"ref_block_prefix\":0,\"expiration\":\"1970-01-01T00:00:00\",\"operations\":[[0,{\"fee\":{\"amount\":0,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.35\",\"to\":\"1.2.0\",\"amount\":{\"amount\":0,\"asset_id\":\"1.3.0\"},\"extensions\":[]}]],\"extensions\":[],\"signatures\":[]}}\n    th_a  transaction.cpp:465 verify_authority","data":{"code":3030001,"name":"tx_missing_active_auth","message":"missing required active authority","stack":[{"context":{"level":"error","file":"transaction.cpp","line":322,"method":"verify_authority","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:23:56"},"format":"Missing Active Authority {id}","data":{"id":"1.2.35","auth":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP",1]]},"owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP",1]]}}},{"context":{"level":"warn","file":"transaction.cpp","line":337,"method":"verify_authority","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:23:56"},"format":"","data":{"ops":[[0,{"fee":{"amount":0,"asset_id":"1.3.0"},"from":"1.2.35","to":"1.2.0","amount":{"amount":0,"asset_id":"1.3.0"},"extensions":[]}]],"sigs":[]}},{"context":{"level":"warn","file":"transaction.cpp","line":465,"method":"verify_authority","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:23:56"},"format":"","data":{"*this":{"ref_block_num":0,"ref_block_prefix":0,"expiration":"1970-01-01T00:00:00","operations":[[0,{"fee":{"amount":0,"asset_id":"1.3.0"},"from":"1.2.35","to":"1.2.0","amount":{"amount":0,"asset_id":"1.3.0"},"extensions":[]}]],"extensions":[],"signatures":[]}}}]}}}"""
    )

    val test = api.validationApi.verifyAccountAuthority(accountName2, listOf(public2.address()))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(DCoreException::class.java)
  }

  @Test fun `should validate signed transaction`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_transaction",[1370282,0]],"id":1}""",
            """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_chain_id",[]],"id":0}""",
            """{"id":0,"result":"17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc"}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_dynamic_global_properties",[]],"id":2}""",
            """{"id":2,"result":{"id":"2.1.0","head_block_number":3442144,"head_block_id":"003485e0f7300e97b92207ea19de4993914e3188","time":"2018-12-19T15:24:00","current_miner":"1.4.6","next_maintenance_time":"2018-12-20T00:00:00","last_budget_time":"2018-12-19T00:00:00","unspent_fee_budget":10461228,"mined_rewards":"335997000000","miner_budget_from_fees":22030422,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":14,"recently_missed_count":3,"current_aslot":9282537,"recent_slots_filled":"249727409722337398080179390021076115419","dynamic_flags":0,"last_irreversible_block_num":3442144}}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"validate_transaction",[{"expiration":"2018-12-19T15:24:33","ref_block_num":34272,"ref_block_prefix":2534289655,"extensions":[],"operations":[[39,{"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7","nonce":735604672334802432},"fee":{"amount":500000,"asset_id":"1.3.0"}}]],"signatures":["2062d7b1b43e388a4061f7db26f2dad3c6e81d0f32b6aa00d2c9ad14484140d841157be7a75460e39e3d2f05e411dd72a9cd8051442fe853ba00d6482d275a9646"]}]],"id":3}""",
            """{"id":3,"result":{"ref_block_num":34272,"ref_block_prefix":2534289655,"expiration":"2018-12-19T15:24:33","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["2062d7b1b43e388a4061f7db26f2dad3c6e81d0f32b6aa00d2c9ad14484140d841157be7a75460e39e3d2f05e411dd72a9cd8051442fe853ba00d6482d275a9646"],"operation_results":[[0,{}]]}}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
    )
    mockHttp.enqueue(
        """{"id":1,"result":"17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc"}"""
    )
    mockHttp.enqueue(
        """{"id":1,"result":{"id":"2.1.0","head_block_number":3442144,"head_block_id":"003485e0f7300e97b92207ea19de4993914e3188","time":"2018-12-19T15:24:00","current_miner":"1.4.6","next_maintenance_time":"2018-12-20T00:00:00","last_budget_time":"2018-12-19T00:00:00","unspent_fee_budget":10461228,"mined_rewards":"335997000000","miner_budget_from_fees":22030422,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":14,"recently_missed_count":3,"current_aslot":9282537,"recent_slots_filled":"249727409722337398080179390021076115419","dynamic_flags":0,"last_irreversible_block_num":3442144}}"""
    )
    mockHttp.enqueue(
        """{"id":1,"result":{"ref_block_num":34272,"ref_block_prefix":2534289655,"expiration":"2018-12-19T15:24:33","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["2062d7b1b43e388a4061f7db26f2dad3c6e81d0f32b6aa00d2c9ad14484140d841157be7a75460e39e3d2f05e411dd72a9cd8051442fe853ba00d6482d275a9646"],"operation_results":[[0,{}]]}}"""
    )

    val trxNew = api.transactionApi.createTransaction(trx.operations).blockingGet()
        .withSignature(ECKeyPair.fromBase58(private))
    val test = api.validationApi.validateTransaction(trxNew)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should fail to validate signed transaction`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_transaction",[1370282,0]],"id":1}""",
            """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_chain_id",[]],"id":0}""",
            """{"id":0,"result":"17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc"}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_dynamic_global_properties",[]],"id":2}""",
            """{"id":2,"result":{"id":"2.1.0","head_block_number":3442144,"head_block_id":"003485e0f7300e97b92207ea19de4993914e3188","time":"2018-12-19T15:24:00","current_miner":"1.4.6","next_maintenance_time":"2018-12-20T00:00:00","last_budget_time":"2018-12-19T00:00:00","unspent_fee_budget":10461228,"mined_rewards":"335997000000","miner_budget_from_fees":22030422,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":14,"recently_missed_count":3,"current_aslot":9282537,"recent_slots_filled":"249727409722337398080179390021076115419","dynamic_flags":0,"last_irreversible_block_num":3442144}}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"validate_transaction",[{"expiration":"2018-12-19T15:24:33","ref_block_num":34272,"ref_block_prefix":2534289655,"extensions":[],"operations":[[39,{"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7","nonce":735604672334802432},"fee":{"amount":500000,"asset_id":"1.3.0"}}]],"signatures":["1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089"]}]],"id":3}""",
            """{"id":3,"error":{"code":1,"message":"3030001 tx_missing_active_auth: missing required active authority\nMissing Active Authority 1.2.34\n    {\"id\":\"1.2.34\",\"auth\":{\"weight_threshold\":1,\"account_auths\":[],\"key_auths\":[[\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",1]]},\"owner\":{\"weight_threshold\":1,\"account_auths\":[],\"key_auths\":[[\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",1]]}}\n    th_a  transaction.cpp:365 verify_authority1\n\n    {\"ops\":[[39,{\"fee\":{\"amount\":500000,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.34\",\"to\":\"1.2.35\",\"amount\":{\"amount\":1500000,\"asset_id\":\"1.3.0\"},\"memo\":{\"from\":\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",\"to\":\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",\"nonce\":\"735604672334802432\",\"message\":\"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7\"},\"extensions\":[]}]],\"sigs\":\"DCT6XMv4Nkg11y3WYb8J2ueWbSumvzVz3Zd65wGShmdkPmGonhs4X\"}\n    th_a  transaction.cpp:374 verify_authority1\n\n    {\"*this\":{\"ref_block_num\":34271,\"ref_block_prefix\":1928460153,\"expiration\":\"2018-12-19T15:24:26\",\"operations\":[[39,{\"fee\":{\"amount\":500000,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.34\",\"to\":\"1.2.35\",\"amount\":{\"amount\":1500000,\"asset_id\":\"1.3.0\"},\"memo\":{\"from\":\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",\"to\":\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",\"nonce\":\"735604672334802432\",\"message\":\"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7\"},\"extensions\":[]}]],\"extensions\":[],\"signatures\":[\"1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089\"]}}\n    th_a  transaction.cpp:465 verify_authority\n\n    {\"trx\":{\"ref_block_num\":34271,\"ref_block_prefix\":1928460153,\"expiration\":\"2018-12-19T15:24:26\",\"operations\":[[39,{\"fee\":{\"amount\":500000,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.34\",\"to\":\"1.2.35\",\"amount\":{\"amount\":1500000,\"asset_id\":\"1.3.0\"},\"memo\":{\"from\":\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",\"to\":\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",\"nonce\":\"735604672334802432\",\"message\":\"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7\"},\"extensions\":[]}]],\"extensions\":[],\"signatures\":[\"1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089\"]}}\n    th_a  db_block.cpp:669 _apply_transaction","data":{"code":3030001,"name":"tx_missing_active_auth","message":"missing required active authority","stack":[{"context":{"level":"error","file":"transaction.cpp","line":365,"method":"verify_authority1","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:23:59"},"format":"Missing Active Authority {id}","data":{"id":"1.2.34","auth":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]}}},{"context":{"level":"warn","file":"transaction.cpp","line":374,"method":"verify_authority1","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:23:59"},"format":"","data":{"ops":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"sigs":"DCT6XMv4Nkg11y3WYb8J2ueWbSumvzVz3Zd65wGShmdkPmGonhs4X"}},{"context":{"level":"warn","file":"transaction.cpp","line":465,"method":"verify_authority","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:23:59"},"format":"","data":{"*this":{"ref_block_num":34271,"ref_block_prefix":1928460153,"expiration":"2018-12-19T15:24:26","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089"]}}},{"context":{"level":"warn","file":"db_block.cpp","line":669,"method":"_apply_transaction","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:23:59"},"format":"","data":{"trx":{"ref_block_num":34271,"ref_block_prefix":1928460153,"expiration":"2018-12-19T15:24:26","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089"]}}}]}}}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
    )
    mockHttp.enqueue(
        """{"id":1,"result":"17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc"}"""
    )
    mockHttp.enqueue(
        """{"id":1,"result":{"id":"2.1.0","head_block_number":3442144,"head_block_id":"003485e0f7300e97b92207ea19de4993914e3188","time":"2018-12-19T15:24:00","current_miner":"1.4.6","next_maintenance_time":"2018-12-20T00:00:00","last_budget_time":"2018-12-19T00:00:00","unspent_fee_budget":10461228,"mined_rewards":"335997000000","miner_budget_from_fees":22030422,"miner_budget_from_rewards":"639249000000","accounts_registered_this_interval":14,"recently_missed_count":3,"current_aslot":9282537,"recent_slots_filled":"249727409722337398080179390021076115419","dynamic_flags":0,"last_irreversible_block_num":3442144}}"""
    )
    mockHttp.enqueue(
        """{"id":1,"error":{"code":1,"message":"3030001 tx_missing_active_auth: missing required active authority\nMissing Active Authority 1.2.34\n    {\"id\":\"1.2.34\",\"auth\":{\"weight_threshold\":1,\"account_auths\":[],\"key_auths\":[[\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",1]]},\"owner\":{\"weight_threshold\":1,\"account_auths\":[],\"key_auths\":[[\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",1]]}}\n    th_a  transaction.cpp:365 verify_authority1\n\n    {\"ops\":[[39,{\"fee\":{\"amount\":500000,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.34\",\"to\":\"1.2.35\",\"amount\":{\"amount\":1500000,\"asset_id\":\"1.3.0\"},\"memo\":{\"from\":\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",\"to\":\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",\"nonce\":\"735604672334802432\",\"message\":\"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7\"},\"extensions\":[]}]],\"sigs\":\"DCT6XMv4Nkg11y3WYb8J2ueWbSumvzVz3Zd65wGShmdkPmGonhs4X\"}\n    th_a  transaction.cpp:374 verify_authority1\n\n    {\"*this\":{\"ref_block_num\":34271,\"ref_block_prefix\":1928460153,\"expiration\":\"2018-12-19T15:24:26\",\"operations\":[[39,{\"fee\":{\"amount\":500000,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.34\",\"to\":\"1.2.35\",\"amount\":{\"amount\":1500000,\"asset_id\":\"1.3.0\"},\"memo\":{\"from\":\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",\"to\":\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",\"nonce\":\"735604672334802432\",\"message\":\"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7\"},\"extensions\":[]}]],\"extensions\":[],\"signatures\":[\"1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089\"]}}\n    th_a  transaction.cpp:465 verify_authority\n\n    {\"trx\":{\"ref_block_num\":34271,\"ref_block_prefix\":1928460153,\"expiration\":\"2018-12-19T15:24:26\",\"operations\":[[39,{\"fee\":{\"amount\":500000,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.34\",\"to\":\"1.2.35\",\"amount\":{\"amount\":1500000,\"asset_id\":\"1.3.0\"},\"memo\":{\"from\":\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",\"to\":\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",\"nonce\":\"735604672334802432\",\"message\":\"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7\"},\"extensions\":[]}]],\"extensions\":[],\"signatures\":[\"1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089\"]}}\n    th_a  db_block.cpp:669 _apply_transaction","data":{"code":3030001,"name":"tx_missing_active_auth","message":"missing required active authority","stack":[{"context":{"level":"error","file":"transaction.cpp","line":365,"method":"verify_authority1","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:23:59"},"format":"Missing Active Authority {id}","data":{"id":"1.2.34","auth":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]},"owner":{"weight_threshold":1,"account_auths":[],"key_auths":[["DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",1]]}}},{"context":{"level":"warn","file":"transaction.cpp","line":374,"method":"verify_authority1","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:23:59"},"format":"","data":{"ops":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"sigs":"DCT6XMv4Nkg11y3WYb8J2ueWbSumvzVz3Zd65wGShmdkPmGonhs4X"}},{"context":{"level":"warn","file":"transaction.cpp","line":465,"method":"verify_authority","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:23:59"},"format":"","data":{"*this":{"ref_block_num":34271,"ref_block_prefix":1928460153,"expiration":"2018-12-19T15:24:26","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089"]}}},{"context":{"level":"warn","file":"db_block.cpp","line":669,"method":"_apply_transaction","hostname":"","thread_name":"th_a","timestamp":"2018-12-19T15:23:59"},"format":"","data":{"trx":{"ref_block_num":34271,"ref_block_prefix":1928460153,"expiration":"2018-12-19T15:24:26","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f3aaceb17ae6718235bac851fb376b2c00cfc69ddb23d471a29135f49c336de5316d92ee14d73567d57781bd8a14d69259adbe6e6094b387d9d9ea60e25fcf089"]}}}]}}}"""
    )

    val trxNew = api.transactionApi.createTransaction(trx.operations).blockingGet()
        .withSignature(ECKeyPair.fromBase58(private))
    val test = api.validationApi.validateTransaction(trxNew.copy(signatures = listOf(signatureInvalid)))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(DCoreException::class.java)
  }

  @Test fun `should get fee for transfer OP`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_required_fees",[[[39,{"fee":{"amount":0,"asset_id":"1.3.0"}}]],"1.3.0"]],"id":1}""",
            """{"id":1,"result":[{"amount":500000,"asset_id":"1.3.0"}]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[{"amount":500000,"asset_id":"1.3.0"}]}"""
    )

    val test = api.validationApi.getFee(OperationType.TRANSFER2_OPERATION)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

}