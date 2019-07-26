package ch.decent.sdk

import kotlin.String
import okhttp3.OkHttpClient
import org.slf4j.Logger

object DCoreSdk {
  fun createApiBlocking(
    client: OkHttpClient,
    websocketUrl: String? = null,
    httpUrl: String? = null,
    logger: Logger? = null
  ) = ch.decent.sdk.api.blocking.DCoreApi(DCoreApi(DCoreClient(client, websocketUrl, httpUrl,
      logger)))
}
