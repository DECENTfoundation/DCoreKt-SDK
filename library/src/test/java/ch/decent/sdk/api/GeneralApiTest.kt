package ch.decent.sdk.api

import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.threeten.bp.LocalDateTime

@RunWith(Parameterized::class)
class GeneralApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `get chain props`() {
    val test = api.generalApi.getChainProperties()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get general props`() {
    val test = api.generalApi.getGlobalProperties()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get config`() {
    val test = api.generalApi.getConfig()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get chain id`() {
    val test = api.generalApi.getChainId()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get dynamic props`() {
    val test = api.generalApi.getDynamicGlobalProperties()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get time to maintenance`() {
    val test = api.generalApi.getTimeToMaintenance(LocalDateTime.parse("2018-10-13T22:26:02.825"))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

}