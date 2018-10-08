package ch.decent.sdk

import ch.decent.sdk.api.*
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Credentials
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
import io.reactivex.Single

class DCoreApi internal constructor(internal val core: DCoreSdk) {

  /**
   * default transaction expiration in seconds used when broadcasting transactions,
   * after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  var transactionExpiration = 30

  val accountApi = AccountApi(this)
  val assetApi = AssetApi(this)
  val authorityApi = AuthorityApi()
  val balanceApi = BalanceApi(this)
  val blockApi = BlockApi()
  val broadcastApi = BroadcastApi(this)
  val contentApi = ContentApi(this)
  val generalApi = GeneralApi()
  val historyApi = HistoryApi(this)
  val miningApi = MiningApi(this)
  val purchaseApi = PurchaseApi(this)
  val seedersApi = SeedersApi()
  val subscriptionApi = SubscriptionApi()
  val transactionApi = TransactionApi(this)
  val operationsHelper = OperationsHelper(this)
}