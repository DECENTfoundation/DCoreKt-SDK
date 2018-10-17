package ch.decent.sdk.net.rpc

import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.exception.DCoreException
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.net.model.request.BaseRequest
import ch.decent.sdk.net.model.response.Error
import com.google.gson.Gson
import com.google.gson.JsonNull
import com.google.gson.JsonParser
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

internal class RpcService(url: String, client: OkHttpClient, private val gson: Gson) {

  private interface RpcEndpoint {
    @POST("rpc")
    fun request(@Body request: RequestBody): Single<ResponseBody>
  }

  private val service = Retrofit.Builder()
      .baseUrl(url)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
      .addConverterFactory(GsonConverterFactory.create(DCoreSdk.gsonBuilder.create()))
      .client(client)
      .build()
      .create(RpcEndpoint::class.java)

  private fun BaseRequest<*>.toRequestBody(): RequestBody = RequestBody.create(MEDIA_TYPE, gson.toJson(this))

  @Suppress("UNCHECKED_CAST")
  fun <T> request(request: BaseRequest<T>): Single<T> =
      service.request(request.toRequestBody())
          .map { JsonParser().parse(it.charStream()).asJsonObject }
          .flatMap {
            when {
              it.has("error") -> Single.error(DCoreException(gson.fromJson(it["error"], Error::class.java)))
              it.has("result") && request.returnClass == Unit::class.java -> Single.just(Unit) as Single<T>
              it.has("result") && ((it["result"].isJsonArray && it["result"].asJsonArray.contains(JsonNull.INSTANCE)) || it["result"] == JsonNull.INSTANCE)
              -> Single.error(ObjectNotFoundException(request.description()))
              it.has("result") -> Single.just<T>(gson.fromJson(it["result"], request.returnClass))
              else -> Single.error(IllegalStateException("invalid HTTP API response"))
            }
          }

  companion object {
    private val MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8")
  }

}
