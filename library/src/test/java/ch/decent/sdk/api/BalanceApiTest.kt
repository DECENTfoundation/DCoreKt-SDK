package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.LinearVestingPolicyCreate
import ch.decent.sdk.model.VestingBalanceObjectId
import ch.decent.sdk.testCheck
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Suite
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

@Suite.SuiteClasses(BalanceOperationsTest::class, BalanceApiTest::class)
@RunWith(Suite::class)
class BalanceSuite

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class BalanceOperationsTest : BaseOperationsTest() {

  @Test fun `create vesting balance`() {
    api.balanceApi.createVestingBalance(
        Helpers.credentials,
        Helpers.account2,
        AssetAmount(100),
        LinearVestingPolicyCreate(LocalDateTime.now(ZoneOffset.UTC), Duration.ZERO, Duration.ofSeconds(1))
    ).testCheck()
  }

  @Test fun `withdraw vesting balance`() {
    api.balanceApi.getAllVestingByAccount(Helpers.account2)
        .flatMap { api.balanceApi.withdrawVestingBalance(Helpers.credentials2, it.last().id, AssetAmount(50)) }
        .testCheck()
  }

}

class BalanceApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get balance for account id`() {
    api.balanceApi.get(Helpers.account, Helpers.createAssetId).testCheck()
  }

  @Test fun `should get balances for account id`() {
    api.balanceApi.getAll(Helpers.account).testCheck()
  }

  @Test fun `should get balance for account name`() {
    api.balanceApi.get(Helpers.accountName, Helpers.createAssetId).testCheck()
  }

  @Test fun `should get balances for account name`() {
    api.balanceApi.getAll(Helpers.accountName).testCheck()
  }

  @Test fun `should get balance for account id with asset`() {
    api.balanceApi.getWithAsset(Helpers.account, Helpers.createAsset).testCheck()
  }

  @Test fun `should get balances for account id with asset`() {
    api.balanceApi.getAllWithAsset(Helpers.account, listOf("DCT", Helpers.createAsset)).testCheck()
  }

  @Test fun `should get balance for account name with asset`() {
    api.balanceApi.getWithAsset(Helpers.accountName, Helpers.createAsset).testCheck()
  }

  @Test fun `should get balances for account name with asset`() {
    api.balanceApi.getAllWithAsset(Helpers.accountName, listOf("DCT", Helpers.createAsset)).testCheck()
  }

  @Test fun `should get vesting balances for account`() {
    api.balanceApi.getAllVestingByAccount(Helpers.account2)
        .map { it.last() }
        .testCheck {
          assertComplete()
          assertNoErrors()
          assertValue { it.balance.amount == 50L }
        }
  }

  @Test fun `should get vesting balance`() {
    api.balanceApi.getVesting(VestingBalanceObjectId()).testCheck()
  }

}
