@file:Suppress(
    "TooManyFunctions",
    "LongParameterList",
    "SpreadOperator",
    "ComplexMethod",
    "ThrowsCount"
)

package ch.decent.sdk.poet

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.DCoreClient
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import io.reactivex.Single
import kastree.ast.Node
import kastree.ast.psi.Parser
import okhttp3.OkHttpClient
import org.slf4j.Logger
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMembers

fun Any?.print() = println(this.toString())

fun main() {
//  val path = System.out
  val path = out
  Blocking.apiFiles.forEach {
    it.writeTo(path)
    println()
  }
  Blocking.apiFile.writeTo(path)
  Factory.file.writeTo(path)
}

fun Node.Expr.name() = (this as Node.Expr.Name).name
fun Node.File.structured() = this.decls.single() as Node.Decl.Structured
fun Node.WithModifiers.hasKeyword(keyword: Node.Modifier.Keyword) = this.mods.contains(Node.Modifier.Lit(keyword))
fun Node.Decl.Func.Param.isNullable() = this.type?.ref is Node.TypeRef.Nullable
fun Node.Decl.Func.paramNames() = this.params.joinToString { it.name }

fun Node.Decl.Func.paramSpecs(imports: List<String>) = this.params.map { p ->
  fun Node.Expr.defaultValue() = when (this) {
    is Node.Expr.Const -> CodeBlock.of(this.value)
    is Node.Expr.StringTmpl -> CodeBlock.of("%S", "")
    is Node.Expr.BinaryOp -> {
      when {
        this.lhs is Node.Expr.Name && this.lhs.name() == apiNameRef -> CodeBlock.of("$apiNameRef.${this.rhs.name()}")
        this.lhs is Node.Expr.Name -> CodeBlock.of("%T.${this.rhs.name()}", ClassName.bestGuess(this.lhs.name().fullName(imports)))
        this.lhs is Node.Expr.BinaryOp -> (this.lhs as Node.Expr.BinaryOp)
            .let { CodeBlock.of("%T.${it.rhs.name()}.${this.rhs.name()}", ClassName.bestGuess(it.lhs.name().fullName(imports))) }
        else -> throw IllegalStateException()
      }
    }
    is Node.Expr.Name -> CodeBlock.of("%M", this.name.member(imports))
    is Node.Expr.Call -> {
      when {
        this.args.isEmpty() && this.expr.name().first().isLowerCase() -> CodeBlock.of("%M()", this.expr.name().member(imports))
        this.args.isEmpty() -> CodeBlock.of("%T()", ClassName.bestGuess(this.expr.name().fullName(imports)))
        else -> throw IllegalStateException()
      }
    }
    else -> throw IllegalStateException()
  }
  val builder = ParameterSpec.builder(p.name, p.type!!.typeName(imports).copy(nullable = p.isNullable()))
  p.default?.let { builder.defaultValue(it.defaultValue()) }
  return@map builder.build()
}

fun Node.TypeRef.Simple.fullName(imports: List<String>) = this.pieces.single().name.fullName(imports)

fun String.member(imports: List<String>) = MemberName(fullName(imports).substringBeforeLast("."), this)

fun String.fullName(imports: List<String>) = when (this) {
  "Class" -> "java.lang.$this"
  "List", "Map", "emptyList" -> "kotlin.collections.$this"
  "Long", "Int", "Short", "Byte", "String", "Boolean" -> "kotlin.$this"
  else -> imports.find { it.substringAfterLast(".") == this }?.substringAfter("import ")
} ?: this

fun Node.Type.typeName(imports: List<String>): TypeName {
  fun recursive(r: Node.TypeRef?): TypeName {
    val type = when {
      r is Node.TypeRef.Simple && r.pieces.single().typeParams.isEmpty() -> ClassName.bestGuess(r.fullName(imports))
      r is Node.TypeRef.Simple -> ClassName.bestGuess(r.fullName(imports)).parameterizedBy(*r.pieces.single().typeParams.map { recursive(it?.ref) }.toTypedArray())
      r is Node.TypeRef.Nullable -> recursive(r.type)
      else -> throw IllegalStateException("unknown node: $r")
    }
    return if (type is ClassName && type.simpleName == "T") TypeVariableName("T")
    else type
  }

  return recursive(this.ref)
}

fun Node.TypeParam.typeName(imports: List<String>): TypeVariableName =
    TypeVariableName(name, ClassName.bestGuess((type!! as Node.TypeRef.Simple).fullName(imports)))

val out = File("library/src-gen/main/java")
val srcApi = "library/src/main/java/ch/decent/sdk/api/"
val packageName = "ch.decent.sdk"
val apiNameRef = "api"
val clientRef = DCoreClient::class.asClassName()
val apiRef = DCoreApi::class.asClassName()

val apiClasses = File(srcApi).listFiles()!!.map { Parser.parseFile(it.readText()) }
    .filterNot { it.structured().hasKeyword(Node.Modifier.Keyword.ABSTRACT) }
    .filterNot { it.structured().name == "CallbackApi" }

object Factory {

  private fun nullable(name: String, klass: KClass<*>) =
      ParameterSpec.builder(name, klass.asTypeName().copy(nullable = true)).defaultValue("null").build()

  fun builder(apiName: String, apiFile: FileSpec) = FunSpec.builder(apiName)
      .addParameter("client", OkHttpClient::class)
      .addParameter(nullable("websocketUrl", String::class))
      .addParameter(nullable("httpUrl", String::class))
      .addParameter(nullable("logger", Logger::class))
      .addStatement("return %T(%T(%T(client, websocketUrl, httpUrl, logger)))", ClassName(apiFile.packageName, apiFile.name), apiRef, clientRef)
      .build()

  val file = FileSpec.builder("ch.decent.sdk", "DCoreSdk")
      .addType(TypeSpec.objectBuilder("DCoreSdk")
          .addFunction(builder("createApiBlocking", Blocking.apiFile))
          .build())
      .build()
}

object Blocking {

  val pckg = "$packageName.api.blocking"
  private val blockingGet = Single<*>::blockingGet.name
  private val members = apiClasses.map { ClassName(pckg, it.structured().name) }

  val api = TypeSpec.classBuilder("DCoreApi")
      .primaryConstructor(FunSpec.constructorBuilder()
          .addParameter(apiNameRef, apiRef)
          .build())
      .addProperties(members.map {
        val name = it.simpleName.decapitalize()
        PropertySpec.builder(name, it)
            .initializer("%T($apiNameRef.$name)", it)
            .build()
      })
      .build()
  val apiFile = FileSpec.builder(pckg, api.name!!).addType(api).build()

  val apis = apiClasses.map {

    val klass = ClassName(it.pkg!!.names.joinToString("."), it.structured().name)

    val apiProp = PropertySpec.builder(apiNameRef, klass, KModifier.PRIVATE)
        .initializer(apiNameRef)
        .build()

    val ctor = FunSpec.constructorBuilder()
        .addModifiers(KModifier.INTERNAL)
        .addParameter(apiProp.name, apiProp.type, KModifier.PRIVATE)
        .build()

    val methods = it.structured().members
        .filterIsInstance<Node.Decl.Func>()
        .filterNot { it.hasKeyword(Node.Modifier.Keyword.PRIVATE) }
        .map { f ->
          val i = it.imports.map { it.names.joinToString(".") }
          val b = FunSpec.builder(f.name!!)
              .addCode("return $apiNameRef.%L(${f.paramNames()}).%L()", f.name, blockingGet)
              .addParameters(f.paramSpecs(i))

          if (f.typeParams.isNotEmpty()) {
            b.addTypeVariable(f.typeParams.single().typeName(i))
          }
          b.build()
        }

    TypeSpec.classBuilder(klass)
        .addProperty(apiProp)
        .primaryConstructor(ctor)
        .addFunctions(methods)
        .build()
  }
  val apiFiles = apis.map {
    FileSpec.builder(pckg, it.name!!)
        .addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                .addMember("%S", "TooManyFunctions")
                .addMember("%S", "LongParameterList")
                .build()
        )
        .addType(it)
        .build()
  }
}
