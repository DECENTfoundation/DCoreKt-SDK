package ch.decent.sdk.api

import ch.decent.sdk.model.Regions
import ch.decent.sdk.model.toChainObject
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class SeederApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get seeder by id`() {
    val test = api.seedersApi.get("1.2.17".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should list seeders by price`() {
    val test = api.seedersApi.listByPrice()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should list seeders by upload`() {
    val test = api.seedersApi.listByUpload()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should list seeders by region`() {
    val test = api.seedersApi.listByRegion(Regions.NONE.code)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should list seeders by rating`() {
    val test = api.seedersApi.listByRating(10)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}