package ch.decent.sdk.net.model

// 0 - database
// 1 - login
// 2 - network
// 3 - history
// 4 - crypto
// 5 - messaging
internal enum class ApiGroup(val apiName: String = "") {
  DATABASE("database"),
  LOGIN,
  BROADCAST("network_broadcast"),
  HISTORY("history"),
  CRYPTO("crypto"),
  MESSAGING("messaging");

  val id: Int
    get() = ordinal
}