package ch.decent.sdk.poet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.asClassName
import kastree.ast.Node
import kastree.ast.psi.Parser
import java.io.File
import java.util.concurrent.Future

val out = File("../library/gen/main/java")
val srcApi = "../library/src/main/java/ch/decent/sdk/api/rx"
val packageName = "ch.decent.sdk"
val packageNameApi = "$packageName.api"
val apiNameRef = "api"
val clientRef = ClassName.bestGuess("$packageName.DCoreClient")
val apiRef = ClassName.bestGuess("$packageNameApi.rx.DCoreApi")

val apiClasses = File(srcApi).listFiles()!!.map { Parser.parseFile(it.readText()) }
    .filterNot { it.structured().hasKeyword(Node.Modifier.Keyword.ABSTRACT) }
    .filterNot { it.structured().name == "CallbackApi" || it.structured().name == apiRef.simpleName }

val apis = listOf(
    ApiDescriptor("blocking", "createApiBlocking", "blockingGet()"),
    ApiDescriptor("futures", "createApiFutures", "toFuture()")
    { returns(Future::class.asClassName().parameterizedBy(it)) },
    ApiDescriptor("callback", "createApi", "subscribeWith(callback)")
    {
      returns(ClassName.bestGuess("$packageNameApi.Cancelable"))
      addParameter("callback", ClassName.bestGuess("$packageNameApi.Callback").parameterizedBy(it))
    }
)

fun main() {
  File("").absolutePath.print()
//  val path = System.out
  val path = out
  apis.forEach {
    Builders.apiFile(it).writeTo(path)
    Builders.apiServiceFiles(it).forEach { it.writeTo(path) }
  }
  Factory.file.writeTo(path)
}

fun Any?.print() = println(this.toString())
