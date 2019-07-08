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
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.NftModel
import ch.decent.sdk.model.RawNft
import com.google.gson.Gson
import org.threeten.bp.Duration
import java.util.concurrent.TimeoutException
import kotlin.reflect.KClass

class DCoreApi internal constructor(internal val core: DCoreSdk) {

  /**
   * default transaction expiration in seconds used when broadcasting transactions,
   * after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  var transactionExpiration: Duration = Duration.ofSeconds(EXPIRATION_DEF)

  /**
   * websocket request timeout, if no response is delivered within time window a [TimeoutException] is thrown
   */
  var timeout: Duration = Duration.ofMinutes(1)
    set(value) {
      field = value
      core.setTimeout(value.seconds)
    }

  val gson: Gson
    get() = core.gson

  internal val registeredNfts = mutableMapOf<ChainObject, KClass<out NftModel>>()

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

  /**
   * Register NFT data model with object id, if no model is provided the [RawNft] will be used
   *
   * @param idToClass id to class pairs
   */
  @JvmName("registerNftsKt")
  fun <T : NftModel> registerNfts(idToClass: List<Pair<ChainObject, KClass<T>>>) {
    registeredNfts.putAll(idToClass)
  }

  /**
   * Register NFT data model with object id, if no model is provided the [RawNft] will be used
   *
   * @param id NFT object id
   * @param clazz NFT data model class reference
   */
  fun <T : NftModel> registerNft(id: ChainObject, clazz: KClass<T>) {
    registeredNfts[id] = clazz
  }

  /**
   * Register NFT data model with object id, if no model is provided the [RawNft] will be used
   *
   * @param id NFT object id
   * @param clazz NFT data model class reference
   */
  @JvmName("registerNftsKt")
  fun <T : NftModel> registerNft(id: ChainObject, clazz: Class<T>) {
    registerNft(id, clazz.kotlin)
  }

  /**
   * remove registered NFT model
   *
   * @param id NFT object id
   */
  fun unregisterNft(id: ChainObject) {
    registeredNfts.remove(id)
  }

  /**
   * remove registered NFT model
   *
   * @param ids NFT object ids
   */
  fun unregisterNfts(ids: List<ChainObject>) {
    ids.forEach { unregisterNft(it) }
  }

}
