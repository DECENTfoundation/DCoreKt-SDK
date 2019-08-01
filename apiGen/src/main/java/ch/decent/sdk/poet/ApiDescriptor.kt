package ch.decent.sdk.poet

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeName

data class ApiDescriptor(
    val packageSuffix: String,
    val createMethodName: String,
    val returnCodeBlock: String,
    val methodBuilder: FunSpec.Builder.(TypeName) -> Any = { returns(it) }
) {
  val packageName = "$packageNameApi.$packageSuffix"
}
