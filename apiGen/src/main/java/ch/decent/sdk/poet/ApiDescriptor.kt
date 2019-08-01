package ch.decent.sdk.poet

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeName

data class ApiDescriptor(
    val packageSuffix: String,
    val createMethodName: String,
    val returnCodeBlock: String,
    val returnType: (TypeName) -> TypeName = { it },
    val methodBuilder: (FunSpec.Builder) -> Any = { }
) {
  val packageName = "$packageNameApi.$packageSuffix"
}
