package ch.decent.sdk

import ch.decent.sdk.DCoreConstants.EXPIRATION_DEF
import ch.decent.sdk.api.AccountApi
import ch.decent.sdk.api.AssetApi
import ch.decent.sdk.api.BalanceApi
import ch.decent.sdk.api.BlockApi
import ch.decent.sdk.api.BroadcastApi
import ch.decent.sdk.api.CallbackApi
import ch.decent.sdk.api.ContentApi
import ch.decent.sdk.api.GeneralApi
import ch.decent.sdk.api.HistoryApi
import ch.decent.sdk.api.MessagingApi
import ch.decent.sdk.api.MiningApi
import ch.decent.sdk.api.NftApi
import ch.decent.sdk.api.PurchaseApi
import ch.decent.sdk.api.SeederApi
import ch.decent.sdk.api.SubscriptionApi
import ch.decent.sdk.api.TransactionApi
import ch.decent.sdk.api.ValidationApi
import org.threeten.bp.Duration

class DCoreApi internal constructor(internal val core: DCoreSdk) {

  /**
   * default transaction expiration in seconds used when broadcasting transactions,
   * after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  var transactionExpiration: Duration = Duration.ofSeconds(EXPIRATION_DEF)

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
  val messagingApi = MessagingApi(this)
  val nftApi = NftApi(this)
}
