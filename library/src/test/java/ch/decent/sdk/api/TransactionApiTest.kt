package ch.decent.sdk.api

import ch.decent.sdk.account
import ch.decent.sdk.crypto.Sha256Hash
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.BlockData
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.print
import ch.decent.sdk.utils.hex
import ch.decent.sdk.utils.unhex
import io.reactivex.schedulers.Schedulers
import org.junit.Ignore
import org.junit.Test
import org.threeten.bp.ZoneOffset

class TransactionApiTest(channel: Channel) : BaseApiTest(channel) {
//  override val useMock: Boolean = false

  //  already expired
  @Test fun `should get recent transaction by ID`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_recent_transaction_by_id",["322d451fb1dc9b3ec6bc521395f4547a8b62eb3f"]],"id":1}""",
            """{"id":1,"result":null}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":null}"""
    )

    val test = api.transactionApi.getRecentTransaction("322d451fb1dc9b3ec6bc521395f4547a8b62eb3f")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertTerminated()
        .assertError(ObjectNotFoundException::class.java)

  }

  @Test fun `should get a transaction by ID`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_transaction_by_id",["322d451fb1dc9b3ec6bc521395f4547a8b62eb3f"]],"id":1}""",
            """{"id":1,"result":{"ref_block_num":65525,"ref_block_prefix":2304643484,"expiration":"2018-10-13T12:37:19","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["2072e8b8efa1ca97c2f9d85f69a31761fc212858fc77b5d8bc824627117904214458a7deecc8b4fd8495f8b448d971ed92c0bcb0c9b3f3fcf0c7eba4c81303de4b"]}}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"ref_block_num":65525,"ref_block_prefix":2304643484,"expiration":"2018-10-13T12:37:19","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["2072e8b8efa1ca97c2f9d85f69a31761fc212858fc77b5d8bc824627117904214458a7deecc8b4fd8495f8b448d971ed92c0bcb0c9b3f3fcf0c7eba4c81303de4b"]}}"""
    )

    val test = api.transactionApi.getTransaction("322d451fb1dc9b3ec6bc521395f4547a8b62eb3f")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
        .assertValue { it.id == "322d451fb1dc9b3ec6bc521395f4547a8b62eb3f"}

  }

  @Test fun `should get a transaction by block`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_transaction",[1370282,0]],"id":1}""",
            """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
    )

    val test = api.transactionApi.getTransaction(1370282, 0)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()

  }

  @Test fun `should get a transaction hex dump`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_chain_id",[]],"id":1}""",
            """{"id":1,"result":"17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc"}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_transaction",[1370282,0]],"id":2}""",
            """{"id":2,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
        )
        .enqueue(
            """{"method":"call","params":[0,"get_transaction_hex",[{"expiration":"2018-07-26T11:27:07","ref_block_num":59561,"ref_block_prefix":2414941591,"extensions":[],"operations":[[39,{"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7","nonce":735604672334802432},"fee":{"amount":500000,"asset_id":"1.3.0"}}]],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"]}]],"id":3}""",
            """{"id":3,"result":"a9e89715f18f0bb0595b012720a10700000000000022230000000000020160e3160000000000000102c03f8e840c1699fd7808c2bb858e249c688c5be8acf0a0c1c484ab0cfb27f0a802e0ced80260630f641f61f6d6959f32b5c43b1a38be55666b98abfe8bafcc556b002ea2558d64350a204bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a70000011f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":"17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc"}"""
    )
    mockHttp.enqueue(
        """{"id":1,"result":{"ref_block_num":59561,"ref_block_prefix":2414941591,"expiration":"2018-07-26T11:27:07","operations":[[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"735604672334802432","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7"},"extensions":[]}]],"extensions":[],"signatures":["1f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"],"operation_results":[[0,{}]]}}"""
    )
    mockHttp.enqueue(
        """{"id":1,"result":"a9e89715f18f0bb0595b012720a10700000000000022230000000000020160e3160000000000000102c03f8e840c1699fd7808c2bb858e249c688c5be8acf0a0c1c484ab0cfb27f0a802e0ced80260630f641f61f6d6959f32b5c43b1a38be55666b98abfe8bafcc556b002ea2558d64350a204bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a70000011f140e5744bcef282147ef3f0bab8df46f49704a99046d6ea5db37ab3113e0f45935fd94af7b33189ad34fa1666ab7e54aa127d725e2018fb6b68771aacef54c41"}"""
    )

    val id = api.generalApi.getChainId().blockingGet()
    val ptrx = api.transactionApi.getTransaction(1370282, 0).blockingGet()
    val trx = Transaction(BlockData(ptrx.refBlockNum, ptrx.refBlockPrefix, ptrx.expiration.toEpochSecond(ZoneOffset.UTC)), ptrx.operations, id, ptrx.signatures)
    val test = api.transactionApi.getTransactionHex(trx)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get a list of proposed transactions`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_proposed_transactions",["1.2.34"]],"id":1}""",
            """{"id":1,"result":[]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[]}"""
    )

    val test = api.transactionApi.getProposedTransactions(account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}