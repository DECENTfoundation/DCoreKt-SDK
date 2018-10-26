package ch.decent.sdk.model

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.Wallet
import ch.decent.sdk.crypto.address
import ch.decent.sdk.crypto.dpk
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.internal.Streams
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import org.threeten.bp.LocalDateTime
import java.math.BigInteger

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
          val op = OperationType.values()[el.asJsonArray[0].asInt]
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

object PubKeyAdapter : TypeAdapter<PubKey>() {
  override fun read(reader: JsonReader): PubKey {
    reader.beginObject()
    reader.nextName()
    val key = PubKey(BigInteger(reader.nextString().dropLast(1)))
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
    val fee = AssetAmount(BigInteger(reader.nextString()))
    val perKb = reader.takeIf { reader.hasNext() }?.run { nextName(); nextInt() }
    reader.endObject()
    return FeeParameter(fee, perKb)
  }
}

object OperationTypeAdapter : TypeAdapter<OperationType>() {
  override fun write(out: JsonWriter, value: OperationType) {
    out.value(value.ordinal)
  }

  override fun read(reader: JsonReader): OperationType = OperationType.values()[reader.nextInt()]
}