package ch.decent.sdk.net.model

internal enum class ApiGroup(val apiName: String = "") {
  NONE, LOGIN, DATABASE("database"), HISTORY("history"), BROADCAST("network_broadcast")
}