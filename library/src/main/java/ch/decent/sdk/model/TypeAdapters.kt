package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Wallet
import ch.decent.sdk.crypto.address
import ch.decent.sdk.crypto.dpk
import ch.decent.sdk.model.operation.BaseOperation
import ch.decent.sdk.model.operation.EmptyOperation
import ch.decent.sdk.model.operation.OperationType
import ch.decent.sdk.model.operation.UnknownOperation
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.Streams
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import org.threeten.bp.LocalDateTime
import java.lang.reflect.ParameterizedType
import java.math.BigInteger
import kotlin.reflect.KClass

object DateTimeAdapter : TypeAdapter<LocalDateTime>() {
  override fun write(out: JsonWriter, value: LocalDateTime) {
    out.value(value.toString())
  }

  override fun read(reader: JsonReader): LocalDateTime = LocalDateTime.parse(reader.nextString())
}

object ChainObjectAdapter : TypeAdapter<ChainObject>() {
  override fun read(reader: JsonReader): ChainObject = ChainObject.parse(reader.nextString())

  override fun write(out: JsonWriter, value: ChainObject?) {
    value?.let { out.value(it.objectId) } ?: out.nullValue()
  }
}

object AddressAdapter : TypeAdapter<Address>() {
  override fun read(reader: JsonReader): Address? = Address.decodeCheckNull(reader.nextString())

  override fun write(out: JsonWriter, value: Address?) {
    out.value(value?.encode())
  }
}

object AuthMapAdapter : TypeAdapter<AuthMap>() {
  override fun read(reader: JsonReader): AuthMap {
    reader.beginArray()
    val f = AuthMap(reader.nextString().address(), reader.nextInt().toShort())
    reader.endArray()
    return f
  }

  override fun write(out: JsonWriter, value: AuthMap) {
    out.beginArray()
    out.value(value.value.encode())
    out.value(value.weight)
    out.endArray()
  }
}


object MapAdapterFactory : TypeAdapterFactory {
  override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
    if (Map::class.javaObjectType == type.rawType) {
      return object : TypeAdapter<T>() {
        override fun write(out: JsonWriter, value: T) {
          out.beginArray()
          (value as Map<*, *>).entries.forEach {
            out.beginArray()
            writeAny(out, it.key)
            writeAny(out, it.value)
            out.endArray()
          }
          out.endArray()
        }

        override fun read(`in`: JsonReader?): T =
            gson.getDelegateAdapter(this@MapAdapterFactory, type).read(`in`)
      }
    }
    return null
  }

  fun writeAny(out: JsonWriter, value: Any?) {
    when (value) {
      is Number -> out.value(value)
      is String -> out.value(value)
      is Boolean -> out.value(value)
    }
  }
}

@Suppress("UNCHECKED_CAST") object NftTypeFactory : TypeAdapterFactory {

  internal data class NftDataRaw(
      @SerializedName("id") val id: ChainObject,
      @SerializedName("nft_id") val nftId: ChainObject,
      @SerializedName("owner") val owner: ChainObject,
      @SerializedName("data") val data: JsonArray
  )

//  internal val idToModel = mutableMapOf<ChainObject, KClass<out NftModel>>()

  override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
    if (NftData::class.javaObjectType == type.rawType) {
      return object : TypeAdapter<T>() {
        override fun write(out: JsonWriter?, value: T) {}

        override fun read(reader: JsonReader?): T {
          val delegate = gson.getDelegateAdapter(this@NftTypeFactory, TypeToken.get(NftDataRaw::class.java))
          val raw = delegate.read(reader)
//          val model = idToModel[raw.nftId]?.let { NftModel.create(raw.data, it) } ?: GenericNft.create(raw.data)
          return NftData(raw.id, raw.nftId, raw.owner, GenericNft(raw.data)) as T
        }

      }
    }

    return null
  }
}

@Suppress("UNCHECKED_CAST")
object OperationTypeFactory : TypeAdapterFactory {
  override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
    if (type.rawType == BaseOperation::class.javaObjectType) {
      return object : TypeAdapter<T>() {
        override fun write(out: JsonWriter, value: T?) {
          val clazz = (value as BaseOperation).type.clazz!!
          val delegate = gson.getDelegateAdapter(this@OperationTypeFactory, TypeToken.get(clazz)) as TypeAdapter<T>
          out.beginArray()
          out.value((value as BaseOperation).type.ordinal)
          delegate.write(out, value)
          out.endArray()
        }

        override fun read(reader: JsonReader): T? {
          val el = Streams.parse(reader)
          val idx = el.asJsonArray[0].asInt
          val op = OperationType.values().getOrElse(idx) { OperationType.UNKNOWN_OPERATION }
          if (op == OperationType.UNKNOWN_OPERATION) return UnknownOperation(idx) as T?

          val obj = el.asJsonArray[1].asJsonObject
          return op.clazz?.let {
            val delegate = gson.getDelegateAdapter(this@OperationTypeFactory, TypeToken.get(it))
            (delegate.fromJsonTree(obj) as BaseOperation).apply { this.type = op } as T?
          } ?: EmptyOperation(op) as T?
        }
      }
    }
    return null
  }
}

// typedef static_variant<void_t, fixed_max_supply_struct>     asset_options_extensions;
// fixed_max_supply_struct has index 1 therefore we write '1'
@Suppress("UNCHECKED_CAST")
object StaticVariantFactory : TypeAdapterFactory {
  override fun <T : Any?> create(gson: Gson, typeToken: TypeToken<T>): TypeAdapter<T?>? {
    if (!StaticVariant::class.javaObjectType.isAssignableFrom(typeToken.rawType)) return null
    return if (StaticVariantParametrized::class.javaObjectType.isAssignableFrom(typeToken.rawType)) {
      val types = (typeToken.type as ParameterizedType).actualTypeArguments.mapIndexed { idx, t -> idx to TypeToken.get(t) }
      val delegates = types
          .filter { (_, t) -> t.rawType != Unit::class.javaObjectType }
          .map { (idx, t) -> idx to gson.getDelegateAdapter(this, t) }
          .toMap()

      object : TypeAdapter<T?>() {
        override fun write(out: JsonWriter, value: T?) {
          out.beginArray()
          delegates.map { (idx, adapter) ->
            out.beginArray()
            out.value(idx)
            (adapter as TypeAdapter<Any>).write(out, (value as StaticVariantParametrized).objects[idx])
            out.endArray()
          }
          out.endArray()
        }

        override fun read(reader: JsonReader): T? {
          val arr = Streams.parse(reader) as JsonArray
          val vals = arr.map { it.asJsonArray[0].asInt to it.asJsonArray[1] }.toMap()
          val objs = types.map { (idx, t) ->
            if (Unit::class.javaObjectType == t.rawType) Unit
            else delegates[idx]?.fromJsonTree(vals.getOrDefault(idx, JsonNull.INSTANCE))
          }
          return typeToken.rawType.constructors.first().newInstance(objs) as T?
        }
      }
    } else {
      val delegate = gson.getDelegateAdapter(this, typeToken) as TypeAdapter<T>
      object : TypeAdapter<T?>() {
        override fun write(out: JsonWriter, value: T?) {
          out.beginArray()
          (value as StaticVariantSingle<T>?)?.get?.let { (idx, obj) ->
            out.beginArray()
            out.value(idx)
            delegate.write(out, obj)
            out.endArray()
          }
          out.endArray()
        }

        override fun read(reader: JsonReader): T? {
          val arr = Streams.parse(reader) as JsonArray
          return if (arr.size() != 0) delegate.fromJsonTree(arr[0].asJsonArray[1]) else null
        }
      }
    }
  }
}

object PubKeyAdapter : TypeAdapter<PubKey>() {
  override fun read(reader: JsonReader): PubKey? {
    reader.beginObject()
    reader.nextName()
    val int = reader.nextString().dropLast(1)
    val key = if (int.isBlank()) null else PubKey(BigInteger(int))
    reader.endObject()
    return key
  }

  override fun write(out: JsonWriter, value: PubKey) {
    out.beginObject()
    out.name("s")
    out.value(value.keyString)
    out.endObject()
  }
}

object ExtraKeysAdapter : TypeAdapter<Wallet.ExtraKeys>() {
  override fun read(reader: JsonReader): Wallet.ExtraKeys {
    reader.beginArray()
    val account = reader.nextString().toChainObject()
    reader.beginArray()
    val keys = mutableListOf<Address>()
    while (reader.peek() != JsonToken.END_ARRAY) {
      keys.add(reader.nextString().address())
    }
    reader.endArray()
    reader.endArray()
    return Wallet.ExtraKeys(account, keys)
  }

  override fun write(out: JsonWriter, value: Wallet.ExtraKeys) {
    out.beginArray()
    out.value(value.account.objectId)
    out.beginArray()
    value.keys.forEach { out.value(it.encode()) }
    out.endArray()
    out.endArray()
  }
}

object CipherKeyPairAdapter : TypeAdapter<Wallet.CipherKeyPair>() {
  override fun read(reader: JsonReader): Wallet.CipherKeyPair {
    reader.beginArray()
    val keys = Wallet.CipherKeyPair(reader.nextString().address(), reader.nextString().dpk())
    reader.endArray()
    return keys
  }

  override fun write(out: JsonWriter, value: Wallet.CipherKeyPair) {
    out.beginArray()
    out.value(value.public.encode())
    out.value(value.private.toString())
    out.endArray()
  }
}

object MinerIdAdapter : TypeAdapter<MinerId>() {
  override fun write(out: JsonWriter, value: MinerId) {
    out.beginArray()
    out.value(value.name)
    out.value(value.id.objectId)
    out.endArray()
  }

  override fun read(reader: JsonReader): MinerId {
    reader.beginArray()
    val minerId = MinerId(reader.nextString(), reader.nextString().toChainObject())
    reader.endArray()
    return minerId
  }

}

object FeeParamAdapter : TypeAdapter<FeeParameter>() {
  override fun write(out: JsonWriter, value: FeeParameter) {
  }

  override fun read(reader: JsonReader): FeeParameter {
    reader.beginObject()
    reader.nextName()
    val fee = AssetAmount(reader.nextString().toLong())
    val perKb = reader.takeIf { reader.hasNext() }?.run { nextName(); AssetAmount(reader.nextString().toLong()) }
    reader.endObject()
    return FeeParameter(fee, perKb)
  }
}

object OperationTypeAdapter : TypeAdapter<OperationType>() {
  override fun write(out: JsonWriter, value: OperationType) {
    out.value(value.ordinal)
  }

  override fun read(reader: JsonReader): OperationType = OperationType.values().getOrElse(reader.nextInt()) { OperationType.UNKNOWN_OPERATION }
}

object VoteIdAdapter : TypeAdapter<VoteId>() {
  override fun write(out: JsonWriter, value: VoteId) {
    out.value(value.toString())
  }

  override fun read(reader: JsonReader): VoteId = VoteId.parse(reader.nextString())
}

object CoAuthorsAdapter : TypeAdapter<CoAuthors>() {
  override fun write(out: JsonWriter, value: CoAuthors) {
    out.beginArray()
    value.authors.forEach { (id, bp) ->
      out.beginArray()
      out.value(id.objectId)
      out.value(bp)
      out.endArray()
    }
    out.endArray()
  }

  override fun read(reader: JsonReader): CoAuthors {
    val map = mutableMapOf<ChainObject, Int>()
    reader.beginArray()
    while (reader.hasNext()) {
      reader.beginArray()
      map[reader.nextString().toChainObject()] = reader.nextInt()
      reader.endArray()
    }
    reader.endArray()
    return CoAuthors(map)
  }
}
