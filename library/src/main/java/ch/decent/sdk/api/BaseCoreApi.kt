package ch.decent.sdk.api

import ch.decent.sdk.DCoreClient
import ch.decent.sdk.DCoreConstants
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.NftModel
import ch.decent.sdk.model.RawNft
import com.google.gson.Gson
import org.threeten.bp.Duration
import java.util.concurrent.TimeoutException
import kotlin.reflect.KClass

abstract class BaseCoreApi(internal val core: DCoreClient) {
  /**
   * default transaction expiration in seconds used when broadcasting transactions,
   * after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block
   */
  var transactionExpiration: Duration = Duration.ofSeconds(DCoreConstants.EXPIRATION_DEF)

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
  @JvmName("registerNftKt")
  fun <T : NftModel> registerNft(id: ChainObject, clazz: KClass<T>) {
    registeredNfts[id] = clazz
  }

  /**
   * Register NFT data model with object id, if no model is provided the [RawNft] will be used
   *
   * @param id NFT object id
   * @param clazz NFT data model class reference
   */
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
