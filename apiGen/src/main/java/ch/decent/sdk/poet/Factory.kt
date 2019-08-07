package ch.decent.sdk.poet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName

object Factory {

  private val docs = """
   Create Api. At least one of HTTP or WebSocket URLs must be set.
   
   @param client OkHttp client handling the requests
   @param webSocketUrl URL for the webSocket requests, eg: wss://testnet-socket.dcore.io/
   @param httpUrl URL for the HTTP requests, eg: https://testnet.dcore.io/
   @param logger optional logger for requests
   
   @return DCore API for making requests
  """.trimIndent()

  private fun nullable(name: String, klass: ClassName) =
      ParameterSpec.builder(name, klass.copy(nullable = true)).defaultValue("null").build()

  val params = listOf(
      ParameterSpec.builder("client", ClassName.bestGuess("okhttp3.OkHttpClient")).build(),
      nullable("websocketUrl", String::class.asClassName()),
      nullable("httpUrl", String::class.asClassName()),
      nullable("logger", ClassName.bestGuess("org.slf4j.Logger"))
  )

  fun builder(methodName: String, pckg: String) = FunSpec.builder(methodName)
      .addParameters(params)
      .addStatement("return %T(%T(%T(client, webSocketUrl, httpUrl, logger)))", ClassName(pckg, apiRef.simpleName), apiRef, clientRef)
      .addAnnotation(JvmStatic::class)
      .addKdoc(docs)
      .build()

  val rx = FunSpec.builder("createApiRx")
      .addParameters(params)
      .addStatement("return %T(%T(client, webSocketUrl, httpUrl, logger))", apiRef, clientRef)
      .addAnnotation(JvmStatic::class)
      .addKdoc(docs)
      .build()

  val file: FileSpec
    get() = FileSpec.builder("ch.decent.sdk", "DCoreSdk")
        .addType(TypeSpec.objectBuilder("DCoreSdk")
            .addFunction(rx)
            .apply { apis.forEach { addFunction(builder(it.createMethodName, it.packageName)) } }
            .build())
        .build()
}
