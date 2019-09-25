package ch.decent.sdk.model

import com.google.gson.annotations.SerializedName

interface StaticVariant

interface StaticVariantSingle<T> : StaticVariant {
  val index: Int
  val value: T
}

abstract class StaticVariantParametrized(val objects: List<Any?>) : StaticVariant

data class StaticVariant1<T1 : Any?>(val obj: T1) : StaticVariantParametrized(listOf(obj))

data class StaticVariant2<T1 : Any?, T2 : Any?>(val obj1: T1? = null, val obj2: T2? = null) : StaticVariantParametrized(listOf(obj1, obj2))

data class FixedMaxSupply(
    @SerializedName("is_fixed_max_supply") val isFixedMaxSupply: Boolean
) : StaticVariantSingle<FixedMaxSupply> {

  // typedef static_variant<void_t, fixed_max_supply_struct>     asset_options_extensions
  // fixed_max_supply_struct has index 1 therefore we write '1''
  override val index: Int
    get() = 1

  override val value: FixedMaxSupply
    get() = this
}
