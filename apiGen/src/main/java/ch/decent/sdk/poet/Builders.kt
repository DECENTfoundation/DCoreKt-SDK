package ch.decent.sdk.poet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import kastree.ast.Node

object Builders {

  private fun apiClassSpec(api: ApiDescriptor) = TypeSpec.classBuilder(apiRef.simpleName)
      .primaryConstructor(FunSpec.constructorBuilder()
          .addParameter(apiNameRef, apiRef)
          .build())
      .superclass(ClassName(packageNameApi, "BaseCoreApi"))
      .addSuperclassConstructorParameter("$apiNameRef.core")
      .addProperties(apiClasses.map { ClassName(api.packageName, it.structured().name) }.map {
        val name = it.simpleName.decapitalize()
        PropertySpec.builder(name, it)
            .initializer("%T($apiNameRef.$name)", it)
            .build()
      })
      .build()

  fun apiFile(api: ApiDescriptor) = apiClassSpec(api).let { FileSpec.builder(api.packageName, it.name!!).addType(it).build() }

  private fun apiServiceClasses(api: ApiDescriptor): List<TypeSpec> = apiClasses
//      .filter { it.structured().name == "NftApi" }
      .map {
        val klass = ClassName(it.pkg!!.names.joinToString("."), it.structured().name)

        val apiProp = PropertySpec.builder(apiNameRef, klass, KModifier.PRIVATE)
            .initializer(apiNameRef)
            .build()

        val ctor = FunSpec.constructorBuilder()
            .addModifiers(KModifier.INTERNAL)
            .addParameter(apiProp.name, apiProp.type, KModifier.PRIVATE)
            .build()

        val apiDoc = DocReader.docs.getValue(klass.simpleName).toMutableList()
        val methods = it.structured().members
            .filterIsInstance<Node.Decl.Func>()
            .filterNot { it.hasKeyword(Node.Modifier.Keyword.PRIVATE) }
            .map { f ->
              val i = it.imports.map { it.names.joinToString(".") }

              val b = FunSpec.builder(f.name!!)
                  .addCode("return $apiNameRef.%L(${f.paramNames()}).%L", f.name, api.returnCodeBlock)
                  .addParameters(f.paramSpecs(i))

              if (f.typeParams.isNotEmpty()) b.addTypeVariable(f.typeParams.single().typeName(i))
              DocReader.applyDocs(b, apiDoc, f, i)
              api.methodBuilder(b, f.type!!.returnType(i))

              b.build()
            }

        TypeSpec.classBuilder(klass)
            .addProperty(apiProp)
            .primaryConstructor(ctor)
            .addFunctions(methods)
            .build()
      }

  fun apiServiceFiles(api: ApiDescriptor) = apiServiceClasses(api).map {
    FileSpec.builder(api.packageName, it.name!!)
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
