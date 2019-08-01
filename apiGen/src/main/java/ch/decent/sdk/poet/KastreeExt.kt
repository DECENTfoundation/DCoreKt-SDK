@file:Suppress(
    "TooManyFunctions",
    "LongParameterList",
    "SpreadOperator",
    "ComplexMethod",
    "ThrowsCount"
)

package ch.decent.sdk.poet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import kastree.ast.Node

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
        this.lhs is Node.Expr.Name -> CodeBlock.of("%T.${this.rhs.name()}", this.lhs.name().className(imports))
        this.lhs is Node.Expr.BinaryOp -> (this.lhs as Node.Expr.BinaryOp)
            .let { CodeBlock.of("%T.${it.rhs.name()}.${this.rhs.name()}", it.lhs.name().className(imports)) }
        else -> throw IllegalStateException()
      }
    }
    is Node.Expr.Name -> CodeBlock.of("%M", this.name.member(imports))
    is Node.Expr.Call -> {
      when {
        this.args.isEmpty() && this.expr.name().first().isLowerCase() -> CodeBlock.of("%M()", this.expr.name().member(imports))
        this.args.isEmpty() -> CodeBlock.of("%T()", this.expr.name().className(imports))
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
fun Node.TypeRef.Simple.className(imports: List<String>) = ClassName.bestGuess(fullName(imports))

fun String.member(imports: List<String>) = MemberName(fullName(imports).substringBeforeLast("."), this)

fun String.fullName(imports: List<String>) = when (this) {
  "Class" -> "java.lang.$this"
  "List", "Map", "emptyList" -> "kotlin.collections.$this"
  "Long", "Int", "Short", "Byte", "String", "Boolean", "Unit", "Any" -> "kotlin.$this"
  else -> imports.find { it.substringAfterLast(".") == this }?.substringAfter("import ")
} ?: this

fun String.className(imports: List<String>) = ClassName.bestGuess(fullName(imports))

fun Node.Type.typeName(imports: List<String>): TypeName {
  fun recursive(r: Node.TypeRef?): TypeName {
    val type = when {
      r is Node.TypeRef.Simple && r.pieces.single().typeParams.isEmpty() -> r.className(imports)
      r is Node.TypeRef.Simple -> r.className(imports).parameterizedBy(*r.pieces.single().typeParams.map { recursive(it?.ref) }.toTypedArray())
      r is Node.TypeRef.Nullable -> recursive(r.type)
      else -> throw IllegalStateException("unknown node: $r")
    }
    return if (type is ClassName && type.simpleName == "T") TypeVariableName("T")
    else type
  }

  return recursive(this.ref)
}

fun Node.Type.returnType(imports: List<String>): TypeName =
    (typeName(imports) as ParameterizedTypeName).typeArguments.single()

fun Node.TypeParam.typeName(imports: List<String>): TypeVariableName =
    TypeVariableName(name, ClassName.bestGuess((type!! as Node.TypeRef.Simple).fullName(imports)))
