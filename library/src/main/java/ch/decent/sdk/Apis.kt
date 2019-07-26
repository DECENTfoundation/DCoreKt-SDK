package ch.decent.sdk

import ch.decent.sdk.api.AccountApi
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.Account
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.toObjectId
import ch.decent.sdk.poet.print
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import org.slf4j.Logger
import java.util.concurrent.Future

object Apis {

  class AccountApiSync internal constructor(private val api: AccountApi) {
    /**
     * Get account by id.
     *
     * @param id account id
     *
     * @return an account if exist, [ObjectNotFoundException] if not found
     */
    fun get(id: AccountObjectId): Account = api.get(id).blockingGet()
  }

  class AccountApiFuture internal constructor(private val api: AccountApi) {
    /**
     * Get account by id.
     *
     * @param id account id
     *
     * @return an account if exist, [ObjectNotFoundException] if not found
     */
    fun get(id: AccountObjectId): Future<Account> = api.get(id).toFuture()
  }

  interface ApiCallback<T> {
    fun onSuccess(value: T)
    fun onError(error: Throwable)
  }

  class AccountApiAsync internal constructor(private val api: AccountApi) {
    /**
     * Get account by id.
     *
     * @param id account id
     *
     * @return an account if exist, [ObjectNotFoundException] if not found
     */
    fun get(id: AccountObjectId, success: (Account) -> Unit, error: (Throwable) -> Unit): Disposable =
        api.get(id).subscribe(success, error)

    fun get(id: AccountObjectId, success: (Account) -> Unit): Disposable =
        api.get(id).subscribe(success, {})

    fun get(id: AccountObjectId, callback: ApiCallback<Account>): Disposable =
        api.get(id).subscribe({ v -> callback.onSuccess(v) }, { e -> callback.onError(e) })
  }

  class DCoreApiSync(api: DCoreApi) {
    val accountApi = AccountApiSync(api.accountApi)
  }

  class DCoreApiAsync(api: DCoreApi) {
    val accountApi = AccountApiAsync(api.accountApi)
  }

  class DCoreApiFuture(api: DCoreApi) {
    val accountApi = AccountApiFuture(api.accountApi)
  }
}

val api = DCoreApi(DCoreClient(OkHttpClient.Builder().build(), "wss://testnet-api.dcore.io", "https://testnet-api.dcore.io/"))

fun DCoreApi.createSync() = Apis.DCoreApiSync(this)
fun DCoreApi.createAsync() = Apis.DCoreApiAsync(this)
fun DCoreApi.createFuture() = Apis.DCoreApiFuture(this)

object DCoreSdka {

  @JvmStatic @JvmOverloads
  fun create(client: OkHttpClient, webSocketUrl: String?, httpUrl: String?, logger: Logger? = null): Apis.DCoreApiSync =
      api.createSync()

  @JvmStatic @JvmOverloads
  fun createAsync(client: OkHttpClient, webSocketUrl: String?, httpUrl: String?, logger: Logger? = null): Apis.DCoreApiAsync =
      api.createAsync()

  @JvmStatic @JvmOverloads
  fun createFuture(client: OkHttpClient, webSocketUrl: String?, httpUrl: String?, logger: Logger? = null): Apis.DCoreApiFuture =
      api.createFuture()

  @JvmStatic @JvmOverloads
  fun createRx(client: OkHttpClient, webSocketUrl: String?, httpUrl: String?, logger: Logger? = null): DCoreApi =
      api
}

fun main() {
  val callback = api.createAsync()
  callback.accountApi.get(AccountObjectId(27), object : Apis.ApiCallback<Account> {
    override fun onSuccess(value: Account) {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(error: Throwable) {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

  })
  callback.accountApi.get("1.2.27".toObjectId()) { account -> account.print() }


  val sync = api.createSync()
  sync.accountApi.get("1.2.27".toObjectId())

  val future = api.createFuture()
  future.accountApi.get("1.2.27".toObjectId())
}
