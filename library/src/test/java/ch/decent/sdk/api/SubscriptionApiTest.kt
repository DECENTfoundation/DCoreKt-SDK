package ch.decent.sdk.api

import ch.decent.sdk.account
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class SubscriptionApiTest(channel: Channel) : BaseApiTest(channel) {
//  override val useMock: Boolean = false

  @Test fun `should get list of subscription for consumer`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"list_subscriptions_by_consumer",["1.2.34",10]],"id":1}""",
            """{"id":1,"result":[]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[]}"""
    )

    val test = api.subscriptionApi.getAllByConsumer(account, 10)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}