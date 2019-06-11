package ch.decent.sdk.api

import ch.decent.sdk.testCheck
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BlockApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get block header`() {
    api.blockApi.getHeader(10).testCheck()
  }

  @Test fun `should get head block time`() {
    api.blockApi.getHeadTime().testCheck()
  }

  @Test fun `should get signed block`() {
    api.blockApi.get(10).testCheck()
  }
}
