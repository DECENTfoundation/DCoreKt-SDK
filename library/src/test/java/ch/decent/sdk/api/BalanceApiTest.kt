package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.testCheck
import org.junit.Test

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

  @Test fun `should get vesting balances`() {
    api.balanceApi.getAllVesting(Helpers.account).testCheck()
  }

}
