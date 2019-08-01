package ch.decent.sdk.api.rx

import ch.decent.sdk.DCoreClient
import ch.decent.sdk.api.BaseCoreApi

class DCoreApi internal constructor(core: DCoreClient) : BaseCoreApi(core) {
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
  val seederApi = SeederApi(this)
  val callbackApi = CallbackApi(this)
  val subscriptionApi = SubscriptionApi(this)
  val transactionApi = TransactionApi(this)
  val messagingApi = MessagingApi(this)
  val nftApi = NftApi(this)
}
