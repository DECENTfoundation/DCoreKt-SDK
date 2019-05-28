package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.model.NftApple
import ch.decent.sdk.model.NftModel
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
        Helpers.createNft,
        100,
        false,
        "an apple",
        NftModel.createDefinitions(NftApple::class),
        true
    ).testCheck()
  }

  @Test fun `nft-1 should create nft nested definition`() {
    api.nftApi.create(
        Helpers.credentials,
        "${Helpers.createNft}.NESTED",
        100,
        false,
        "an apple",
        NftModel.createDefinitions(NftApple::class),
        true
    ).testCheck()
  }

  @Test fun `nft-2 should update nft definition`() {
    api.nftApi.update(
        Helpers.credentials,
        Helpers.createNft,
        description = "an apple updated"
    ).testCheck()
  }

  @Test fun `nft-3 should issue nft`() {
    api.nftApi.issue(
        Helpers.credentials,
        Helpers.createNft,
        Helpers.account,
        NftApple(5, "red", false)
    )
  }

}

@RunWith(Parameterized::class)
class NftApiTest(channel: Channel) : BaseApiTest(channel) {
  @Test fun `should get list of NFTs`() {
    api.nftApi.countAll().testCheck()
  }

  @Test fun `should get list of NFTs data`() {
    api.nftApi.countAllData().testCheck()
  }

}
