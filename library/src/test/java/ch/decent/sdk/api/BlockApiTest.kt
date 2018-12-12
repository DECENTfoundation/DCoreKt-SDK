package ch.decent.sdk.api

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BlockApiTest(channel: Channel) : BaseApiTest(channel) {
  override val useMock: Boolean = false

  @Test fun `should get block header`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"get_block_header",[10]],"id":1}""", """{"id":1,"result":{"previous":"00000009fdaa51c1bbb7ca167aa87cf36ef330a1","timestamp":"2018-04-26T11:24:45","miner":"1.4.8","transaction_merkle_root":"0000000000000000000000000000000000000000","extensions":[]}}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    mockHttp.enqueue("""{"id":1,"result":{"previous":"00000009fdaa51c1bbb7ca167aa87cf36ef330a1","timestamp":"2018-04-26T11:24:45","miner":"1.4.8","transaction_merkle_root":"0000000000000000000000000000000000000000","extensions":[]}}""")

    val test = api.blockApi.getBlockHeader(10)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get head block time`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"head_block_time",[]],"id":1}""", """{"id":1,"result":"2018-10-12T14:37:40"}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")

    mockHttp.enqueue("""{"id":1,"result":"2018-10-12T14:37:40"}""")

    val test = api.blockApi.headBlockTime()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get signed block`() {
    val test = api.blockApi.getBlock(10)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}