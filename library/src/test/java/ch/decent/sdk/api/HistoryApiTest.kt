package ch.decent.sdk.api

import ch.decent.sdk.account
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.print
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class HistoryApiTest(channel: Channel) : BaseApiTest(channel) {
//  override val useMock: Boolean = false

  @Test fun `should get account history`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[3,"get_account_history",["1.2.34","1.7.0",100,"1.7.0"]],"id":1}""",
            """{"id":1,"result":[{"id":"1.7.557","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":480676,"trx_in_block":0,"op_in_trx":0,"virtual_op":1231},{"id":"1.7.556","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":480675,"trx_in_block":0,"op_in_trx":0,"virtual_op":1228},{"id":"1.7.456","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":419229,"trx_in_block":0,"op_in_trx":0,"virtual_op":902},{"id":"1.7.455","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":419228,"trx_in_block":0,"op_in_trx":0,"virtual_op":899},{"id":"1.7.368","op":[0,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":150000000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"9637208953715299072","message":"b7da6b1f94635777b2649309af699cee7ece00f31f0a652eb786a03b32ff2714"},"extensions":[]}],"result":[0,{}],"block_num":385238,"trx_in_block":0,"op_in_trx":0,"virtual_op":632},{"id":"1.7.367","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":385122,"trx_in_block":0,"op_in_trx":0,"virtual_op":629}]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[{"id":"1.7.557","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":480676,"trx_in_block":0,"op_in_trx":0,"virtual_op":1231},{"id":"1.7.556","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":480675,"trx_in_block":0,"op_in_trx":0,"virtual_op":1228},{"id":"1.7.456","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":419229,"trx_in_block":0,"op_in_trx":0,"virtual_op":902},{"id":"1.7.455","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":["0:5","0:8"],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":419228,"trx_in_block":0,"op_in_trx":0,"virtual_op":899},{"id":"1.7.368","op":[0,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":150000000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"9637208953715299072","message":"b7da6b1f94635777b2649309af699cee7ece00f31f0a652eb786a03b32ff2714"},"extensions":[]}],"result":[0,{}],"block_num":385238,"trx_in_block":0,"op_in_trx":0,"virtual_op":632},{"id":"1.7.367","op":[2,{"fee":{"amount":500000,"asset_id":"1.3.0"},"account":"1.2.34","new_options":{"memo_key":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","voting_account":"1.2.3","num_miner":0,"votes":[],"extensions":[],"allow_subscription":false,"price_per_subscribe":{"amount":0,"asset_id":"1.3.0"},"subscription_period":0},"extensions":{}}],"result":[0,{}],"block_num":385122,"trx_in_block":0,"op_in_trx":0,"virtual_op":629}]}"""
    )

    val test = api.historyApi.getAccountHistory(account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get relative account history`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[3,"get_relative_account_history",["1.2.34",0,10,0]],"id":1}""",
            """{"id":1,"result":[{"id":"1.7.57238","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3424128,"trx_in_block":0,"op_in_trx":0,"virtual_op":60320},{"id":"1.7.57236","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3423895,"trx_in_block":0,"op_in_trx":0,"virtual_op":60314},{"id":"1.7.57067","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3356379,"trx_in_block":0,"op_in_trx":0,"virtual_op":59796},{"id":"1.7.57065","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3356332,"trx_in_block":0,"op_in_trx":0,"virtual_op":59790},{"id":"1.7.57064","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3356325,"trx_in_block":0,"op_in_trx":0,"virtual_op":59787},{"id":"1.7.56849","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":3,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"8225985137041510913","message":"6bb0a05d5d597d2e468883f65dcebdf5"},"extensions":[]}],"result":[0,{}],"block_num":3341179,"trx_in_block":0,"op_in_trx":0,"virtual_op":59136},{"id":"1.7.56848","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":2,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"13772278880363122689","message":"9b955a68cf707d0617c40fc1dcc843a7"},"extensions":[]}],"result":[0,{}],"block_num":3340937,"trx_in_block":0,"op_in_trx":0,"virtual_op":59133},{"id":"1.7.56844","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"12349095297475186433","message":"f8c41beea4705ed03e0c048dd149ac09"},"extensions":[]}],"result":[0,{}],"block_num":3340722,"trx_in_block":0,"op_in_trx":0,"virtual_op":59121},{"id":"1.7.56805","op":[42,{"fee":{"amount":0,"asset_id":"1.3.0"},"author":"1.2.34","escrow":{"amount":10,"asset_id":"1.3.0"},"content":"2.13.621"}],"result":[0,{}],"block_num":3333778,"trx_in_block":0,"op_in_trx":1,"virtual_op":59003},{"id":"1.7.56772","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.687","amount":{"amount":10000000,"asset_id":"1.3.53"},"memo":{"from":"DCT1111111111111111111111111111111114T1Anm","to":"DCT1111111111111111111111111111111114T1Anm","nonce":0,"message":"00000000"},"extensions":[]}],"result":[0,{}],"block_num":3329053,"trx_in_block":0,"op_in_trx":0,"virtual_op":58906}]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[{"id":"1.7.57238","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3424128,"trx_in_block":0,"op_in_trx":0,"virtual_op":60320},{"id":"1.7.57236","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3423895,"trx_in_block":0,"op_in_trx":0,"virtual_op":60314},{"id":"1.7.57067","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3356379,"trx_in_block":0,"op_in_trx":0,"virtual_op":59796},{"id":"1.7.57065","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3356332,"trx_in_block":0,"op_in_trx":0,"virtual_op":59790},{"id":"1.7.57064","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3356325,"trx_in_block":0,"op_in_trx":0,"virtual_op":59787},{"id":"1.7.56849","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":3,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"8225985137041510913","message":"6bb0a05d5d597d2e468883f65dcebdf5"},"extensions":[]}],"result":[0,{}],"block_num":3341179,"trx_in_block":0,"op_in_trx":0,"virtual_op":59136},{"id":"1.7.56848","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":2,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"13772278880363122689","message":"9b955a68cf707d0617c40fc1dcc843a7"},"extensions":[]}],"result":[0,{}],"block_num":3340937,"trx_in_block":0,"op_in_trx":0,"virtual_op":59133},{"id":"1.7.56844","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","nonce":"12349095297475186433","message":"f8c41beea4705ed03e0c048dd149ac09"},"extensions":[]}],"result":[0,{}],"block_num":3340722,"trx_in_block":0,"op_in_trx":0,"virtual_op":59121},{"id":"1.7.56805","op":[42,{"fee":{"amount":0,"asset_id":"1.3.0"},"author":"1.2.34","escrow":{"amount":10,"asset_id":"1.3.0"},"content":"2.13.621"}],"result":[0,{}],"block_num":3333778,"trx_in_block":0,"op_in_trx":1,"virtual_op":59003},{"id":"1.7.56772","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.687","amount":{"amount":10000000,"asset_id":"1.3.53"},"memo":{"from":"DCT1111111111111111111111111111111114T1Anm","to":"DCT1111111111111111111111111111111114T1Anm","nonce":0,"message":"00000000"},"extensions":[]}],"result":[0,{}],"block_num":3329053,"trx_in_block":0,"op_in_trx":0,"virtual_op":58906}]}"""
    )

    val test = api.historyApi.getAccountHistoryRelative(account, limit = 10)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()

    test.values().map { it.map { it.id } }.print()
  }

  @Test fun `should get account history balances`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[3,"search_account_balance_history",["1.2.34",[],null,0,0,2,3]],"id":1}""",
            """{"id":1,"result":[{"hist_object":{"id":"1.7.57067","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3356379,"trx_in_block":0,"op_in_trx":0,"virtual_op":59796},"balance":{"asset0":{"amount":-1,"asset_id":"1.3.0"},"asset1":{"amount":0,"asset_id":"1.3.0"}},"fee":{"amount":500000,"asset_id":"1.3.0"}},{"hist_object":{"id":"1.7.57065","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3356332,"trx_in_block":0,"op_in_trx":0,"virtual_op":59790},"balance":{"asset0":{"amount":-1,"asset_id":"1.3.0"},"asset1":{"amount":0,"asset_id":"1.3.0"}},"fee":{"amount":500000,"asset_id":"1.3.0"}},{"hist_object":{"id":"1.7.57064","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3356325,"trx_in_block":0,"op_in_trx":0,"virtual_op":59787},"balance":{"asset0":{"amount":-1,"asset_id":"1.3.0"},"asset1":{"amount":0,"asset_id":"1.3.0"}},"fee":{"amount":500000,"asset_id":"1.3.0"}}]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[{"hist_object":{"id":"1.7.57067","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3356379,"trx_in_block":0,"op_in_trx":0,"virtual_op":59796},"balance":{"asset0":{"amount":-1,"asset_id":"1.3.0"},"asset1":{"amount":0,"asset_id":"1.3.0"}},"fee":{"amount":500000,"asset_id":"1.3.0"}},{"hist_object":{"id":"1.7.57065","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3356332,"trx_in_block":0,"op_in_trx":0,"virtual_op":59790},"balance":{"asset0":{"amount":-1,"asset_id":"1.3.0"},"asset1":{"amount":0,"asset_id":"1.3.0"}},"fee":{"amount":500000,"asset_id":"1.3.0"}},{"hist_object":{"id":"1.7.57064","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":3356325,"trx_in_block":0,"op_in_trx":0,"virtual_op":59787},"balance":{"asset0":{"amount":-1,"asset_id":"1.3.0"},"asset1":{"amount":0,"asset_id":"1.3.0"}},"fee":{"amount":500000,"asset_id":"1.3.0"}}]}"""
    )

    val test = api.historyApi.searchAccountBalanceHistory(account, startOffset = 2, limit = 3)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get account balance history for op`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[3,"get_account_balance_for_transaction",["1.2.34","1.7.7570"]],"id":1}""",
            """{"id":1,"result":{"hist_object":{"id":"1.7.7570","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":2447775,"trx_in_block":0,"op_in_trx":0,"virtual_op":7595},"balance":{"asset0":{"amount":-1,"asset_id":"1.3.0"},"asset1":{"amount":0,"asset_id":"1.3.0"}},"fee":{"amount":500000,"asset_id":"1.3.0"}}}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"hist_object":{"id":"1.7.7570","op":[39,{"fee":{"amount":500000,"asset_id":"1.3.0"},"from":"1.2.34","to":"1.2.35","amount":{"amount":1,"asset_id":"1.3.0"},"extensions":[]}],"result":[0,{}],"block_num":2447775,"trx_in_block":0,"op_in_trx":0,"virtual_op":7595},"balance":{"asset0":{"amount":-1,"asset_id":"1.3.0"},"asset1":{"amount":0,"asset_id":"1.3.0"}},"fee":{"amount":500000,"asset_id":"1.3.0"}}}"""
    )

    val test = api.historyApi.getAccountBalanceForTransaction(account, "1.7.7570".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}