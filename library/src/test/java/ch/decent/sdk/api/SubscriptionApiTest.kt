package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import io.reactivex.schedulers.Schedulers
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
@Ignore
class SubscriptionApiTest(channel: Channel) : BaseApiTest(channel) {
  @Test fun `should get list of subscription for consumer`() {
    val test = api.subscriptionApi.getAllByConsumer(Helpers.account, 10)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}