package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.Helpers
import ch.decent.sdk.crypto.address
import ch.decent.sdk.crypto.ecKey
import ch.decent.sdk.exception.DCoreException
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.Authority
import ch.decent.sdk.model.CoAuthors
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.RegionalPrice
import ch.decent.sdk.model.Synopsis
import ch.decent.sdk.model.operation.AssetClaimFeesOperation
import ch.decent.sdk.model.operation.AssetFundPoolsOperation
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.testCheck
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Ignore
import org.junit.Test
import org.junit.runners.MethodSorters
import org.slf4j.LoggerFactory
import org.threeten.bp.LocalDateTime

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class OperationsTest {

  companion object {
    private lateinit var timestamp: String
    private lateinit var uri: String
    private lateinit var asset: String

    @BeforeClass @JvmStatic fun before() {
      timestamp = System.currentTimeMillis().toString().dropLast(3)
      uri = "http://hello.world.io?timestamp=$timestamp"
      asset = "SDK.${timestamp}T"
    }
  }

  private lateinit var api: DCoreApi

  private val accountName: String
    get() = "sdk-account-$timestamp"

  @Before fun init() {
    val logger = LoggerFactory.getLogger("RxWebSocket")
    api = DCoreSdk.createForWebSocket(Helpers.client(logger), Helpers.wsUrl, logger)
  }

  @After fun finish() {
  }

  //  @Ignore
  @Test fun `accounts-1 should create account`() {
    api.accountApi.create(Helpers.credentials, accountName, Helpers.public.address())
        .testCheck()
  }

  //  @Ignore
  @Test fun `accounts-2 should make a transfer to new account`() {
    api.accountApi.transfer(Helpers.credentials, accountName, DCoreConstants.DCT.amount(1.0))
        .testCheck()
  }

  //  @Ignore
  @Test fun `accounts-3 should update credentials on a new account`() {
    api.accountApi.createCredentials(accountName, Helpers.private)
        .flatMap { api.accountApi.update(it, active = Authority(Helpers.public2.address())) }
        .testCheck()
  }

  //  @Ignore
  @Test fun `accounts-4 should make a vote on a new account`() {
    api.accountApi.createCredentials(accountName, Helpers.private2)
        .flatMap { api.miningApi.vote(it, listOf("1.4.4".toChainObject())) }
        .testCheck()
  }

  //  @Ignore
  @Test fun `message- should send message`() {
    api.messagingApi.send(Helpers.credentials, listOf(Helpers.account2 to "test message encrypted t=$timestamp"))
        .testCheck()
  }

  //  @Ignore
  @Test fun `message- should send unencrypted message`() {
    api.messagingApi.sendUnencrypted(Helpers.credentials, listOf(Helpers.account2 to "test message plain t=$timestamp"))
        .testCheck()
  }

  //  @Ignore
  @Test fun `content- should make a transfer to content`() {
    api.contentApi.transfer(Helpers.credentials, "2.13.3".toChainObject(), AssetAmount(1), "transfer to content")
        .testCheck()
  }

  //  @Ignore
  @Test fun `content-1 should add a content`() {
    api.contentApi.add(
        Helpers.credentials,
        CoAuthors(mapOf(Helpers.account2 to 1000)), // 10%
        uri,
        listOf(RegionalPrice(AssetAmount(2))),
        LocalDateTime.now().plusDays(100),
        Synopsis("hello", "world")
    ).testCheck()
  }

  //  @Ignore
  @Test fun `content-2 should update a content`() {
    api.contentApi.update(
        Helpers.credentials,
        uri,
        Synopsis("hello", "update")
    ).testCheck()
  }

  //  @Ignore
  @Test fun `content-3 should make a purchase`() {
    api.contentApi.purchase(Helpers.credentials, uri)
        .testCheck()
  }

  //  @Ignore
  @Test fun `content-4 should rate and comment a purchased content`() {
    api.purchaseApi.rateAndComment(
        Helpers.credentials,
        uri,
        4,
        "hello comment"
    ).testCheck()
  }

  //  @Ignore
  @Test fun `content-5 should remove a content`() {
    api.contentApi.remove(Helpers.credentials, uri)
        .testCheck()
  }

  @Test fun `asset-1 should create an asset`() {
    api.assetApi.create(
        Helpers.credentials,
        asset,
        12,
        "hello api"
    ).testCheck()
  }

  // account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; }
  // therefore Missing Active Authority 1.2.0
  @Test @Ignore fun `asset- should create a monitored asset`() {
    api.assetApi.createMonitoredAsset(
        Helpers.credentials,
        "MSDK",
        4,
        "hello api monitored"
    ).testCheck()
  }

  @Test fun `asset-2 should update an asset`() {
    api.assetApi.update(
        Helpers.credentials,
        "SDK.1557392016T",
        1 to 2,
        timestamp,
        true,
        timestamp.toLong()
    ).testCheck()
  }

  @Test fun `asset-3 should update advanced asset`() {
    api.assetApi.updateAdvanced(
        Helpers.credentials,
        asset,
        6,
        false
    ).testCheck()
  }

  @Test fun `asset-1 should issue an asset`() {
    api.assetApi.issue(
        Helpers.credentials,
        "1.3.40",
        200000
    ).testCheck()
  }

  @Test fun `asset-2 should fund an asset pool`() {
    api.assetApi.fund(
        Helpers.credentials,
        "1.3.40",
        0,
        100000 // 0.01 dct fee
    ).testCheck()
  }

  @Test fun `asset- should fund an asset pool from non-issuer account`() {
    val op = AssetFundPoolsOperation(Helpers.account2, AssetAmount(0, "1.3.36".toChainObject()), AssetAmount(1))
    api.broadcastApi.broadcastWithCallback(Helpers.private2.ecKey(), op)
        .testCheck()
  }

  @Test fun `asset-3 should make a transfer with fee`() {
    api.accountApi.transfer(
        Helpers.credentials,
        Helpers.accountName2,
        AssetAmount(1),
        fee = Fee("1.3.40".toChainObject())
    ).testCheck {
      assertComplete()
      assertNoErrors()
      assertValue { it.transaction.operations.single().fee.assetId == "1.3.40".toChainObject() }
    }
  }

  @Test fun `asset-4 should claim an asset pool`() {
    api.assetApi.claim(
        Helpers.credentials,
        "1.3.40",
        200000,
        0
    ).testCheck()
  }

  @Test fun `asset- should claim an asset pool from non-issuer account is not allowed`() {
    val op = AssetClaimFeesOperation(Helpers.account2, AssetAmount(0, "1.3.36"), AssetAmount(1))
    api.broadcastApi.broadcastWithCallback(Helpers.private2.ecKey(), op)
        .testCheck {
          assertError(DCoreException::class.java)
        }
  }

  @Test fun `asset-5 should reserve an asset`() {
    api.assetApi.reserve(
        Helpers.credentials,
        "1.3.40",
        200000
    ).testCheck()
  }
}
