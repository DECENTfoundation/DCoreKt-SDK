package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.exception.DCoreException
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.NftApple
import ch.decent.sdk.model.NftNotApple
import ch.decent.sdk.model.RawNft
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.net.model.request.SearchNftHistory
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
        NftApple.SYMBOL,
        100,
        false,
        "an apple",
        NftApple::class,
        true
    ).testCheck()
  }

  @Test fun `nft-2 should create nft nested definition`() {
    api.nftApi.create(
        Helpers.credentials,
        NftApple.SYMBOL_NESTED,
        100,
        false,
        "an apple",
        NftApple::class,
        true
    ).testCheck()
  }

  @Test fun `nft-3 should create some other nft definition`() {
    api.nftApi.create(
        Helpers.credentials,
        NftNotApple.SYMBOL,
        100,
        false,
        "not an apple",
        NftNotApple::class,
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
    ).testCheck()
  }

  @Test fun `nft-4 should fail issue same nft again`() {
    api.nftApi.issue(
        Helpers.credentials,
        Helpers.createNft,
        Helpers.account,
        NftApple(5, "red", false)
    ).testCheck { assertError(DCoreException::class.java) }
  }

  //todo should fail on unique constrain
  @Test fun `nft-3 should issue same nft other data`() {
    api.nftApi.issue(
        Helpers.credentials,
        Helpers.createNft,
        Helpers.account,
        NftApple(5, "green", false)
    ).testCheck()
  }

  @Test fun `nft-4 should issue some other nft`() {
    api.nftApi.issue(
        Helpers.credentials,
        NftNotApple.SYMBOL,
        Helpers.account,
        NftNotApple(true, -1, "this is not an apple")
    ).testCheck()
  }

  @Test fun `nft-4 should transfer nft`() {
    api.nftApi.transfer(
        Helpers.credentials,
        Helpers.account2,
        "1.11.2".toChainObject()
    ).testCheck()
  }

  @Test fun `nft-5 should update transferred nft by issuer`() {
    api.nftApi.getData("1.11.2".toChainObject(), NftNotApple::class)
        .doOnSuccess { it.data!!.eaten = !it.data!!.eaten }
        .flatMap { api.nftApi.updateData(Helpers.credentials, it.id, it.data!!) }
        .testCheck()
  }

  @Test fun `nft-5 should update transferred nft by owner`() {
    api.nftApi.getData("1.11.2".toChainObject(), NftNotApple::class)
        .doOnSuccess { it.data!!.eaten = !it.data!!.eaten }
        .flatMap { api.nftApi.updateData(Credentials(Helpers.account2, Helpers.private2), it.id, it.data!!) }
        .testCheck()
  }
}

@RunWith(Parameterized::class)
class NftApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get NFT by symbol or id`() {
    api.nftApi.get(Helpers.createNft).testCheck()
  }

  @Test fun `should get NFT by id`() {
    api.nftApi.get("1.10.0".toChainObject()).testCheck()
  }

  @Test fun `should fail get NFT by id`() {
    api.nftApi.get("1.10.10000".toChainObject()).testCheck { assertError(ObjectNotFoundException::class.java) }
  }

  @Test fun `should get NFTs by id`() {
    api.nftApi.getAll(listOf("1.10.0".toChainObject(), "1.10.1".toChainObject())).testCheck()
  }

  @Test fun `should get NFT by symbol`() {
    api.nftApi.getBySymbol(Helpers.createNft).testCheck()
  }

  @Test fun `should get NFTs by symbol`() {
    api.nftApi.getAllBySymbol(listOf(Helpers.createNft, Helpers.createNftNested)).testCheck()
  }

  @Test fun `should get NFTs data by id typed`() {
    api.nftApi.getAllData(listOf("1.11.0".toChainObject(), "1.11.1".toChainObject()), NftApple::class).testCheck()
  }

  @Test fun `should get NFTs data by id registered`() {
    api.registerNfts(
        "1.10.0".toChainObject() to NftApple::class,
        "1.10.1".toChainObject() to NftApple::class
    )
    api.nftApi.getAllData(listOf("1.11.0".toChainObject(), "1.11.1".toChainObject())).testCheck {
      assertValue { it.filter { it.data is NftApple }.count() == 2 }
    }
  }

  @Test fun `should get NFTs data by id not registered`() {
    api.nftApi.getAllData(listOf("1.11.0".toChainObject(), "1.11.1".toChainObject())).testCheck {
      assertValue { it.all { it.data is RawNft } }
    }
  }

  @Test fun `should get NFTs data by id raw`() {
    api.nftApi.getAllDataRaw(listOf("1.11.0".toChainObject(), "1.11.1".toChainObject())).testCheck()
  }

  @Test fun `should get NFT data by id typed`() {
    api.nftApi.getData("1.11.0".toChainObject(), NftApple::class).testCheck()
  }

  @Test fun `should get NFT data by id registered`() {
    api.registerNfts("1.10.0".toChainObject() to NftApple::class)
    api.nftApi.getData("1.11.0".toChainObject()).testCheck { assertValue { it.data is NftApple } }
  }

  @Test fun `should fail get NFT data by id`() {
    api.nftApi.getData("1.11.2".toChainObject(), NftApple::class).testCheck { assertError(IllegalArgumentException::class.java) }
  }

  @Test fun `should get NFT data by id raw`() {
    api.nftApi.getDataRaw("1.11.0".toChainObject()).testCheck()
  }

  @Test fun `should get count of NFTs`() {
    api.nftApi.countAll().testCheck { assertValue(3) }
  }

  @Test fun `should get count of NFTs data`() {
    api.nftApi.countAllData().testCheck { assertValue(3) }
  }

  @Test fun `should get NFT balances raw`() {
    api.nftApi.getNftBalancesRaw(Helpers.account).testCheck()
  }

  @Test fun `should get NFT balances typed`() {
    api.nftApi.getNftBalances(Helpers.account, "1.10.0".toChainObject(), NftApple::class).testCheck()
  }

  @Test fun `should get NFT balances registered`() {
    api.registerNfts("1.10.2".toChainObject() to NftNotApple::class)
    api.nftApi.getNftBalances(Helpers.account2, listOf("1.10.2".toChainObject())).testCheck { assertValue { it.all { it.data is NftNotApple } } }
  }

  @Test fun `should list all NFTs`() {
    api.nftApi.listAllRelative().testCheck()
  }

  @Test fun `should get data for NFT raw`() {
    api.nftApi.listDataByNftRaw("1.10.0".toChainObject()).testCheck()
  }

  @Test fun `should get data for NFT registered`() {
    api.registerNfts("1.10.0".toChainObject() to NftApple::class)
    api.nftApi.listDataByNft("1.10.0".toChainObject()).testCheck { assertValue { it.all { it.data is NftApple } } }
  }

  @Test fun `should get data for NFT typed`() {
    api.nftApi.listDataByNft("1.10.0".toChainObject(), NftApple::class).testCheck()
  }

  @Test fun `should search data for NFT, check issue and transfer count`() {
    api.nftApi.searchNftHistory("1.11.2".toChainObject()).testCheck { assertValue { it.count() == 2 } }
  }

}
