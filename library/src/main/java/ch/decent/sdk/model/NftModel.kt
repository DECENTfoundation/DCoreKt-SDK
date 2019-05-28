package ch.decent.sdk.model

import ch.decent.sdk.model.annotations.Modifiable
import ch.decent.sdk.model.annotations.Unique
import ch.decent.sdk.net.serialization.Variant
import com.google.gson.JsonArray
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

interface NftModel {
  fun values(): List<Variant> = values(this)

  companion object {
    fun <T : NftModel> createDefinitions(model: Class<T>): List<NftDataType> = createDefinitions(model.kotlin)

    fun <T : NftModel> createDefinitions(model: KClass<T>): List<NftDataType> = ordered(model).map {
      val type = NftDataType.Type[it.returnType]
      val unique = it.findAnnotation<Unique>() != null
      val modifiable = it.findAnnotation<Modifiable>()?.modifiable ?: NftDataType.ModifiableBy.NOBODY
      val name = it.name
      NftDataType(type, unique, modifiable, name)
    }

    @Suppress("IMPLICIT_CAST_TO_ANY", "SpreadOperator")
    fun <T : NftModel> create(values: JsonArray, model: KClass<T>): NftModel = ordered(model)
        .mapIndexed { idx, prop ->
          when (prop.returnType) {
            String::class.createType() -> values[idx].asString
            Boolean::class.createType() -> values[idx].asBoolean
            Byte::class.createType() -> values[idx].asByte
            Short::class.createType() -> values[idx].asShort
            Int::class.createType() -> values[idx].asInt
            Long::class.createType() -> values[idx].asLong
            else -> throw IllegalArgumentException("unsupported type for NFT model: ${prop.returnType}")
          }
        }
        .toList().toTypedArray()
        .let { model.constructors.first().call(*it) }

    @Suppress("UNCHECKED_CAST")
    fun <T : NftModel> values(model: T) = ordered(model::class).map { (it as KProperty1<T, Any>).get(model) }

    private fun <T : NftModel> ordered(klass: KClass<T>): List<KProperty1<T, *>> =
        klass.constructors.first().parameters.withIndex().associate { it.value.name to it.index }
            .let { order -> klass.declaredMemberProperties.sortedBy { order[it.name] } }
  }

}
