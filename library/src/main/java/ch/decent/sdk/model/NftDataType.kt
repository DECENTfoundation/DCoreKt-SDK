package ch.decent.sdk.model

import ch.decent.sdk.model.annotations.Unique
import ch.decent.sdk.model.types.Int64
import ch.decent.sdk.model.types.UInt64
import com.google.gson.annotations.SerializedName
import java.math.BigInteger
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

data class NftDataType(
    @SerializedName("type") val type: Type = Type.STRING,
    @SerializedName("unique") val unique: Boolean = false,
    @SerializedName("modifiable") val modifiable: Modifiable = Modifiable.NOBODY,
    @SerializedName("name") val name: String? = null
) {

  companion object {

    fun createDefinitions(model: Class<*>): List<NftDataType> = createDefinitions(model.kotlin)

    fun createDefinitions(model: KClass<*>): List<NftDataType> {
      val order = model.constructors.first().parameters.withIndex().associate { it.value.name to it.index }
      return model.declaredMemberProperties.map {
        val type = Type[it.returnType]
        val unique = it.findAnnotation<Unique>() != null
        val modifiable = it.findAnnotation<ch.decent.sdk.model.annotations.Modifiable>()?.let { it.modifiable } ?: Modifiable.NOBODY
        val name = it.name
        NftDataType(type, unique, modifiable, name)
      }.sortedBy { order[it.name] }
    }
  }

  enum class Modifiable {
    @SerializedName("nobody") NOBODY,
    @SerializedName("issuer") ISSUER,
    @SerializedName("owner") OWNER,
    @SerializedName("both") BOTH
  }

  enum class Type(vararg val types: KType) {
    @SerializedName("string") STRING(String::class.createType()),
    @SerializedName("integer") @UInt64 @Int64 INTEGER(
        Byte::class.createType(),
        Short::class.createType(),
        Int::class.createType(),
        Long::class.createType(),
        BigInteger::class.createType()
    ),
    @SerializedName("boolean") BOOLEAN(Boolean::class.createType());

    companion object {
      operator fun get(type: KType): Type = when (type) {
        in Type.STRING.types -> STRING
        in Type.BOOLEAN.types -> BOOLEAN
        in Type.INTEGER.types -> INTEGER
        else -> throw IllegalArgumentException("type '$type' not supported")
      }
    }
  }
}
