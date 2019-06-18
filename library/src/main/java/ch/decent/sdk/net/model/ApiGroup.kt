package ch.decent.sdk.net.model

// 0 - database
// 1 - login
// 2 - network
// 3 - history
// 4 - crypto
// 5 - messaging
internal enum class ApiGroup(val apiName: String = "") {
  DATABASE("database_api"),
  LOGIN("login_api"),
  BROADCAST("network_broadcast_api"),
  HISTORY("history_api"),
  CRYPTO("crypto_api"),
  MESSAGING("messaging_api");

  val id: String
    get() = apiName
}
