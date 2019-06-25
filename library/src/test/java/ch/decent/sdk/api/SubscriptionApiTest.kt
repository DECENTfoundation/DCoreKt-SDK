package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.testCheck
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
@Ignore //todo
class SubscriptionApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get list of subscription for consumer`() {
    api.subscriptionApi.getAllByConsumer(Helpers.account, 10).testCheck()
  }
}
