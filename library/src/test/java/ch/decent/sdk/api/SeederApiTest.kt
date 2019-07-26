package ch.decent.sdk.api

import ch.decent.sdk.model.Regions
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.testCheck
import io.reactivex.schedulers.Schedulers
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@Ignore //todo
@RunWith(Parameterized::class)
class SeederApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get seeder by id`() {
    api.seederApi.get("1.2.17".toChainObject()).testCheck()
  }

  @Test fun `should list seeders by price`() {
    api.seederApi.listByPrice().testCheck()
  }

  @Test fun `should list seeders by upload`() {
    api.seederApi.listByUpload().testCheck()
  }

  @Test fun `should list seeders by region`() {
    api.seederApi.listByRegion(Regions.NONE.code).testCheck()
  }

  @Test fun `should list seeders by rating`() {
    api.seederApi.listByRating(10).testCheck()
  }
}
