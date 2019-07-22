package ch.decent.sdk.model

import ch.decent.sdk.model.types.Int64
import ch.decent.sdk.model.types.UInt64
import com.google.gson.annotations.SerializedName
import java.math.BigInteger
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf

data class NftDataType(
    @SerializedName("type") val type: Type = Type.STRING,
    @SerializedName("unique") val unique: Boolean = false,
    @SerializedName("modifiable") val modifiable: ModifiableBy = ModifiableBy.NOBODY,
    @SerializedName("name") val name: String? = null
) {

  enum class ModifiableBy {
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
      operator fun get(type: KType): Type = when {
        Type.STRING.types.any { type.isSubtypeOf(it) } -> STRING
        Type.BOOLEAN.types.any { type.isSubtypeOf(it) } -> BOOLEAN
        Type.INTEGER.types.any { type.isSubtypeOf(it) } -> INTEGER
        else -> throw IllegalArgumentException("type '$type' not supported")
      }
    }
  }
}
