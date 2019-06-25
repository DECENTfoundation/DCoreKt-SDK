package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.testCheck
import org.junit.Test

class HistoryApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get account balance for op`() {
    api.historyApi.getOperation(Helpers.account, "1.7.1".toChainObject()).testCheck()
  }

  @Test fun `should list account history`() {
    api.historyApi.listOperations(Helpers.account).testCheck()
  }

  @Test fun `should list relative account history`() {
    api.historyApi.listOperationsRelative(Helpers.account, limit = 100).testCheck()
  }

  @Test fun `should search account history balances`() {
    api.historyApi.findAllOperations(Helpers.account, startOffset = 2, limit = 3).testCheck()
  }
}
