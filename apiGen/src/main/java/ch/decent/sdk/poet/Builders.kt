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
      .filter { it.structured().name == "AccountApi" }
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
              val docs = DocReader.applyDocs(apiDoc, f, i)
              docs?.let { b.addKdoc(api.docBuilder(it)) }
              api.methodBuilder(b, f.type!!.returnType(i))
              f.mods.filterIsInstance<Node.Modifier.AnnotationSet>().map {
                it.anns.single().let {
                  val ba = AnnotationSpec.builder(it.names.single().className(i))
                  it.args.singleOrNull()?.let {
                    val str = ((it.expr as Node.Expr.StringTmpl).elems.single() as Node.Expr.StringTmpl.Elem.Regular).str
                    ba.addMember("%S", str)
                  }
                  ba.build()
                }
              }.also { b.addAnnotations(it) }

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
