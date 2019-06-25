package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.testCheck
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PurchaseApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get list of history purchases`() {
    api.purchaseApi.getAllHistory(Helpers.account).testCheck()
  }

  @Test fun `should get list of open purchases`() {
    api.purchaseApi.getAllOpen().testCheck()
  }

  @Test fun `should get list of open purchases for uri`() {
    api.purchaseApi.getAllOpenByUri(Helpers.createUri).testCheck()
  }

  @Test fun `should get list of open purchases for account`() {
    api.purchaseApi.getAllOpenByAccount(Helpers.account).testCheck()
  }

  @Test fun `should get purchase for account and uri`() {
    api.purchaseApi.get(Helpers.account, Helpers.createUri).testCheck()
  }

  @Test fun `should search purchases`() {
    api.purchaseApi.findAll(Helpers.account, "").testCheck()
  }

  @Test fun `should search feedback`() {
    api.purchaseApi.findAllForFeedback(Helpers.createUri).testCheck()
  }
}
