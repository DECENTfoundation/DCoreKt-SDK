package ch.decent.sdk

import ch.decent.sdk.api.*

class DCoreApi internal constructor(internal val core: DCoreSdk) {

  /**
   * default transaction expiration in seconds used when broadcasting transactions,
   * after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  var transactionExpiration = 30

  fun setTimeout(seconds: Long) {
    core.setTimeout(seconds)
  }

  val accountApi = AccountApi(this)
  val assetApi = AssetApi(this)
  val validationApi = ValidationApi(this)
  val balanceApi = BalanceApi(this)
  val blockApi = BlockApi(this)
  val broadcastApi = BroadcastApi(this)
  val contentApi = ContentApi(this)
  val generalApi = GeneralApi(this)
  val historyApi = HistoryApi(this)
  val miningApi = MiningApi(this)
  val purchaseApi = PurchaseApi(this)
  val seedersApi = SeederApi(this)
  val callbackApi = CallbackApi(this)
  val subscriptionApi = SubscriptionApi(this)
  val transactionApi = TransactionApi(this)
}