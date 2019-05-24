package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.model.NftApple
import ch.decent.sdk.model.NftDataType
import ch.decent.sdk.testCheck
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized
import org.junit.runners.Suite

@Suite.SuiteClasses(NftOperationsTest::class, NftApiTest::class)
@RunWith(Suite::class)
class NftSuite

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class NftOperationsTest : BaseOperationsTest() {

  @Test fun `nft-1 should create nft definition`() {
    api.nftApi.create(
        Helpers.credentials,
        "SDK.APPLE",
        100,
        false,
        "an apple",
        NftDataType.createDefinitions(NftApple::class),
        true
    ).testCheck()
  }
}

@RunWith(Parameterized::class)
class NftApiTest(channel: Channel) : BaseApiTest(channel) {

}
