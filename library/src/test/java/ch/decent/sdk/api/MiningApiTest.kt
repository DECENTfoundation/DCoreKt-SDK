package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.model.toChainObject
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class MiningApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `get actual votes`() {
    val test = api.miningApi.getActualVotes()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get asset per block for block`() {
    val test = api.miningApi.getAssetPerBlock(100)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get feeds by miner`() {
    val test = api.miningApi.getFeedsByMiner("1.2.4".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get miner by account`() {
    val test = api.miningApi.getMinerByAccount("1.2.4".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get miner count`() {
    val test = api.miningApi.getMinerCount()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get miners by ids`() {
    val test = api.miningApi.getMiners(listOf("1.4.2".toChainObject(), "1.4.3".toChainObject()))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get miners, load their accounts and put it into map with miner names`() {
    val test = api.miningApi.getMiners()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get new asset per block`() {
    val test = api.miningApi.getNewAssetPerBlock()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should list miners`() {
    val test = api.miningApi.listMinersRelative("")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `lookup votes`() {
    val test = api.miningApi.findVotedMiners(listOf("0:0", "0:1"))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `search miner voting`() {
    val test = api.miningApi.findAllVotingInfo("init", accountName = Helpers.accountName, onlyMyVotes = true)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

}