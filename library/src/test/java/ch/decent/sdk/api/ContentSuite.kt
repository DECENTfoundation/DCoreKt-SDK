package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.CoAuthors
import ch.decent.sdk.model.RegionalPrice
import ch.decent.sdk.model.Synopsis
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.testCheck
import ch.decent.sdk.utils.privateElGamal
import org.junit.FixMethodOrder
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized
import org.junit.runners.Suite
import org.threeten.bp.LocalDateTime

@Suite.SuiteClasses(ContentOperationsTest::class, ContentApiTest::class)
@RunWith(Suite::class)
class ContentSuite

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ContentOperationsTest : BaseOperationsTest() {

  @Test fun `content-1 should add a content`() {
    api.contentApi.add(
        Helpers.credentials,
        CoAuthors(mapOf(Helpers.account2 to 1000)), // 10%
        Helpers.createUri,
        listOf(RegionalPrice(AssetAmount(2))),
        LocalDateTime.now().plusDays(100),
        Synopsis("hello", "world")
    ).testCheck()
  }

  @Test fun `content-1 should add one more content`() {
    api.contentApi.add(
        Helpers.credentials,
        CoAuthors(),
        "${Helpers.createUri}/other",
        listOf(RegionalPrice(AssetAmount(20))),
        LocalDateTime.now().plusDays(10),
        Synopsis("hello", "world")
    ).testCheck()
  }

  @Test fun `content-2 should make a transfer to content`() {
    api.contentApi.transfer(Helpers.credentials, Helpers.createContentId, AssetAmount(1), "transfer to content")
        .testCheck()
  }

  @Test fun `content-2 should update a content`() {
    api.contentApi.update(
        Helpers.credentials,
        Helpers.createUri,
        Synopsis("hello", "update")
    ).testCheck()
  }

  @Test fun `content-3 should make a purchase`() {
    api.contentApi.purchase(Helpers.credentials, Helpers.createUri)
        .testCheck()
  }

  @Test fun `content-4 should rate and comment a purchased content`() {
    api.purchaseApi.rateAndComment(
        Helpers.credentials,
        Helpers.createUri,
        4,
        "hello comment"
    ).testCheck()
  }

  @Test fun `content-5 should remove a content`() {
    api.contentApi.remove(Helpers.credentials, Helpers.createUri)
        .testCheck()
  }
}

@RunWith(Parameterized::class)
class ContentApiTest(channel: Channel) : BaseApiTest(channel) {

  @Ignore //todo
  @Test fun `should generate content keys`() {
    api.contentApi.generateKeys(listOf(ChainObject.parse("1.2.17"), ChainObject.parse("1.2.18"))).testCheck()
  }

  @Test fun `should get content by id`() {
    api.contentApi.get(Helpers.createContentId).testCheck()
  }

  @Test fun `should get contents by ids`() {
    api.contentApi.getAll(listOf(Helpers.createContentId, Helpers.createContentId2)).testCheck()
  }

  @Test fun `should get content by uri`() {
    api.contentApi.get(Helpers.createUri).testCheck()
  }

  @Test fun `should get list of publishing managers`() {
    api.contentApi.listAllPublishersRelative("").testCheck()
  }

  @Test fun `should restore content encryption key`() {
    api.contentApi.restoreEncryptionKey(Helpers.credentials.keyPair.privateElGamal(), Helpers.createPurchaseId).testCheck()
  }

  @Test fun `should get list of content by search`() {
    api.contentApi.findAll("").testCheck()
  }
}
