package ch.decent.sdk.api

import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BlockApiTest(channel: Channel) : BaseApiTest(channel) {
//  override val useMock: Boolean = false

  @Test fun `should get block header`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_block_header",[10]],"id":1}""",
            """{"id":1,"result":{"previous":"00000009fdaa51c1bbb7ca167aa87cf36ef330a1","timestamp":"2018-04-26T11:24:45","miner":"1.4.8","transaction_merkle_root":"0000000000000000000000000000000000000000","extensions":[]}}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"previous":"00000009fdaa51c1bbb7ca167aa87cf36ef330a1","timestamp":"2018-04-26T11:24:45","miner":"1.4.8","transaction_merkle_root":"0000000000000000000000000000000000000000","extensions":[]}}"""
    )

    val test = api.blockApi.getBlockHeader(10)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get head block time`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"head_block_time",[]],"id":1}""",
            """{"id":1,"result":"2018-10-12T14:37:40"}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":"2018-10-12T14:37:40"}"""
    )

    val test = api.blockApi.headBlockTime()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get signed block`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_block",[10]],"id":1}""",
            """{"id":1,"result":{"previous":"00000009fdaa51c1bbb7ca167aa87cf36ef330a1","timestamp":"2018-04-26T11:24:45","miner":"1.4.8","transaction_merkle_root":"0000000000000000000000000000000000000000","extensions":[],"miner_signature":"204d599f40e6413e6c1c0a009e8ae244f56872ca4f501d42ba61c8a8aa6e08eff9399b71973512fb40cdb33a7faf9d8c743f618f348ef00c39dbd0119a0e934028","transactions":[]}}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"previous":"00000009fdaa51c1bbb7ca167aa87cf36ef330a1","timestamp":"2018-04-26T11:24:45","miner":"1.4.8","transaction_merkle_root":"0000000000000000000000000000000000000000","extensions":[],"miner_signature":"204d599f40e6413e6c1c0a009e8ae244f56872ca4f501d42ba61c8a8aa6e08eff9399b71973512fb40cdb33a7faf9d8c743f618f348ef00c39dbd0119a0e934028","transactions":[]}}"""
    )

    val test = api.blockApi.getBlock(10)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}