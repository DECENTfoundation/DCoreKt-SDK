package ch.decent.sdk.net.serialization

typealias Variant = Any

internal enum class VariantTypeId {
  NULL_TYPE,
  INT64_TYPE,
  UINT64_TYPE,
  DOUBLE_TYPE,
  BOOL_TYPE,
  STRING_TYPE,
  ARRAY_TYPE,
  OBJECT_TYPE,
  BLOB_TYPE
}
