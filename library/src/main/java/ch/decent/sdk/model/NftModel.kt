package ch.decent.sdk.model

import ch.decent.sdk.model.annotations.Modifiable
import ch.decent.sdk.model.annotations.Unique
import ch.decent.sdk.net.serialization.Variant
import com.google.gson.JsonArray
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

interface NftModel {
  fun values(): List<Variant> = values(this)

  @Suppress("UNCHECKED_CAST")
  fun createUpdate(): Map<String, Variant> = this::class.declaredMemberProperties
      .filter { it.findAnnotation<Modifiable>() != null }
      .associate { it.name to (it as KProperty1<NftModel, Any>).get(this) }

  companion object {
    internal fun <T : NftModel> createDefinitions(model: KClass<T>): List<NftDataType> = ordered(model).map {
      val type = NftDataType.Type[it.returnType]
      val unique = it.findAnnotation<Unique>() != null
      val modifiable = it.findAnnotation<Modifiable>()?.modifiable ?: NftDataType.ModifiableBy.NOBODY
      val name = it.name
      NftDataType(type, unique, modifiable, name)
    }

    @Suppress("SpreadOperator")
    internal fun <T : NftModel> create(values: JsonArray, model: KClass<T>): T = ordered(model).let { ordered ->
      ordered.mapIndexed { idx, prop ->
        try {
          parseType(values, idx, prop.returnType)
        } catch (ex: ClassCastException) {
          throw IllegalArgumentException("model: ${model.simpleName}[${ordered.joinToString { "${it.name}:${it.returnType}" }}] cannot be created from values: $values")
        } catch (ex: NumberFormatException) {
          throw IllegalArgumentException("model: ${model.simpleName}[${ordered.joinToString { "${it.name}:${it.returnType}" }}] cannot be created from values: $values")
        }
      }
    }
        .toList().toTypedArray()
        .let { model.constructors.first().call(*it) }

    @Suppress("UNCHECKED_CAST")
    internal fun <T : NftModel> values(model: T) = ordered(model::class).map { (it as KProperty1<T, Any>).get(model) }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun parseType(values: JsonArray, idx: Int, type: KType) =
        when (type) {
          String::class.createType() -> values[idx].asString
          Boolean::class.createType() -> values[idx].asBoolean
          Byte::class.createType() -> values[idx].asByte
          Short::class.createType() -> values[idx].asShort
          Int::class.createType() -> values[idx].asInt
          Long::class.createType() -> values[idx].asLong
          else -> throw IllegalArgumentException("unsupported type for NFT model: $type")
        }

    private fun <T : NftModel> ordered(klass: KClass<T>): List<KProperty1<T, *>> =
        klass.constructors.first().parameters.withIndex().associate { it.value.name to it.index }
            .let { order -> klass.declaredMemberProperties.sortedBy { order[it.name] } }
  }
}

data class RawNft(
    val values: JsonArray
) : NftModel {
  override fun values(): List<Variant> = values.map {
    when {
      it.isJsonPrimitive.not() -> IllegalArgumentException("value type not supported: $it")
      it.asJsonPrimitive.isNumber -> it.asNumber
      it.asJsonPrimitive.isBoolean -> it.asBoolean
      it.asJsonPrimitive.isString -> it.asString
      else -> IllegalArgumentException("value type not supported: ${it::class.createType()}")
    }
  }

  override fun createUpdate(): Map<String, Variant> {
    throw IllegalArgumentException("generic NFT has no implicit parameter names, use `createUpdate(nft: Nft)` function")
  }

  fun createUpdate(nft: Nft): Map<String, Variant> = values().let { vals ->
    nft.definitions.withIndex().filter { it.value.modifiable != NftDataType.ModifiableBy.NOBODY && it.value.name.isNullOrBlank().not() }
        .associate { it.value.name!! to vals[it.index] }
  }

  fun <T : NftModel> make(clazz: Class<T>): T = NftModel.create(values, clazz.kotlin)
  fun <T : NftModel> make(clazz: KClass<T>): T = NftModel.create(values, clazz)
  inline fun <reified T : NftModel> make(): T = make(T::class)
}
