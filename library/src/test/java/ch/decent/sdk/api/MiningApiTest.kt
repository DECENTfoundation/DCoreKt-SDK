package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.model.VoteId
import ch.decent.sdk.model.toObjectId
import ch.decent.sdk.testCheck
import org.junit.Test

class MiningApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `get actual votes`() {
    api.miningApi.getActualVotes().testCheck()
  }

  @Test fun `get asset per block for block`() {
    api.miningApi.getAssetPerBlock(100).testCheck()
  }

  @Test fun `get feeds by miner`() {
    api.miningApi.getFeedsByMiner("1.2.4".toObjectId()).testCheck()
  }

  @Test fun `get miner by account`() {
    api.miningApi.getMinerByAccount("1.2.4".toObjectId()).testCheck()
  }

  @Test fun `get miner count`() {
    api.miningApi.getMinerCount().testCheck()
  }

  @Test fun `get miners by ids`() {
    api.miningApi.getMiners(listOf("1.4.2".toObjectId(), "1.4.3".toObjectId())).testCheck()
  }

  @Test fun `get miners, load their accounts and put it into map with miner names`() {
    api.miningApi.getMiners().testCheck()
  }

  @Test fun `get new asset per block`() {
    api.miningApi.getNewAssetPerBlock().testCheck()
  }

  @Test fun `should list miners`() {
    api.miningApi.listMinersRelative("").testCheck()
  }

  @Test fun `lookup votes`() {
    api.miningApi.findVotedMiners(listOf(VoteId.parse("0:0"), VoteId.parse("0:1"))).testCheck()
  }

  @Test fun `search miner voting`() {
    api.miningApi.findAllVotingInfo("init", accountName = Helpers.accountName, onlyMyVotes = true).testCheck()
  }

}
