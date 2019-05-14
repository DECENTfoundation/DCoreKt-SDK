package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.model.toChainObject
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class HistoryApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get account balance for op`() {
    val test = api.historyApi.getOperation(Helpers.account, "1.7.980424".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should list account history`() {
    val test = api.historyApi.listOperations("1.2.27".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should list relative account history`() {
    val test = api.historyApi.listOperationsRelative(Helpers.account, limit = 100)
        .map { it.joinToString("\n") { it.operation.toString() } }
        .doOnSuccess { System.out.println(it) }
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should search account history balances`() {
    val test = api.historyApi.findAllOperations(Helpers.account, startOffset = 2, limit = 3)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}
