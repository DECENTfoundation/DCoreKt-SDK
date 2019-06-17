package ch.decent.sdk.api

import ch.decent.sdk.testCheck
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.threeten.bp.LocalDateTime

@RunWith(Parameterized::class)
class GeneralApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `get chain props`() {
    api.generalApi.getChainProperties().testCheck()
  }

  @Test fun `get general props`() {
    api.generalApi.getGlobalProperties().testCheck()
  }

  @Test fun `get config`() {
    api.generalApi.getConfig().testCheck()
  }

  @Test fun `get chain id`() {
    api.generalApi.getChainId().testCheck()
  }

  @Test fun `get dynamic props`() {
    api.generalApi.getDynamicGlobalProperties().testCheck()
  }

  @Test fun `get time to maintenance`() {
    api.generalApi.getTimeToMaintenance(LocalDateTime.parse("2018-10-13T22:26:02.825")).testCheck()
  }

}
