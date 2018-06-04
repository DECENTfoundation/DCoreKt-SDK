package ch.decent.sdk.net.serialization

/**
 * Interface implemented by all entities for which makes sense to have a byte-array representation.
 */
internal interface ByteSerializable {
  val bytes: ByteArray
}
